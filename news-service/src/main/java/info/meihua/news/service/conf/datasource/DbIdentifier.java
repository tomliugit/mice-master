package info.meihua.news.service.conf.datasource;

/**
 * 数据库标识管理类。用于区分数据源连接的不同数据库。
 *
 * @author sunwell
 */
public class DbIdentifier {

    /**
     * 用不同的工程编码来区分数据库
     */
    private static ThreadLocal<String> projectDb = new ThreadLocal<String>();


    public static String getProjectDb() {
        return projectDb.get();
    }

    /**
     * @param dbInfo code#env#read
     */
    public static void setProjectDb(String dbInfo) {
        projectDb.set(dbInfo);
    }
}
