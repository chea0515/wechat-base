package com.cc.wx.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
    private static final Logger logger;
    /* json */
    public static final ObjectMapper jsonMapper;
    /* httpclient */
    public static final CloseableHttpClient httpClient;
    /* default charset */
    public static final String DEFAULT_CHARSET;
    /* content type: json */
    public static final String APPLICATION_JSON;

    static {
        logger = LoggerFactory.getLogger(HttpUtil.class);
        jsonMapper = new ObjectMapper();
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        httpClient = HttpClients.createDefault();
        DEFAULT_CHARSET = "UTF-8";
        APPLICATION_JSON = "application/json";
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
            HttpGet get = new HttpGet(buildUrl(url, entity));
            logger.info("request: {} {}", get.getMethod(), url);
            response = httpClient.execute(get);
            String resultStr = null;
            if (respType != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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

    public static void post(String url) {
        post(url, null, null, APPLICATION_JSON, DEFAULT_CHARSET);
    }

    public static void post(String url, Object entity) {
        post(url, entity, null, APPLICATION_JSON, DEFAULT_CHARSET);
    }

    public static <T> T post(String url, Class<T> respType) {
        return post(url, null, respType, APPLICATION_JSON, DEFAULT_CHARSET);
    }

    public static <T> T post(String url, Object entity, Class<T> respType) {
        return post(url, entity, respType, APPLICATION_JSON, DEFAULT_CHARSET);
    }

    public static <T> T post(String url, Object entity, Class<T> respType, String contentType, String charsetName) {
        T t = null;
        HttpPost post = new HttpPost(url);
        if (entity != null) {
            post.setEntity(buildEntity(entity, contentType, charsetName));
        }

        logger.info("request: {} {}", post.getMethod(), url);
        try (CloseableHttpResponse response = httpClient.execute(post)) {
            String resultStr = null;
            if (respType != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity respEntity = response.getEntity();
                resultStr = EntityUtils.toString(respEntity, charsetName);
                t = jsonMapper.readValue(resultStr, respType);
            }
            logger.info("response: {}", resultStr);
            return t;
        } catch (IOException e) {
            logger.error("http post error: {}", e.getMessage());
            return null;
        }
    }

    public static <T> T put(String url, Object entity, Class<T> respType, String contentType, String charsetName) {
        T t = null;
        HttpPut httpPut = new HttpPut(url);
        if (entity != null) {
            httpPut.setEntity(buildEntity(entity, contentType, charsetName));
        }
        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            if (respType != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = EntityUtils.toString(response.getEntity(), charsetName);
                t = jsonMapper.readValue(str, respType);
            }
            return t;
        } catch (IOException e) {
            logger.error("http put error: {}", e.getMessage());
            return null;
        }
    }

    public static void delete(String url) {
        delete(url, null, null, DEFAULT_CHARSET);
    }

    public static void delete(String url, Object entity) {
        delete(url, entity, null, DEFAULT_CHARSET);
    }

    public static <T> T delete(String url, Object entity, Class<T> respType, String charsetName) {
        T t = null;
        HttpDelete httpDelete = new HttpDelete(buildUrl(url, entity));
        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            if (respType != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String str = EntityUtils.toString(response.getEntity(), charsetName);
                t = jsonMapper.readValue(str, respType);
            }
            return t;
        } catch (IOException e) {
            logger.error("http put error: {}", e.getMessage());
            return null;
        }
    }

    private static String buildUrl(String url, Object entity) {
        String targetUrl = url;
        if (entity != null) {
            StringBuilder sb = new StringBuilder(16);
            sb.append("?");
            JsonNode jsonNode = jsonMapper.convertValue(entity, JsonNode.class);
            jsonNode.fields().forEachRemaining(entry ->
                    sb.append(entry.getKey()).append("=").append(entry.getValue().asText()).append("&"));
            sb.delete(sb.length() - 1, sb.length());
            targetUrl += sb.toString();
        }
        return targetUrl;
    }

    private static StringEntity buildEntity(Object entity, String contentType, String charsetName) {
        try {
            String entityStr = jsonMapper.writeValueAsString(entity);
            StringEntity stringEntity = new StringEntity(entityStr, charsetName);
            stringEntity.setContentType(contentType);
            stringEntity.setContentEncoding(charsetName);
            return stringEntity;
        } catch (JsonProcessingException e) {
            logger.error("build string entity error: {}", e);
            return null;
        }
    }
}
