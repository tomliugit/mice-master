package util.io.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Image写入类 Created by sunwell on 15/7/23.
 */
public class QrCodeUtils {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private QrCodeUtils() {
    }


    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }


    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 生成二维码
     * BEGIN:VCARD #代表这是个vCard文件
     * VERSION:3.0 #vCard标准的版本
     * N:Gump;Forrest #名;姓
     * FN:Forrest Gump #姓名的标准显示方式
     * ORG:Bubba Gump Shrimp Co. #所属公司或组织
     * TITLE:Shrimp Man #职位
     * PHOTO;VALUE=URL;TYPE=GIF:http://www.site.com/dir_photos/my_photo.gif #照片或其他图案文件
     * TEL;TYPE=WORK,VOICE:(111) 555-1212 #工作电话，语音电话
     * TEL;TYPE=HOME,VOICE:(404) 555-1212 #家庭电话，语音电话
     * ADR;TYPE=WORK:;;100 Waters Edge;Baytown;LA;30314;United States of America #工作地址
     * LABEL;TYPE=WORK:100 Waters Edge\nBaytown, LA 30314\nUnited States of America #工作邮寄地址
     * ADR;TYPE=HOME:;;42 Plantation St.;Baytown;LA;30314;United States of America #家庭地址
     * LABEL;TYPE=HOME:42 Plantation St.\nBaytown, LA 30314\nUnited States of America #家庭邮寄地址
     * EMAIL;TYPE=PREF,INTERNET:forrestgump@example.com #电子邮件地址
     * REV:20080424T195243Z #修订版本号
     * END:VCARD #代表这是vCard文件的结尾
     **/
    public static String getQRCode(String path, String fileName, String returnFilePath, String fullName, String email, String mobilePhone, String workPhone, String address, String company, String jobTitle, String note) {

        try {
            File saveDirFile = new File(path);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
            String content = "BEGIN:VCARD\n" +
                    "VERSION:3.0\n" +
                    "N:" + fullName + "\n" +
                    "EMAIL:" + email + "\n" +
                    "TEL:" + mobilePhone + "\n" +
                    "TEL;CELL:" + workPhone + "\n" +
                    "ADR:" + address + "\n" +
                    "ORG:" + company + "\n" +
                    "TITLE:" + jobTitle + "\n" +
                    "URL:\n" +
                    "NOTE:" + note + "\n" + "END:VCARD";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
            File file1 = new File(path, fileName);


            writeToFile(bitMatrix, "jpg", file1);
            return returnFilePath + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
