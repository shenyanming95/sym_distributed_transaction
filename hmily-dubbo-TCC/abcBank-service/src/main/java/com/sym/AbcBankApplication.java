package com.sym;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

/**
 * 农业银行(入账)服务启动类
 *
 * @author shenyanming
 * Created on 2020/5/13 17:21
 */
@Configuration
@ComponentScan({"com.sym","org.dromara.hmily.*"})
@EnableAspectJAutoProxy
@EnableJpaRepositories("com.sym.repository")
@EnableTransactionManagement
public class AbcBankApplication {
    public static void main(String[] args) throws IOException {
        // 启动服务
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AbcBankApplication.class);
        applicationContext.start();

        // 保证服务运行
        System.in.read();
    }
}
