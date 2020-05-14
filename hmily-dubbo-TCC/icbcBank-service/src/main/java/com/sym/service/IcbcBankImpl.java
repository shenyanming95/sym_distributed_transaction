package com.sym.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sym.api.IIcbcBank;
import com.sym.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shenyanming
 * @date 2020/5/15 5:20.
 */
@Service
@Slf4j
public class IcbcBankImpl  implements IIcbcBank {

    /**
     * 相当于TCC模式下的try接口,需要解决悬挂。
     * 业务逻辑：判断账户余额，将资金减去转账金额，同时增加日志
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    @Override
    @Hmily(confirmMethod = "reduceMoney", cancelMethod = "increaseMoney")
    @Transactional(rollbackFor = Exception.class)
    public boolean transferOut(AccountDTO accountDTO) {
        return false;
    }

    /**
     * 确认扣钱, 相当于TCC模式下的confirm接口
     * 需要保证幂等性
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    public boolean reduceMoney(AccountDTO accountDTO){
        return false;
    }

    /**
     * 取消扣钱, 相当于TCC模式下的cancel接口，
     * 需要解决幂等、空回滚
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    public boolean increaseMoney(AccountDTO accountDTO){
        return false;
    }
}
