package com.sym;

import com.sym.util.KeepAlive;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author shenyanming
 * Created on 2020/5/13 15:48
 */
public class OrderApplication {
    public static void main(String[] args) throws IOException {
        // 启动容器, 将订单服务注册到zk上
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-dubbo-order.xml");
        applicationContext.start();
        // 保持系统运行
        KeepAlive.sync();
    }
}
