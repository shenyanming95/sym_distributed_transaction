package com.sym.config;

import org.dromara.hmily.common.config.HmilyRedisConfig;
import org.dromara.hmily.common.enums.RepositorySupportEnum;
import org.dromara.hmily.core.bootstrap.HmilyTransactionBootstrap;
import org.dromara.hmily.core.service.HmilyInitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * ABC Bank, hmily配置
 *
 * @author shenyanming
 * @date 2020/5/14 21:57.
 */
@Configuration
public class AbcServiceHmilyConfig {

    /**
     * hmily启动配置
     */
    @Bean
    public HmilyTransactionBootstrap hmilyTransactionBootstrap(HmilyInitService hmilyInitService){
        HmilyTransactionBootstrap bootstrap = new HmilyTransactionBootstrap(hmilyInitService);
        bootstrap.setSerializer("kryo");
        // 定时任务延迟时间（单位是秒，默认120。这个参数只是要大于你的rpc调用的超时时间设置
        bootstrap.setRecoverDelayTime(120);
        // 最大重复次数，默认3次。当你的服务down机，定时任务会执行retryMax次数去执行你的cancel还是confrim。
        bootstrap.setRetryMax(3);
        bootstrap.setLoadFactor(2);
        bootstrap.setScheduledDelay(120);
        bootstrap.setScheduledThreadMax(4);
        // disruptor的bufferSize,当高并发的时候，可以调大。注意是 2n次方
        bootstrap.setBufferSize(4096);
        // distuptor消费线程数量,高并发的时候，可以调大。
        bootstrap.setConsumerThreads(32);
        // 发起方的时候，把此属性设置为true。参与方为false。
        bootstrap.setStarted(false);
        // 异步执行confirm和cancel线程池线程的大小，高并发的时候请调大
        bootstrap.setAsyncThreads(32);
        //
        bootstrap.setRepositorySupport(RepositorySupportEnum.REDIS.getSupport());
        bootstrap.setHmilyRedisConfig(hmilyRedisConfig());
        return bootstrap;
    }

    /**
     * hmily自身事务日志配置, 用redis存储
     */
    @Bean
    public HmilyRedisConfig hmilyRedisConfig(){
        HmilyRedisConfig config = new HmilyRedisConfig();
        config.setHostName("127.0.0.1");
        config.setPort(6379);
        return config;
    }
}
