package com.sym;

import com.sym.config.AbcServiceConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * 农业银行(入账)服务启动
 *
 * @author shenyanming
 * Created on 2020/5/13 17:21
 */
public class AbcBankApplication {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AbcServiceConfiguration.class);
        applicationContext.start();

        // 保证服务运行
        System.in.read();
    }
}
