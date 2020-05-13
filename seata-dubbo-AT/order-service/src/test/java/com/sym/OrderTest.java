package com.sym;

import com.sym.api.IOrderService;
import com.sym.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 订单服务测试类
 *
 * @author ym.shen
 * Created on 2020/4/17 09:11
 */
@Slf4j
public class OrderTest {

    private IOrderService orderService;

    @Before
    public void before(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dubbo-order.xml");
        orderService = applicationContext.getBean(IOrderService.class);
    }

    @Test
    public void insertTest(){
        OrderDTO dto = new OrderDTO();
        dto.setCommodityCode("a0001").setOrderCount(11);
        orderService.create(dto);
    }
}
