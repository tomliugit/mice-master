package util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

/**
 * M1 http请求 Created by sunwell on 2017/6/8.
 */
public class MhHttpClient {

    private String defaultContentEncoding;

    public MhHttpClient() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    public MhHttpClient(String encode) {
        this.defaultContentEncoding = encode;
    }


    /**
     * 发送GET请求
     *
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException
     */
    public MhHttpResponse sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", null, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @return 响应对象
     * @throws IOException
     */
    public MhHttpResponse sendGet(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "GET", params, null);
    }

    /**
     * 发送GET请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException
     */
    public MhHttpResponse sendGet(String urlString, Map<String, String> params,
                                  Map<String, String> propertys) throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    /**
     * 发送POST请求
     *
     * @param urlString URL地址
     * @return 响应对象
     * @throws IOException
     */
    public MhHttpResponse sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", null, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @return 响应对象
     * @throws IOException
     */
    public MhHttpResponse sendPost(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "POST", params, null);
    }

    /**
     * 发送POST请求
     *
     * @param urlString URL地址
     * @param params    参数集合
     * @param propertys 请求属性
     * @return 响应对象
     * @throws IOException
     */
    public MhHttpResponse sendPost(String urlString, Map<String, String> params,
                                   Map<String, String> propertys) throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    /**
     * 发送HTTP请求
     *
     * @param urlString
     * @return 响映对象
     * @throws IOException
     */
    private MhHttpResponse send(String urlString, String method,
                                Map<String, String> parameters, Map<String, String> propertys)
            throws IOException {
        HttpURLConnection urlConnection = null;

        if (method.equalsIgnoreCase("GET") && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

//        urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
//        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        if (propertys != null)
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }

        if (method.equalsIgnoreCase("POST") && parameters != null) {
            StringBuffer param = new StringBuffer();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }

        return this.makeContent(urlString, urlConnection);
    }

    /**
     * 得到响应对象
     *
     * @param urlConnection
     * @return 响应对象
     * @throws IOException
     */
    private MhHttpResponse makeContent(String urlString,
                                       HttpURLConnection urlConnection) throws IOException {
        MhHttpResponse mhHttpResponse = new MhHttpResponse();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(in));
            mhHttpResponse.contentCollection = new Vector<String>();
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                mhHttpResponse.contentCollection.add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String ecod = urlConnection.getContentEncoding();
            if (ecod == null)
                ecod = this.defaultContentEncoding;

            mhHttpResponse.urlString = urlString;

            mhHttpResponse.defaultPort = urlConnection.getURL().getDefaultPort();
            mhHttpResponse.file = urlConnection.getURL().getFile();
            mhHttpResponse.host = urlConnection.getURL().getHost();
            mhHttpResponse.path = urlConnection.getURL().getPath();
            mhHttpResponse.port = urlConnection.getURL().getPort();
            mhHttpResponse.protocol = urlConnection.getURL().getProtocol();
            mhHttpResponse.query = urlConnection.getURL().getQuery();
            mhHttpResponse.ref = urlConnection.getURL().getRef();
            mhHttpResponse.userInfo = urlConnection.getURL().getUserInfo();

            mhHttpResponse.content = new String(temp.toString().getBytes(), ecod);
            mhHttpResponse.contentEncoding = ecod;
            mhHttpResponse.code = urlConnection.getResponseCode();
            mhHttpResponse.message = urlConnection.getResponseMessage();
            mhHttpResponse.contentType = urlConnection.getContentType();
            mhHttpResponse.method = urlConnection.getRequestMethod();
            mhHttpResponse.connectTimeout = urlConnection.getConnectTimeout();
            mhHttpResponse.readTimeout = urlConnection.getReadTimeout();

            return mhHttpResponse;
        } catch (IOException e) {
            throw e;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    /**
     * 默认的响应字符集
     */
    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    /**
     * 设置默认的响应字符集
     */
    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }
}
