package util.lang;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Pattern;

public class NumberUtils {

    public static boolean isNumeric(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    //处理double类型数据,小数点后保留两位数字
    public static double doubleHandler(double input) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(input));
    }

    //生成6位随机数字验证码
    public static String genVerifyCode() {
        Random random = new Random();
        int x = random.nextInt(899999) + 100000;
        return String.valueOf(x);
    }

    /**
     * 通过UserId加时间戳，转化为16进制，然后可以用左移右移来生成6位大小写字母加数字的推荐码。要求唯一，并且6位，比如mHcA35之类的。
     * @param userId
     */
    public static String getRecommendCode(Long userId) {

        long dateTime = System.currentTimeMillis();

        dateTime += userId;

        String li = Long.toHexString(dateTime);

        return li.substring(li.length()-6, li.length());
    }

    public static void main(String[] args) {

       /* List<String> list = new ArrayList<String>();
        int j=50000;
        for(int i = 0;i<=1000000;i++){
            list.add(getRecommendCode(j+i));
        }
        System.out.println("----list.size()="+list.size());
        Set<String> set = new HashSet<String>();
        set.addAll(list);
        System.out.println("-------set.size="+set.size());*/

        long a = 88888;
        System.out.println(isNumeric("1111"));


    }
}
