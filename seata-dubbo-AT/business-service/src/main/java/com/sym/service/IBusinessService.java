package com.sym.service;

/**
 * 对外业务接口
 *
 * @author ym.shen
 * @date 2020/4/16 22:46.
 */

public interface IBusinessService {

    /**
     * 用户下单接口, 这边省略掉用户id
     *
     * @param commodityCode 商品编号
     * @param orderCount    下单数量
     */
    void purchase(String commodityCode, Integer orderCount);
}
