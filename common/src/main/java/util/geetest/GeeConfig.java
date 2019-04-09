package util.geetest;


/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeeConfig {

	// 填入自己的captcha_id和private_key
//	private static final String geetest_id = "c43bbcf9c565864249a6a35be7fd68db";
//	private static final String geetest_key = "2769f223e07e5d23f58268f2e232aa81";

	private static final String geetest_id = "4fda6de72f8b36381b147ff40c0f8993";
	private static final String geetest_key = "0a085817bc8bdd1d1eb7b73902cefbea";

	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}
