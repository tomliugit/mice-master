package info.meihua.file.service.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * // 该注解的locations已经被启用，现在只要是在环境中，都会优先加载
 * @author sunwell
 */
@ConfigurationProperties(prefix = "spring.task.pool")
public class TaskThreadPoolConfig {  
    private int corePoolSize;  
  
    private int maxPoolSize;  
  
    private int keepAliveSeconds;  
  
    private int queueCapacity;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}