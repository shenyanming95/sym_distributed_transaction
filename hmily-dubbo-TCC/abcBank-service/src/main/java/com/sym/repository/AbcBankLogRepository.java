package com.sym.repository;

import com.sym.domain.AbcBankLog;
import com.sym.enums.OperationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author shenyanming
 * Created on 2020/5/14 17:39
 */
@Repository
public interface AbcBankLogRepository extends CrudRepository<AbcBankLog, Long> {

    /**
     * 判断阶段日志是否存在
     */
    boolean existsBySerialNumberAndOperationType(String serialNumber, OperationType operationType);
}
