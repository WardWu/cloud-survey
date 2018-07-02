package com.shengwu.cloud.survey.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengwu.cloud.survey.model.PageModel;
import com.shengwu.cloud.survey.model.QueryModel;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 创建ES 客户端。
 * Author： shengwu
 * DATE ：  2018/1/12
 */
@Resource
public class EsClient {
    private static final String hosts = "localhost";
    private static final int port = 9300;
    private static final String clusterName = "cloud-survey";
    private TransportClient client = this.getClient();

    TransportClient getClient() {
        if (client == null) {
            client = createClient();
        }
        return client;
    }

    // ES: ip + port
    private TransportClient createClient() {
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hosts), port));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    // 设置集群名称,es集群嗅探功能
    /*private  TransportClient createClientOthers() {
        Settings settings = Settings.builder().put()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .put("client.transport.ignore_cluster_name", clusterName)
                .build();
        TransportClient client  = null;
        try {
            client = new PreBuiltTransportClient(settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }*/

    /**
     * 创建索引index。
     *
     * @param index 索引
     * @return 是否创建成功
     */
    public boolean createIndex(String index) {
        CreateIndexResponse createIndexResponse = this.getClient().admin().indices()
                .create(new CreateIndexRequest(index))
                .actionGet();
        return createIndexResponse.isShardsAcked();
    }

    /**
     * 删除索引。
     *
     * @param indexName 索引名称(批量)
     * @return boolean
     */
    public Boolean deleteIndex(String indexName) {
        DeleteIndexRequestBuilder deleteIndexResponse = this.getClient().admin().indices().prepareDelete(indexName);
        return deleteIndexResponse.get().isAcknowledged();
    }

    /**
     * 新增数据。
     *
     * @param indexName 索引
     * @param typeName  type
     * @param id        id
     * @param obj       参数
     * @return 新增数据id
     */
    public String addDoc(String indexName, String typeName, String id, JSONObject obj) {
        IndexRequestBuilder requestBuilder =
                this.getClient().prepareIndex(indexName, typeName, id).setCreate(true);
        IndexResponse indexResponse = requestBuilder.setSource(JSON.toJSONString(obj)).execute().actionGet();
        return indexResponse.getId();
    }


    /**
     * 根据id获取数据信息。
     *
     * @param id 唯一标示
     * @return 数据信息
     */
    public Map<String, Object> getDocById(String indexName, String typeName, String id, PageModel pageModel) {
        QueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds(id);
        SearchResponse searchResponse = this.getSearchResponse(indexName, typeName, queryBuilder, pageModel);
        Map<String, Object> result = getData(searchResponse);
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        if (data.size() > 0) {
            return data.get(0);
        }
        return new HashMap<>();
    }


    /**
     * 根据id获取数据信息。
     *
     * @param indexName index
     * @param typeName  type
     * @param ids       id列表
     * @param pageModel 分页查询
     * @return 总数total， 数据 data列表
     */
    public Map<String, Object> getDocListById(String indexName, String typeName, String name,
                                              List<String> ids, PageModel pageModel) {
        QueryBuilder queryBuilder = this.createQueryBuilderIds(ids);
        SearchResponse searchResponse = this.getSearchResponse(indexName, typeName, queryBuilder, pageModel);
        return getData(searchResponse);
    }

    /**
     * 查询多个id数据，创建查询条件。
     *
     * @param ids id列表
     * @return 查询条件
     */
    private IdsQueryBuilder createQueryBuilderIds(List<String> ids) {
        IdsQueryBuilder idsQueryBuilder = new IdsQueryBuilder();
        for (String id : ids) {
            idsQueryBuilder.addIds(id);
        }
        return idsQueryBuilder;
    }

    /**
     * 分页查询数据。
     *
     * @param queryModel 查询条件
     * @param pageModel  分页条件
     * @return 数据列表和总数
     */
    public Map<String, Object> queryDocList(QueryModel queryModel, PageModel pageModel) {
        QueryBuilder queryBuilder = this.createQueryBuildersQuery(queryModel);
        SearchResponse searchResponse = this.getSearchResponse(queryModel.getIndexName(),
                queryModel.getTypeName(), queryBuilder, pageModel);
        return this.getData(searchResponse);
    }

    /**
     * 创建QueryBuilder。
     *
     * @param queryModel 查询条件
     * @return 查询 QueryBuilder
     */
    private QueryBuilder createQueryBuildersQuery(QueryModel queryModel) {
        BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
        BoolQueryBuilder mustNotQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entrySet : queryModel.getMustQuery().entrySet()) {
            queryBuilders.must(QueryBuilders.matchQuery(entrySet.getKey(), entrySet.getValue()));
        }
        for (Map.Entry<String, Object> entrySet : queryModel.getMustNotQuery().entrySet()) {
            mustNotQuery.mustNot(QueryBuilders.matchQuery(entrySet.getKey(), entrySet.getValue()));
        }
        for (Map.Entry<String, Object> entrySet : queryModel.getBoolQuery().entrySet()) {
            boolQuery.should(QueryBuilders.matchQuery(entrySet.getKey(), entrySet.getValue()));
        }
        queryBuilders.must().add(mustNotQuery);
        queryBuilders.must().add(boolQuery);
        System.out.println(queryBuilders);
        return queryBuilders;
    }

    /**
     * 更新数据。
     *
     * @param index 索引
     * @param type  type
     * @param id    唯一标识id
     * @param doc   参数
     * @return boolean 是否更新成功
     */
    public boolean updateDoc(String index, String type, String id, Map<String, Object> doc) {
        if (doc.containsKey("id")) {
            doc.remove("id");
        }
        UpdateRequest updateRequest = new UpdateRequest().index(index).type(type).id(id).doc(doc);
        boolean isUpdate = false;
        try {
            this.getClient().update(updateRequest).get();
            isUpdate = true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    /**
     * 删除数据。
     *
     * @param index 索引
     * @param type  type
     * @param id    唯一标识id
     * @return boolean 是否删除成功
     */
    public boolean deleteDoc(String index, String type, String id) {
        return false;
    }

    /**
     * 数据处理。
     *
     * @param searchResponse 返回参数
     * @return total: 0 , data: list
     */
    private Map<String, Object> getData(SearchResponse searchResponse) {
        Map<String, Object> result = new HashMap<>();
        if (searchResponse == null) return result;
        SearchHits searchHits = searchResponse.getHits();
        long total = searchHits.getTotalHits();
        List<Map<String, Object>> data = new ArrayList<>();
        for (SearchHit searchHit : searchHits.getHits()) {
            data.add(searchHit.getSource());
        }

        result.put("total", total);
        result.put("data", data);
        return result;
    }

    // es 查询
    private SearchResponse getSearchResponse(String indexName, String typeName,
                                             QueryBuilder queryBuilder, PageModel pageModel) {
        SortOrder sortOrder = SortOrder.ASC;
        if (pageModel.isDesc()) {
            sortOrder = SortOrder.DESC;
        }
        return this.getClient().prepareSearch(indexName)
                .setTypes(typeName)
                .setFrom(pageModel.getSkip())
                .setSize(pageModel.getLimit())
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
    }
}
