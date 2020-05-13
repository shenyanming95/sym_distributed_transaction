package com.sym.api;

import com.sym.dto.CommodityDTO;

/**
 * dubbo-库存接口
 *
 * @author ym.shen
 * @date 2020/4/16 22:51.
 */
public interface IStorageService {

    /**
     * 扣减库存
     * @param commodityDTO 扣减库存信息
     */
    void deduct(CommodityDTO commodityDTO);
}
