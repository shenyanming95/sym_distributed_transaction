package com.sym.repository;

import com.sym.domain.AbcAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author shenyanming
 * Created on 2020/5/13 17:35
 */
@Repository
public interface AbcAccountRepository extends CrudRepository<AbcAccount, Integer> {

    /**
     * 手机号定位用户
     */
    AbcAccount findByCellphone(String cellphone);
}
