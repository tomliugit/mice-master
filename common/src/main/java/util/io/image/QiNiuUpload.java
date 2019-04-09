package util.io.image;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QiNiuUpload {
    private final String ACCESS_KEY = "G4be_TBi3rbtKy-bqT_DwNcvfOs_ReG7da9iY6f5";
    private final String SECRET_KEY = "OUANyutMaRBjh8SKCK6uLD2HVGwRNIEmYMCoBX0D";
    private final String HOST = "http://resource.bigcat.com/";

    private String getUploadToken() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken("bigcat", null, 3600, new StringMap()
                .putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
    }

    private String getUploadToken(String key) {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken("mhfile", key, 3600, new StringMap().put("insertOnly", 0)
                .putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
    }

    public UploadReturnBody uploadFile(byte[] data, String key) throws Exception {
        UploadReturnBody body = null;
        String token = getUploadToken(key);
        UploadManager uploadManager = null;
        long fileLength = data.length;
        if (fileLength > 4194304) {
            //大于4兆的文件分片上传
            Recorder recorder = new FileRecorder("/data/mh_file");
            //Recorder recorder = new FileRecorder("E://photo");
            uploadManager = new UploadManager(recorder);
        } else {
            uploadManager = new UploadManager();
        }
        Response res = uploadManager.put(data, key, token);
        if (res.isOK()) {
            body = res.jsonToObject(UploadReturnBody.class);
            body.setUrl(HOST + body.getKey());
        }
        return body;
    }

    public UploadReturnBody uploadFileByKey(String fullFilePath, String key) {
        UploadReturnBody body = null;
        try {
            String token = getUploadToken();
            UploadManager uploadManager = new UploadManager();
            //删除之前的版本
            deleteFile("bigcat", key);

            //上传新的版本
            Response res = uploadManager.put(fullFilePath, key, token);
            if (res.isOK()) {
                body = res.jsonToObject(UploadReturnBody.class);
                body.setUrl(HOST + body.getKey());
            }
        } catch (Exception e) {
            return null;
        }
        return body;
    }

    public UploadReturnBody uploadFile(byte[] data) throws Exception {
        UploadReturnBody body = null;
        String token = getUploadToken();
        UploadManager uploadManager = null;
        long fileLength = data.length;
        if (fileLength > 4194304) {
            //大于4兆的文件分片上传
            Recorder recorder = new FileRecorder("/data/mh_file");
            //Recorder recorder = new FileRecorder("E://photo");
            uploadManager = new UploadManager(recorder);
        } else {
            uploadManager = new UploadManager();
        }
        Response res = uploadManager.put(data, null, token);
        if (res.isOK()) {
            body = res.jsonToObject(UploadReturnBody.class);
            body.setUrl(HOST + body.getKey());
        }
        return body;
    }

    /**
     * 根据图片网络地址获取图片的byte[]类型数据
     *
     * @param urlPath 图片网络地址
     * @return 图片数据
     */
    public static byte[] getImageFromURL(String urlPath) {
        byte[] data = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            // conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            is = conn.getInputStream();
            if (conn.getResponseCode() == 200) {
                data = readInputStream(is);
            } else {
                data = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return data;
    }

    /**
     * 读取InputStream数据，转为byte[]数据类型
     *
     * @param is InputStream数据
     * @return 返回byte[]数据
     */
    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = baos.toByteArray();
        try {
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void deleteFile(String bucketName, String key) {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(bucketName, key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
      /*  String url = "http://resource.m1world.com/safasf";
        new QiNiuUpload().deleteFile("pager", url.replace("http://resource.m1world.com/", ""));
*/
        new QiNiuUpload().deleteFile("mhfile", "996");
    }
}
