package info.meihua.news.service.conf.datasource;

import java.util.TimerTask;

/**
 * 清除空闲连接任务
 *
 * @author sunwell
 */
public class ClearIdleTimerTask extends TimerTask {

    @Override
    public void run() {
        DdsHolder.instance().clearIdleDDS();
    }
}
