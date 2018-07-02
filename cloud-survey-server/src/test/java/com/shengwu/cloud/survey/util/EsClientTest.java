package com.shengwu.cloud.survey.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengwu.cloud.survey.model.PageModel;
import com.shengwu.cloud.survey.model.QueryModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Author： shengwu
 * DATE ：  2018/1/22
 */
public class EsClientTest implements Runnable{

    private static final String indexName = "survey_index";
    private static final String typeName = "survey_type";
    private static   EsClient esClient = getEsClient();


    private static EsClient getEsClient(){
        return new EsClient();
    }

    @Test
    public void createIndex() throws Exception {
        boolean isCreate = esClient.createIndex("survey_index");
        System.out.println(isCreate);
    }

    @Test
    public void deleteIndex() throws Exception {
    }

    @Test
    public void addDoc() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", "shengwu");
        jsonObject.put("info", "Test question message...");
        String  id = null;
        String chars = "ABCD";
        String qid = null;
        long allTime = 0;
        for (int i = 0; i< 2998; i++){
            for (int j = 1; j <=10; j++) {
                String question = "question_" +  j;
                jsonObject.put(question, chars.charAt((int)(Math.random() * 4)));
            }
            id = UUID.randomUUID().toString().replace("-", "");
            jsonObject.put("id", id);
            long start = System.currentTimeMillis();
            qid =  esClient.addDoc(indexName, typeName, id, jsonObject);
            long end = System.currentTimeMillis() - start;
            System.out.println("qid:" + qid + ", Time cost:" + end + "ms...");
            if( i > 0) allTime += end;
        }
        System.out.println("Add 999 docs time cost: " + allTime + "ms");
    }

    @Test
    public void addDocThread() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", "shengwu");
        jsonObject.put("info", "Test question message...");
        String  id = null;
        String chars = "ABCD";
        String qid = null;
        long allTime = 0;
        for (int j = 1; j <=10; j++) {
            String question = "question_" +  j;
            jsonObject.put(question, chars.charAt((int)(Math.random() * 4)));
        }
        id = UUID.randomUUID().toString().replace("-", "");
        jsonObject.put("id", id);
        long start = System.currentTimeMillis();
        qid = esClient.addDoc(indexName, typeName, id, jsonObject);
        long end = System.currentTimeMillis() - start;
        System.out.println("qid:" + qid + ", Time cost:" + end + "ms...");
    }

    @Test
    public void getDocById() throws Exception {
        String id = "366d41e62c934788ae22388456372558";
        Map<String, Object> data = esClient.getDocById(indexName, typeName, id, new PageModel());
        System.out.println(JSON.toJSONString(data));
    }

    @Test
    public void getDocListById() throws Exception {
        String id = "54be2d5d693445bf8d101cc42a66d216";
        String id1 = "49fa5217309441709ab97720cfafb959";
        List<String> ids = new ArrayList<>();
        ids.add(id);
        ids.add(id1);
        String name = "id";
        Map<String, Object> data = esClient.getDocListById(indexName, typeName, name, ids, new PageModel());
        System.out.println(JSON.toJSONString(data));
    }

    @Test
    public void queryDocList() throws Exception {
        QueryModel queryModel = new QueryModel();
        queryModel.setIndexName(indexName);
        queryModel.setTypeName(typeName);
        Map<String, Object> mustQuery = queryModel.getMustQuery();
        if (mustQuery == null ){
            mustQuery = new HashMap<>();
        }
        Map<String, Object> mustNotQuery = queryModel.getMustNotQuery();
        if (mustNotQuery == null ){
            mustNotQuery = new HashMap<>();
        }
        Map<String, Object> boolQuery = queryModel.getBoolQuery();
        if (boolQuery == null ){
            boolQuery = new HashMap<>();
        }
        mustQuery.put("question_1", "A");
        mustQuery.put("question_2", "A");
        mustQuery.put("question_3", "A");
        mustNotQuery.put("question_4", "A or B or C");
        mustNotQuery.put("question_10", "B");
//        mustNotQuery.put("question_3", "B");
//        mustNotQuery.put("question_4", "B");
//        mustNotQuery.put("question_3", "B");
//        mustNotQuery.put("question_3", "C");
//        mustNotQuery.put("question_3", "A");
        boolQuery.put("question_5", "B or D");
//        boolQuery.put("question_3", "D");
        Map<String, Object> result = esClient.queryDocList(queryModel, new PageModel());
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        System.out.println(result.get("total"));
        for (Map<String, Object> map:data) {
            System.out.println(JSON.toJSONString(map));
        }
        System.out.println("----------------------------");
    }

    @Test
    public void updateDoc() throws Exception {

        String id = "506edb50267f40fcb5a6629d9b606279";
        // String id1 = "3194ccea881f4862a37c94d455a34259";
        Map<String, Object> doc = new HashMap<>();
        doc.put("question_1", "D");
        doc.put("question_2", "D");
        doc.put("question_3", "D");
        doc.put("question_4", "D");
        doc.put("question_5", "D");
        doc.put("question_6", "D");
        doc.put("question_7", "D");
        doc.put("question_8", "D");
        doc.put("question_9", "D");
        doc.put("question_10", "D");
        boolean isUpdate = esClient.updateDoc(indexName, typeName, id, doc);
        System.out.println(isUpdate);
    }

    @Test
    public void deleteDoc() throws Exception {
    }

    @Test
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for (int i = 0; i< 1000; i++){
            try {
                this.addDocThread();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Add 999 docs time cost: " + (System.currentTimeMillis() - start) + "ms");
    }
}