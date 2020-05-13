package com.sym.api;

import com.sym.dto.OrderDTO;

/**
 * dubbo-订单接口
 *
 * @author ym.shen
 * @date 2020/4/16 22:49.
 */
public interface IOrderService {

    /**
     * 下单
     * @param orderDTO 订单信息
     */
    void create(OrderDTO orderDTO);
}
