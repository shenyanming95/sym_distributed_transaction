package com.sym.service;

import com.sym.api.IStorageService;
import com.sym.domain.Commodity;
import com.sym.dto.CommodityDTO;
import com.sym.exception.StockShortageException;
import com.sym.repository.IStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author ym.shen
 * @date 2020/4/16 23:37.
 */
@Slf4j
public class StorageServiceImpl implements IStorageService {

    @Autowired
    private IStorageRepository storageRepository;

    /**
     * 减库存
     * @param commodityDTO 扣减库存信息
     */
    @Override
    public void deduct(CommodityDTO commodityDTO) {
        log.info("...开始扣减库存...");
        // 扣减商品的编号、扣减数量
        String commodityCode = commodityDTO.getCommodityCode();
        Integer quantity = commodityDTO.getQuantity();

        // 先找出对应商品信息
        Commodity commodity = storageRepository.findByCommodityCode(commodityCode);
        log.info("商品信息：{}", commodity);
        if (null == commodity) {
            log.error("商品不存在, commodityCode：{}", commodityCode);
            throw new IllegalArgumentException("商品不存在");
        }

        // 判断库存
        int stock = commodity.getStock();
        log.info("商品编号：{}, 剩余库存：{}, 下单数量：{}", commodityCode, stock, quantity);
        stock = stock - quantity;
        if (stock < 0) {
            log.error("商品库存不足");
            throw new StockShortageException("stock not enough");
        }

        // 扣减库存
        commodity.setStock(stock).setUpdateTime(LocalDateTime.now());
        storageRepository.save(commodity);
        log.info("...结束扣减库存...");
    }
}
