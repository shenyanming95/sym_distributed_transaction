package com.sym.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import io.seata.config.springcloud.EnableSeataSpringConfig;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 以注解方式配置dubbo 和 spring容器
 *
 * @author ym.shen
 * @date 2020/4/18 11:03.
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.sym.service.impl")
@ComponentScan("com.sym")
@EnableSeataSpringConfig
public class DubboBusinessConfiguration {

    private String applicationName = "seata-dubbo-business-service";

    @Bean
    public ApplicationConfig applicationConfig(){
        return new ApplicationConfig(applicationName);
    }

    @Bean
    public RegistryConfig registryConfig(){
        String zookeeperHost = "zookeeper://127.0.0.1:2181";
        return new RegistryConfig(zookeeperHost);
    }

    @Bean
    public GlobalTransactionScanner globalTransactionScanner(){
        // 第一个参数指定应用名称, 第二个参数指定事务组(需要先配置在seata-server上)
        return new GlobalTransactionScanner(applicationName, "sym_tx_group");
    }
}
