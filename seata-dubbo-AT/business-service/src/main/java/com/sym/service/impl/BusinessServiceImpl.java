package com.sym.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sym.api.IOrderService;
import com.sym.api.IStorageService;
import com.sym.dto.CommodityDTO;
import com.sym.dto.OrderDTO;
import com.sym.service.IBusinessService;
import com.sym.util.KeepAlive;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 对外业务接口实现
 *
 * @author ym.shen
 * @date 2020/4/16 22:53.
 */
@Service("businessService")
@Slf4j
public class BusinessServiceImpl implements IBusinessService {
    /**
     * 订单dubbo接口
     */
    @Reference(timeout = 5000, retries = 1)
    private IOrderService orderService;

    /**
     * 库存dubbo接口
     */
    @Reference(timeout = 5000, retries = 1)
    private IStorageService storageService;

    /**
     * 业务接口, 在这里开启全局分布式事务, 执行“下单 + 减库存”的原子操作
     *
     * @param commodityCode 商品编号
     * @param orderCount    下单数量
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void purchase(String commodityCode, Integer orderCount) {
        try {
            log.info("...开始执行业务...");

            // 创建订单
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setCommodityCode(commodityCode).setOrderCount(orderCount);
            orderService.create(orderDTO);
            log.info("创建订单");

            // 减库存
            CommodityDTO commodityDTO = new CommodityDTO();
            commodityDTO.setCommodityCode(commodityCode).setQuantity(orderCount);
            storageService.deduct(commodityDTO);
            log.info("删减库存");

            // 执行成功
            log.info("...结束执行业务...");

        } catch (Exception e) {
            log.error("业务执行失败, 原因：{}", e.getMessage());
            throw e;
        } finally {
            // 将程序退出
            KeepAlive.exit();
        }
    }
}
