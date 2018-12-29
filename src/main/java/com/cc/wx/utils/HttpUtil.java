package com.cc.wx.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * http 请求工具
 */
public class HttpUtil {
    /* logger */
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    /* httpclient */
    public static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * get
     * @param url request url.
     * @return String.class
     */
    public static String get(String url) {
        return get(url, null, String.class);
    }

    /**
     * get
     * @param url request url.
     * @param entity request entity
     * @return String.class
     */
    public static String get(String url, Object entity) {
        return get(url, entity, String.class);
    }

    /**
     * get
     * @param url request url.
     * @param respType result type.
     * @return result entity.
     */
    public static <T> T get(String url, Class<T> respType) {
        return get(url, null, respType);
    }

    /**
     * get
     * @param url request url.
     * @param entity request entity.
     * @param respType result type.
     * @return result entity.
     */
    public static <T> T get(String url, Object entity, Class<T> respType) {
        CloseableHttpResponse response = null;
        T t = null;
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            StringBuilder sb = new StringBuilder(16);
            sb.append("?");
            if (entity != null) {
                JsonNode jsonNode = jsonMapper.convertValue(entity, JsonNode.class);
                jsonNode.fields().forEachRemaining(entry ->
                    sb.append(entry.getKey()).append("=").append(entry.getValue().asText()).append("&"));
                sb.delete(sb.length() - 1, sb.length());
                url += sb.toString();
            }
            HttpGet get = new HttpGet(url);
            logger.info("request: {} {}", get.getMethod(), url);
            response = httpClient.execute(get);
            String resultStr = null;
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity respEntity = response.getEntity();
                resultStr = EntityUtils.toString(respEntity, DEFAULT_CHARSET);
                t = jsonMapper.readValue(resultStr, respType);
            }
            logger.info("response: {}", resultStr);
            return t;
        } catch (IOException e) {
            logger.error("http get error: {}", e.getMessage());
            return null;
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException ignored) {
            }
        }
    }

    public static String post(String url) {
        return post(url, null, String.class, ContentType.APPLICATION_JSON);
    }

    public static String post(String url, Object entity) {
        return post(url, entity, String.class, ContentType.APPLICATION_JSON);
    }

    public static <T> T post(String url, Class<T> respType) {
        return post(url, null, respType, ContentType.APPLICATION_JSON);
    }

    public static <T> T post(String url, Object entity, Class<T> respType) {
        return post(url, entity, respType, ContentType.APPLICATION_JSON);
    }

    public static <T> T post(String url, Object entity, Class<T> respType, ContentType contentType) {
        CloseableHttpResponse response = null;
        T t = null;
        try {
            HttpPost post = new HttpPost(url);
            ObjectMapper jsonMapper = new ObjectMapper();
            if (entity != null) {
                String reqJsonStr = jsonMapper.writeValueAsString(entity);
                StringEntity entityJson = new StringEntity(reqJsonStr, contentType);
                post.setEntity(entityJson);
            }

            logger.info("request: {} {}", post.getMethod(), url);
            response = httpClient.execute(post);
            String resultStr = null;
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity respEntity = response.getEntity();
                resultStr = EntityUtils.toString(respEntity, DEFAULT_CHARSET);
                t = jsonMapper.readValue(resultStr, respType);
            }
            logger.info("response: {}", resultStr);
            return t;
        } catch (IOException e) {
            logger.error("http post error: {}", e.getMessage());
            return null;
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException ignored) {
            }
        }
    }
}
