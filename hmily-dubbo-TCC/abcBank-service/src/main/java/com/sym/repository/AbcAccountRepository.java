package com.sym.repository;

import com.sym.domain.AbcAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * @author shenyanming
 * Created on 2020/5/13 17:35
 */
public interface AbcAccountRepository extends CrudRepository<AbcAccount, Integer> {

    /**
     * 手机号定位用户
     * @param cellphone
     * @return
     */
    AbcAccount findByCellphone(String cellphone);
}
