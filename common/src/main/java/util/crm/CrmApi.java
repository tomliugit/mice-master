package util.crm;

import util.http.HttpClientUtil;
import util.security.MD5;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import java.util.HashMap;
import java.util.Map;

public class CrmApi {
    private static final String LOGIN = "http://crm.meihua.info/service/crmservice.asmx/login";

    public static CrmLoginRes login(String username, String pwd) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", username);
        params.put("password", pwd);
        Document document;
        try {
            String xml = HttpClientUtil.post(LOGIN, params);
            document = DocumentHelper.parseText(xml);
        } catch (Exception e) {
            e.printStackTrace();
            return new CrmLoginRes(false, "登录异常", null);
        }

        String result = document.getRootElement().getText();
        try {
            int error_code = Integer.valueOf(result);
            return new CrmLoginRes(false, error_code, null);
        } catch (Exception e) {
            JSONObject jb = JSONObject.fromObject(result);
            CrmUser user = new CrmUser();
            user.setUsername(jb.getString("username"));
            user.setPassword(MD5.toMD5(pwd));
            user.setFull_name(jb.getString("fullName"));
            user.setEmail(jb.getString("email"));
            user.setMobilephone(jb.getString("mobilephone"));
            user.setJob_type(jb.getInt("jobtype"));
            user.setRole(jb.getInt("role"));
            return new CrmLoginRes(true, null, user);
        }
    }

    public static void main(String[] args) {
        // TODO: 2018/6/15 密码：5VCVUNDDE
        login("sunwell.gu", "5VCVUNDDE");
    }
}