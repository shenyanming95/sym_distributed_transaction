# sym_distributed_transaction
分布式事务的一些解决方案

# 1.seata

[seata](https://seata.io/zh-cn/docs/user/quickstart.html)是阿里开源的具有高性能且易于使用的分布式事务解决方案，可用于微服务架构，它为用户提供了 AT、TCC、SAGA 和 XA 事务模式，详细内容请查阅[官方文档](https://seata.io/zh-cn/docs/user/quickstart.html)。此项目基于seata v1.0.0。

## 1.1.seata-dubbo-AT

seata-dubbo使用的是AT事务模式，它实际上就是2PC协议。这边会将系统分为business-service、order-service和storage-service等3个微服务，由business-service发起全局事务请求，执行逻辑就是简单的”下订单-扣库存“；首先要到官网将seata-server下载到本地，注意是1.0.0版本，目前最新为1.1.0。不管是seata-server，还是seata-client(就是自己的微服务)都有一个重要文件：registry.conf，由它来定义seata的注册中心和配置中心。