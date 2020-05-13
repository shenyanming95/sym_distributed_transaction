package com.sym.repository;

import com.sym.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 订单dao
 *
 * @author ym.shen
 * @date 2020/4/16 23:12.
 */
@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
}
