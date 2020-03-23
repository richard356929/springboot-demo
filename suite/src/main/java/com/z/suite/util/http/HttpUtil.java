package com.z.suite.util.http;

import com.z.suite.exception.SystemException;
import com.z.suite.util.StringUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

    private static Logger logger = LogManager.getLogger(HttpUtil.class);

    private static final int MAX_WAIT_TIME = 40000; // 接口调用最长时间

    /**
     * HTTP POST请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws SystemException
     */
    public static String httpPost(String url, Map<String, String> headers, Map<String, Object> params)
            throws SystemException {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uri = new URIBuilder(url);
            HttpPost hp = new HttpPost(uri.build().toString());

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 请求参数
            if (params != null && params.size() > 0) {
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    Object param = params.get(key);
                    if (param instanceof File) {
                        builder.addPart(key, new FileBody((File) param));
                    } else if (param instanceof String) {
                        builder.addPart(key, new StringBody((String) param, ContentType.TEXT_PLAIN));
                    } else if (param instanceof byte[]) {
                        builder.addPart(key, new ByteArrayBody((byte[]) param, ContentType.DEFAULT_BINARY, key));
                    } else {
                        throw new IllegalArgumentException(key + "的类型是" + param.getClass() + "（允许的参数类型为File或者String）。");
                    }
                }
                hp.setEntity(builder.build());
            }

            // POST请求参数设置
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(MAX_WAIT_TIME)
                .setConnectTimeout(MAX_WAIT_TIME).setConnectionRequestTimeout(MAX_WAIT_TIME).build();
            hp.setConfig(requestConfig);

            // 请求头
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    hp.addHeader(key, headers.getOrDefault(key, ""));
                }
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(hp);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("POSTFile请求出错：[url={}, headers={}, params={}]", url, headers, params, e);
            throw new SystemException(520101);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * HTTP POST（JSON）请求
     *
     * @param url
     *        URL
     * @param headers
     *        请求头
     * @param body
     *        请求体
     * @return
     * @throws SystemException
     */
    public static String httpPost(String url, Map<String, String> headers, String body) throws SystemException {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uri = new URIBuilder(url);
            HttpPost hp = new HttpPost(uri.build().toString());

            // POST请求参数设置
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(MAX_WAIT_TIME)
                .setConnectTimeout(MAX_WAIT_TIME).setConnectionRequestTimeout(MAX_WAIT_TIME).build();
            hp.setConfig(requestConfig);

            // 请求头
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    hp.addHeader(key, headers.getOrDefault(key, ""));
                }
            }

            // POST请求body体
            if (StringUtil.isNotNull(body)) {
                StringEntity entity = new StringEntity(body, "utf-8"); // UTF-8编码
                entity.setContentType("application/json");
                hp.setEntity(entity);
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(hp);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("POST请求出错：[url={}, headers={}, body={}]", url, headers, body, e);
            throw new SystemException(520101);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * HTTP DELETE请求
     *
     * @param url
     *        URL
     * @param headers
     *        请求头
     * @param params
     *        参数
     * @return
     * @throws SystemException
     */
    public static String httpDelete(String url, Map<String, String> headers, Map<String, String> params)
            throws SystemException {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uri = new URIBuilder(url);

            // 请求参数
            if (params != null && params.size() > 0) {
                List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    NameValuePair param = new BasicNameValuePair(key, params.getOrDefault(key, ""));
                    nameValues.add(param);
                }
                uri.setParameters(nameValues);
            }

            // Delete请求参数设置
            HttpDeleteWithBody hd = new HttpDeleteWithBody(uri.build().toString());
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).build();
            hd.setConfig(requestConfig);

            // 请求头
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    hd.addHeader(key, headers.getOrDefault(key, ""));
                }
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(hd);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("DELETE请求出错：[url={}, params={}, headers={}]", url, params, headers, e);
            throw new SystemException(520101);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * HTTP PUT请求
     *
     * @param url
     * @param params
     * @param headers
     * @return
     * @throws SystemException
     */
    public static String httpPUT(String url, Map<String, String> params, Map<String, String> headers)
            throws SystemException {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uri = new URIBuilder(url);

            // 请求参数
            if (params != null && params.size() > 0) {
                List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    NameValuePair param = new BasicNameValuePair(key, params.getOrDefault(key, ""));
                    nameValues.add(param);
                }
                uri.setParameters(nameValues);
            }

            // GET请求参数设置
            HttpPut hp = new HttpPut(uri.build().toString());
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).build();
            hp.setConfig(requestConfig);

            // 请求头
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    hp.addHeader(key, headers.getOrDefault(key, ""));
                }
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(hp);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("PUT请求出错：[url={}, params={}, headers={}]", url, params, headers, e);
            throw new SystemException(520101);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static String httpPUT(String url, Map<String, String> headers, String body) throws SystemException {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uri = new URIBuilder(url);
            HttpPut hp = new HttpPut(uri.build().toString());

            // PUT请求参数设置
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(MAX_WAIT_TIME)
                .setConnectTimeout(MAX_WAIT_TIME).setConnectionRequestTimeout(MAX_WAIT_TIME).build();
            hp.setConfig(requestConfig);

            // 请求头
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    hp.addHeader(key, headers.getOrDefault(key, ""));
                }
            }

            // POST请求body体
            if (StringUtil.isNotNull(body)) {
                StringEntity entity = new StringEntity(body, "utf-8"); // UTF-8编码
                entity.setContentType("application/json");
                hp.setEntity(entity);
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(hp);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("POST请求出错：[url={}, headers={}, body={}]", url, headers, body, e);
            throw new SystemException(520101);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            }
        }
    }

    
    /**
     * HTTP GET请求
     *
     * @param url
     *        URL
     * @param headers
     *        请求头
     * @param params
     *        参数
     * @return
     * @throws SystemException
     */
    public static String httpGet(String url, Map<String, String> headers, Map<String, String> params)
            throws SystemException {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uri = new URIBuilder(url);

            // 请求参数
            if (params != null && params.size() > 0) {
                List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
                Set<String> keys = params.keySet();
                for (String key : keys) {
                    NameValuePair param = new BasicNameValuePair(key, params.getOrDefault(key, ""));
                    nameValues.add(param);
                }
                uri.setParameters(nameValues);
            }

            // GET请求参数设置
            HttpGet hg = new HttpGet(uri.build().toString());
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000).build();
            hg.setConfig(requestConfig);

            // 请求头
            if (headers != null && headers.size() > 0) {
                Set<String> keys = headers.keySet();
                for (String key : keys) {
                    hg.addHeader(key, headers.getOrDefault(key, ""));
                }
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            response = httpClient.execute(hg);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("GET请求出错：[url={}, params={}, headers={}]", url, params, headers, e);
            throw new SystemException(520101);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
