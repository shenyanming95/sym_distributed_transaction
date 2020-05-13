package com.sym.service;

import com.sym.api.IOrderService;
import com.sym.domain.Order;
import com.sym.dto.OrderDTO;
import com.sym.repository.IOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ym.shen
 * @date 2020/4/16 23:10.
 */
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    /**
     * 创建订单
     * @param orderDTO 订单信息
     */
    @Override
    public void create(OrderDTO orderDTO) {
        String commodityCode = orderDTO.getCommodityCode();
        Integer orderCount = orderDTO.getOrderCount();

        log.info("开始创建订单, 商品编号：{}, 数量：{}", commodityCode, orderCount);
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();
        order.setCommodityCode(commodityCode)
                .setOrderCount(orderCount)
                .setMoney(BigDecimal.valueOf(666.666))
                .setCreateTime(now)
                .setUpdateTime(now);
        log.info("结束创建订单：{}", orderRepository.save(order));
    }
}
