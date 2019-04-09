package util.regex;

import util.lang.ArrayListUtils;
import util.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验工具类 Created by sunwell on 2017/6/7.
 */
public class RegexValidateUtils {

    /**
     * 手机号码校验
     *
     * @param mobilePhone
     * @return
     */
    public static String checkMobilePhone(String mobilePhone) {
        try {
            String mobilePhoneReg = "^(0|86|17951)?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$";
            mobilePhone = mobilePhone.replaceAll(" ", "");
            mobilePhone = delSpecialSign(mobilePhone);//手机号：去除特殊字符、去空格，中国号默认加上86
            if (mobilePhone.equals("") || mobilePhone.toLowerCase().equals("null") || mobilePhone.toLowerCase().equals("undefine") || mobilePhone.toLowerCase().equals("none")) {
                return "";
            }
            if (mobilePhone.substring(0, 1).equals("0")) {
                mobilePhone = mobilePhone.substring(1, mobilePhone.length());
            }
            if (mobilePhone.substring(0, 1).equals("+")) {
                mobilePhone = mobilePhone.substring(1, mobilePhone.length());
            }
            Pattern regex = Pattern.compile(mobilePhoneReg);
            Matcher matcher = regex.matcher(mobilePhone);
            if (matcher.matches()) {
                if (mobilePhone.substring(0, 2).equals("86")) {
                    mobilePhone = "+" + mobilePhone;
                } else {
                    mobilePhone = "+86" + mobilePhone;
                }
            } else {
                mobilePhone = "";
            }
            return mobilePhone;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 电话号码校验
     *
     * @param workPhone
     * @return
     */
    public static String checkWorkPhone(String workPhone) {
        workPhone = delSpecialSign(workPhone);//固号：去除特殊字符、去空格
        return workPhone;
    }

    /**
     * 邮箱校验
     *
     * @param email
     * @return
     */
    public static String checkEmail(String email) {
        String emailReg = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        email = email.replaceAll(" ", "");//Email去除空格
        if (email.equals("") || email.toLowerCase().equals("null") || email.toLowerCase().equals("undefine") || email.toLowerCase().equals("none")) {
            return "";
        }
        char a = email.charAt(email.length() - 1);
        if (a == ',' || a == ';' || a == '，' || a == '；') {
            email = email.substring(0, email.length() - 1);
        }
        Pattern regex = Pattern.compile(emailReg);
        Matcher matcher = regex.matcher(email);
//        if (!matcher.matches()) {
//
//        }
        email = email.replaceAll("<", "").replaceAll(">", "").replaceAll("'", "").replaceAll("\\?","").replaceAll("\\\\", "");
        return email;
    }

    /**
     * 是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        String numberReg = "-?\\d+(\\.\\d+)?";
        return str.matches(numberReg);
    }

    /**
     * 是否SQL注入
     *
     * @param str
     * @return
     */
    public static boolean isInjectSql(String str) {
        String sqlReg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        Pattern sqlPattern = Pattern.compile(sqlReg, Pattern.CASE_INSENSITIVE);
        return sqlPattern.matcher(str).find();
    }


    /**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        String messyCodeReg = "\\s*|t*|r*|n*";
        Pattern p = Pattern.compile(messyCodeReg);
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (char c : ch) {
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        return result > 0.4;
    }

    /**
     * 是否是中文
     *
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     */
    private static String delSpecialSign(String str) {
        String[] reg = {",", "-", "–", "，", "、", "/", ":", "\"", "<", ">"};
        for (String r : reg) {
            if (str.contains(r)) {
                str = str.replaceAll(r, "");
            }
        }
        str = str.replaceAll(" ", "");
        if (str.contains("?")) {
            str = str.replaceAll("[?]", "");
        }
        if (str.contains("*")) {
            str = str.replaceAll("[*]", "");
        }
        if (str.contains("\\")) {
            str = str.replaceAll("\\\\", "");//去掉\
        }
        if (str.contains("|")) {
            str = str.replaceAll("[|]", "");
        }
        return str;
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        return pattern.matcher(str).matches();
    }

    /**
     * 验证是否是email
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

    public static String checkUrl(String url) {
        String urlReg = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern regex = Pattern.compile(urlReg);
        Matcher matcher = regex.matcher(url);
        if (!matcher.matches()) {
            return null;
        }
        return url;
    }

    public static boolean isCompanyEmail(String email) {
        String[] items = new String[]{"qq.com", "google.com", "sina.com", "163.com", "126.com", "yeah.net", "sohu.com", "tom.com", "sogou.com", "139.com", "hotmail.com", "live.com", "live.cn", "live.com.cn", "189.com", "yahoo.com.cn", "yahoo.cn", "eyou.com", "21cn.com", "188.com", "foxmail.com"};
        return !ArrayListUtils.hasInCondition(items, email);
    }

    public static String[] splitEmailPhone(String row) {
        row = row.trim();
        String[] items = row.split(";");
        if (items.length == 1) {
            items = row.split(",");
        }
        return items;
    }

}
