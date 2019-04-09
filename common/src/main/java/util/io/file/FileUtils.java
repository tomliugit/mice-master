package util.io.file;

import constant.GlobalConstants;
import util.lang.EncodeUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunwell
 * @date 2017/6/8
 */
public class FileUtils {
    private static AtomicInteger counter = new AtomicInteger(0);

    private static final int COUNT_MAX = 999999;

    /**
     * 生成相对唯一ID
     *
     * @return
     */
    private static long getRelativeUniqueFileId() {
        if (counter.get() > COUNT_MAX) {
            counter.set(1);
        }
        long time = System.currentTimeMillis();
        return time * 100 + counter.incrementAndGet();
    }

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    public static String fileUpload(MultipartFile file) throws Exception {
        String path = null;
        if (!file.isEmpty()) {
            String suffix = "";
            String orgFileName = file.getOriginalFilename();
            int index = orgFileName.lastIndexOf(".");
            if (index > 0) {
                suffix = orgFileName.substring(index + 1);
            }

            DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd/");
            String datePath = format1.format(new Date());
            String tmp = GlobalConstants.DEFAULT_FILE_PATH + datePath;

            //String tmp = Constants.FILE_ROOT + rootPath;
            File dir = new File(tmp);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }

            String filePath = datePath + getRelativeUniqueFileId();

            if (!"".equals(suffix)) {
                filePath = filePath + "." + suffix;
            }

            String fileFullPath = GlobalConstants.DEFAULT_FILE_PATH + filePath;
            File localFile = new File(fileFullPath);
            file.transferTo(localFile);
            path = filePath;
        }
        return path;
    }

    /**
     * 文件读取
     *
     * @param filePath
     * @return
     */
    public static String read(String filePath) throws Exception {
        Resource resource = new ClassPathResource(filePath);
        InputStreamReader read = new InputStreamReader(resource.getInputStream(), "UTF-8");
        BufferedReader br = new BufferedReader(read);
        String lineTxt;
        StringBuilder txtContent = new StringBuilder();

        while ((lineTxt = br.readLine()) != null) {
            txtContent.append(lineTxt);
        }
        read.close();

        return txtContent.toString();

    }

    public static boolean checkFileType(String s) {
        String[] condition = new String[]{"csv", "xls", "xlsx"};
        boolean has = false;
        for (String url : condition) {
            if (s.indexOf(url) == 0) {
                has = true;
                break;
            }
        }
        return has;
    }

    /**
     * 将文件转为字节数组
     *
     * @param file
     * @param fileBase64
     * @return
     */
    public static byte[] getFileByte(MultipartFile file, String fileBase64) {
        byte[] bytes;
        try {
            if (file != null) {
                bytes = file.getBytes();
            } else {
                bytes = EncodeUtils.convertBase64ToByte(fileBase64);
            }
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }


}
