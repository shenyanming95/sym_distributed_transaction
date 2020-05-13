package com.sym;

import com.sym.util.KeepAlive;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 库存发布服务
 *
 * @author ym.shen
 * @date 2020/4/16 23:07.
 */

public class StorageApplication {
    public static void main(String[] args) throws IOException {
        // 启动容器, 将订单服务注册到zk上
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-dubbo-storage.xml");
        applicationContext.start();
        // keep alive
        KeepAlive.sync();
    }
}
