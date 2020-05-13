package com.sym;

import com.sym.config.DubboBusinessConfiguration;
import com.sym.service.IBusinessService;
import com.sym.util.KeepAlive;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 启动业务接口, 远程调用订单服务和库存服务
 *
 * @author ym.shen
 * @date 2020/4/16 22:45.
 */
public class BusinessApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DubboBusinessConfiguration.class);
        IBusinessService businessService = applicationContext.getBean(IBusinessService.class);
        // 执行业务接口
        businessService.purchase("a0001", 40);
        // 保持运行直至业务方法执行完
        KeepAlive.sync();
    }
}
