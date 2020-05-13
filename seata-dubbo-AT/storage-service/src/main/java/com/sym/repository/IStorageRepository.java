package com.sym.repository;

import com.sym.domain.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 库存dao
 *
 * @author ym.shen
 * @date 2020/4/16 23:12.
 */
@Repository
public interface IStorageRepository extends JpaRepository<Commodity, Integer> {

    /**
     * 通过商品编号查询
     *
     * @param commodityCode 商品编号
     * @return 实体
     */
    Commodity findByCommodityCode(String commodityCode);
}
