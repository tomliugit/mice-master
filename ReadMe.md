```  
### MICE项目结构：
* common                   -------------------------- 基础公共项目，主要开发公共基础工具等代码  
* registry  6501           -------------------------- 服务注册中心  项目勿动 
* gateway   6505           -------------------------- 代理所有微服务的接口网关  
* config    6502           -------------------------- 外部配置  项目勿动，维护config-repo目录   

* monitor   6533 6534      -------------------------- 监控   项目勿动   
* zipkin    6504           -------------------------- 分布式跟踪   项目勿动
 

### 本地手动启动
#### 配置 rabbitmq
#### 修改host:
- 127.0.0.1 registry 
- 127.0.0.1 config
- 127.0.0.1 monitor
- 127.0.0.1 rabbitmq

#### 启动顺序：
registry=> config=> monitor=> zipkin=> gateway(新增service需修改gateway配置文件)=> 

- 启动后地址：（用户名：admin；密码：meihua6666666）
- 服务注册中心：http://registry:6501 
- 外部配置地址：http://config:6502   

media-service        6506
setting-service      6507
news-service         6508
task-service         6509
analysis-service     6510

logs-service         6511
schedule-service     6512
message-service      6513
file-service         6514

redis-service                6515
elasticsearch-service        6516

```  