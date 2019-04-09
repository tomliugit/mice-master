package info.meihua.news.service.conf.datasource;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

/**
 * 动态数据源管理器。
 *
 * @author sunwell
 */
public class DdsHolder {

    /**
     * 管理动态数据源列表。<工程编码，数据源>
     */
    private Map<String, DdsTimer> ddsMap = new HashMap<String, DdsTimer>();

    /**
     * 通过定时任务周期性清除不使用的数据源
     */
    @SuppressWarnings("AlibabaAvoidUseTimer")
    private static Timer clearIdleTask = new Timer();

    static {
        clearIdleTask.schedule(new ClearIdleTimerTask(), 5000, 60 * 1000);
    }

    private DdsHolder() {

    }

    /**
     * 获取单例对象
     */
    public static DdsHolder instance() {
        return DdsHolderBuilder.instance;
    }

    /**
     * 添加动态数据源。
     *
     * @param projectCode 项目编码
     * @param dds         dds
     */
    public synchronized void addDDS(String projectCode, DataSource dds) {

        DdsTimer ddst = new DdsTimer(dds);
        ddsMap.put(projectCode, ddst);
    }

    /**
     * 查询动态数据源
     *
     * @param projectCode 项目编码
     * @return dds
     */
    public synchronized DataSource getDDS(String projectCode) {

        if (ddsMap.containsKey(projectCode)) {
            DdsTimer ddst = ddsMap.get(projectCode);
            ddst.refreshTime();
            return ddst.getDds();
        }

        return null;
    }

    /**
     * 清除超时无人使用的数据源。
     */
    public synchronized void clearIdleDDS() {

        Iterator<Entry<String, DdsTimer>> iter = ddsMap.entrySet().iterator();
        for (; iter.hasNext(); ) {

            Entry<String, DdsTimer> entry = iter.next();
            if (entry.getValue().checkAndClose()) {
                iter.remove();
            }
        }
    }

    /**
     * 单例构件类
     *
     * @author sunwell
     */
    private static class DdsHolderBuilder {
        private static DdsHolder instance = new DdsHolder();
    }
}
