package com.shengwu.cloud.survey.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * http请求。
 * Created by shengwu on 2017/9/14.
 */
public class HttpUtils {
    /**
     * POST请求公用方法。
     *
     * @param url    [in] 请求地址
     * @param params [in] 请求参数
     * @return String [out] 请求结果
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        PostMethod method = new PostMethod(url);

        try {
            method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            List<NameValuePair> nameValuePairsList = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                nameValuePairsList.add(new NameValuePair(entry.getKey(), entry.getValue()));
            }
            method.addParameters(nameValuePairsList.toArray(new NameValuePair[]{}));
            new HttpClient().executeMethod(method);
            return method.getResponseBodyAsString();
        } finally {
            method.releaseConnection();
        }
    }
}
