



[seata](https://seata.io/zh-cn/docs/user/quickstart.html)是阿里开源的具有高性能且易于使用的分布式事务解决方案，可用于微服务架构，它为用户提供了 AT、TCC、SAGA 和 XA 事务模式，详细内容请查阅[官方文档](https://seata.io/zh-cn/docs/user/quickstart.html)。此项目基于seata v1.0.0。

seata-dubbo-AT模块使用的是AT事务模式，将系统分为business-service、order-service和storage-service等3个微服务，由business-service发起全局事务请求，执行逻辑就是简单的”下订单-扣库存“。首先要到官网将seata-server下载到本地，注意是1.0.0版本，目前最新为1.1.0。不管是seata-server，还是seata-client(就是自己的微服务)都有一个重要文件：registry.conf，由它来定义seata的注册中心和配置中心。

# 1.seata-server的配置

1. 打开registry.conf：

```wiki
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  # 默认这边为file, 表示注册在磁盘文件上，这里我换成zk，所以就需要将zk配置为本地服务地址
  type = "zk"

  nacos {
    serverAddr = "localhost"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  # 这边修改zk地址
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}
```

配置完注册中心，还要指定配置中心，即seata-server运行时会从配置中心读取配置完成自身启动：

```wiki
config {
  # file、nacos 、apollo、zk、consul、etcd3
  # 默认的配置就是file, 你也可以换成上面定义的任一种，然后预先将配置导进去，如果配置的是文件
  # 只要在同级目录下文件指定文件名完成配置修改即可
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = ""
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```

2. 打开file.conf

```wiki
service {
  # 这边是事务组，可以自定义，例如“sym_tx_group”就是我自定义的事务组名，后面seata-client也要用这个组
  # 名才可以注册到seata-server上
  vgroup_mapping.sym_tx_group = "default"
  # seata-server的启动地址
  default.grouplist = "127.0.0.1:8091"
  #disable seata
  disableGlobalTransaction = false
}

## transaction log store, only used in seata-server
store {
  ## store mode: file、db
  ## 默认这边用的是file，因为AT模式需要3表进行事务回滚操作，所以还是使用数据库保存，将mode换成“db”
  mode = "db"

  ## file store property
  file {
    ## store location dir
    dir = "sessionStore"
  }

  ## 在这边更换数据库的配置，尤其是url、user和password，保证seata-server能连上你的数据库
  db {
    ## 
    datasource = "druid"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    driver-class-name = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://127.0.0.1:3307/seata_server"
    user = "root"
    password = "root"
  }
}
```

# 2.seata-client的配置

seata-client就是我们自己定义的微服务，所以必须引入seata的依赖，详细看项目的pom.xml

1. 配置seata代理数据源和全局扫描器，以xml配置为例：

```xml
<!-- seata 代理数据源, 其实就执行sql时进行拦截，原理是InnoDB的undo和redo-->
<bean id="storageDataSourceProxy" class="io.seata.rm.datasource.DataSourceProxy">
    <!-- 这里是配置原始的数据源 -->
    <constructor-arg ref="storageDatasource" />
</bean>
<!-- seata 全局扫描器 -->
<bean class="io.seata.spring.annotation.GlobalTransactionScanner">
    <!-- 应用名称自定义即可 -->
    <constructor-arg value="seata-dubbo-AT-storage-service"/>
    <!-- 这里就很重要，需要前面seata-server的事务组配置一样，不然注册不进去 -->
    <constructor-arg value="sym_tx_group"/>
</bean>
```

2. seata-client也有registry.conf，需要放到classpath下，这个和seata-server的配置没啥区别，同样是更换注册中心为zk，同时指定配置中心是用file读取

```wi
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "zk"

  nacos {
    serverAddr = "localhost"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = ""
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```

3. file.conf，大部分用默认配置即可，这个配置可以到[seata源码地址](https://github.com/seata/seata/tree/1.0.0/script)找到(前面下载的seata-server的解压包里面的README.md有介绍每个包保存的是什么配置，看一眼就明白了)

```wiki
transport {
  # tcp udt unix-domain-socket
  type = "TCP"
  #NIO NATIVE
  server = "NIO"
  #enable heartbeat
  heartbeat = true
  # the client batch send request enable
  enable-client-batch-send-request = true
  #thread factory for netty
  thread-factory {
    boss-thread-prefix = "NettyBoss"
    worker-thread-prefix = "NettyServerNIOWorker"
    server-executor-thread-prefix = "NettyServerBizHandler"
    share-boss-worker = false
    client-selector-thread-prefix = "NettyClientSelector"
    client-selector-thread-size = 1
    client-worker-thread-prefix = "NettyClientWorkerThread"
    # netty boss thread size,will not be used for UDT
    boss-thread-size = 1
    #auto default pin or 8
    worker-thread-size = 8
  }
  shutdown {
    # when destroy server, wait seconds
    wait = 3
  }
  serialization = "seata"
  compressor = "none"
}
service {
  ## 唯一要改的就是这里，保证和seata-server的事务组一致即可
  vgroup_mapping.sym_tx_group = "default"
  #only support when registry.type=file, please don't set multiple addresses
  default.grouplist = "127.0.0.1:8091"
  #degrade, current not support
  enableDegrade = false
  #disable seata
  disableGlobalTransaction = true
}

client {
  rm {
    async.commit.buffer.limit = 10000
    lock {
      retry.internal = 10
      retry.times = 30
      retry.policy.branch-rollback-on-conflict = true
    }
    report.retry.count = 5
    table.meta.check.enable = false
    report.success.enable = true
  }
  tm {
    commit.retry.count = 5
    rollback.retry.count = 5
  }
  undo {
    data.validation = true
    log.serialization = "jackson"
    log.table = "undo_log"
  }
  log {
    exceptionRate = 100
  }
  support {
    # auto proxy the DataSource bean
    spring.datasource.autoproxy = false
  }
}
```

# 3.数据库表的创建

seata-server在AT模式下需要创建三张表：global_table、branch_table和lock_table，脚本在官方源码仓库有，在本项目的sql目录下也有；

seata-client，也就是各个微服务，除了创建自己的业务表以外，还要创建一张undo_log表，脚本在官方源码仓库有，在本项目子模块的resource/sql/也有。

