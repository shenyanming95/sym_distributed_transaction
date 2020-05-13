package com.sym.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sym.api.IAbcBank;
import com.sym.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;

/**
 * TCC模式实现类, 必须要解决TCC协议分布式事务的三个问题：
 * 1.幂等
 * 2.空回滚
 * 3.悬挂
 *
 * @author shenyanming
 * Created on 2020/5/13 17:36
 */
@Slf4j
@Service
public class AbcBankImpl implements IAbcBank {

    /**
     * 相当于TCC模式下的try接口
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    @Override
    @Hmily(confirmMethod = "addMoney", cancelMethod = "reduceMoney")
    public boolean transferIn(AccountDTO accountDTO) {
        log.info("tcc-try操作, abc bank入账信息：{}", accountDTO);
        return false;
    }

    /**
     * 确认加钱, 相当于TCC模式下的confirm接口
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    public boolean addMoney(AccountDTO accountDTO){
        return false;
    }

    /**
     * 取消加钱, 相当于TCC模式下的cancel接口
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    public boolean reduceMoney(AccountDTO accountDTO){
        return false;
    }
}
