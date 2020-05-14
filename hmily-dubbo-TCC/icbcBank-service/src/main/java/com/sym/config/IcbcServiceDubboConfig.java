package com.sym.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ICBC Bank, dubbo配置
 *
 * @author shenyanming
 * @date 2020/5/14 21:55.
 */
@Configuration
@DubboComponentScan("com.sym.service")
public class IcbcServiceDubboConfig {

    @Bean
    public ApplicationConfig applicationConfig(){
        return new ApplicationConfig("icbc-bank-service");
    }

    @Bean
    public RegistryConfig registryConfig(){
        return new RegistryConfig("zookeeper://127.0.0.1:2181");
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        return new ProtocolConfig("dubbo", 10013);
    }

}
