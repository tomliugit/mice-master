package util.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CNameUtils cname校验工具类
 * @date 2018年9月13日
 * @author Ziqin
 */
public class CNameUtils {

    /**
     * ping后返回的结果数据
     */
    static List<String> returnList = new ArrayList<>();

    /**
     * 校验CNAME是否配置成功
     * @param ipAddress     域名地址
     * @param dirAddress    指向地址
     * @return     true-cname检查成功 false-cname检测失败
     */
    public boolean checkCName(String ipAddress,String dirAddress){
        try{
            ping(ipAddress);
            boolean tof = false;
            for(int i = 0;i<returnList.size();i++){
                String pingStr = returnList.get(i);
                tof = pingStr.contains(dirAddress);
                if(tof){
                    return tof;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+e);
        }
        return false;
    }

    /**
     * 调用系统Ping方法
     * @param ipAddress 域名地址
     * @return
     */
    private static String ping(String ipAddress)throws Exception {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        String pingCommand ="ping -c 3 "+ ipAddress;
        Process p = r.exec(pingCommand);
        if (p == null) {
            return null;
        }
        in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        int connectedCount = 0;
        String line = null;
        while ((line = in.readLine()) != null) {
            connectedCount += getCheckResult(line);
        }
        in.close();
        return null;
    }

    /**
     * 获得ping返回
     * @param line
     * @return
     */
    static int getCheckResult(String line) {
        //System.out.println("控制台输出的结果为:" + line);
        returnList.add(line);
        //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }
    public static void main(String[] args){
        String osName = System.getProperty("os.name");
        System.out.println(osName);
    }

}
