package com.sym.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sym.api.IAbcBank;
import com.sym.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.common.bean.context.HmilyTransactionContext;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.transaction.annotation.Transactional;

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

//    @Autowired
//    private AbcAccountRepository abcAccountRepository;

//    @Autowired
//    private AbcBankLogRepository abcBankLogRepository;

    /**
     * 相当于TCC模式下的try接口,需要解决悬挂。
     * 业务逻辑：将入账的钱加到用户的冻结金额上，然后增加一条日志
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    @Override
    @Hmily(confirmMethod = "addMoney", cancelMethod = "reduceMoney")
    @Transactional(rollbackFor = Exception.class)
    public boolean transferIn(AccountDTO accountDTO) {
        HmilyTransactionContext context = HmilyTransactionContextLocal.getInstance().get();
        log.info("【ABC-try操作】参数信息: {}, 事务上下文：{}", accountDTO, context);

        // String serialNumber = accountDTO.getSerialNumber();

        // 防止TCC悬挂
        /*if(abcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.CANCEL)){
            log.info("【ABC-try操作】发生悬挂, 参数信息：{}, 事务ID: {}", accountDTO, transId);
            return false;
        }*/

        // 将入账的钱保存到用户的冻结金额上
        /*AbcAccount abcAccount = abcAccountRepository.findByCellphone(accountDTO.getCellPhoneTo());
        BigDecimal oldFrozenBalance = abcAccount.getFrozenBalance();
        abcAccount.setFrozenBalance(oldFrozenBalance.add(accountDTO.getAmount()))
                .setUpdateTime(LocalDateTime.now());
        abcAccountRepository.save(abcAccount);*/

        // 同时插入try操作日志
        /*AbcBankLog log = new AbcBankLog();
        log.setCellphone(abcAccount.getCellphone())
                .setCurrentFrozenBalance(oldFrozenBalance)
                .setCurrentTotalBalance(abcAccount.getTotalBalance())
                .setSerialNumber(serialNumber)
                .setTransferBalance(accountDTO.getAmount())
                .setUsername(abcAccount.getUsername())
                .setOperationType(OperationType.TRY)
                .setCreateTime(LocalDateTime.now());
        abcBankLogRepository.save(log);*/
        return true;
    }

    /**
     * 确认加钱, 相当于TCC模式下的confirm接口
     * 需要保证幂等性
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addMoney(AccountDTO accountDTO){
        log.info("【ABC-confirm操作】参数信息: {}, 事务上下文：{}", accountDTO,
                HmilyTransactionContextLocal.getInstance().get());
//        String serialNumber = accountDTO.getSerialNumber();

        // 幂等控制
        /*if(abcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.CONFIRM)){
            log.info("【ABC-confirm操作】发生重复提交, 事务ID：{}", transId);
            return false;
        }*/

        // 把冻结的钱添加到用户的总金额上
        /*BigDecimal amount = accountDTO.getAmount();

        AbcAccount abcAccount = abcAccountRepository.findByCellphone(accountDTO.getCellPhoneTo());
        BigDecimal oldTotalBalance = abcAccount.getTotalBalance();
        BigDecimal oldFrozenBalance = abcAccount.getFrozenBalance();

        abcAccount.setTotalBalance(oldTotalBalance.add(amount))
                .setFrozenBalance(oldFrozenBalance.subtract(amount))
                .setUpdateTime(LocalDateTime.now());

        abcAccountRepository.save(abcAccount);*/

        // 新增日志
        /*AbcBankLog log = new AbcBankLog();
        log.setCellphone(abcAccount.getCellphone())
                .setCurrentFrozenBalance(oldFrozenBalance)
                .setCurrentTotalBalance(oldTotalBalance)
                .setSerialNumber(serialNumber)
                .setTransferBalance(amount)
                .setUsername(abcAccount.getUsername())
                .setOperationType(OperationType.CONFIRM)
                .setCreateTime(LocalDateTime.now());
        abcBankLogRepository.save(log);*/

        return true;
    }

    /**
     * 取消加钱, 相当于TCC模式下的cancel接口，
     * 需要解决幂等、空回滚
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    public boolean reduceMoney(AccountDTO accountDTO){
        log.info("【ABC-cancel操作】参数信息: {}, 事务上下文：{}", accountDTO,
                HmilyTransactionContextLocal.getInstance().get());


//        String serialNumber = accountDTO.getSerialNumber();

        // 防止空回滚
        /*if(!abcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.TRY)){
            // 如果try操作都未执行, 那么canel被回调了就是空回滚,
            log.info("【ABC-cancel操作】发生空回滚, 事务ID:{}", transId);
            return false;
        }*/

        // 幂等
        /*if(abcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.CANCEL)){
            log.info("【ABC-cancel操作】发生重复提交, 事务ID: {}", transId);
            return false;
        }*/

        // 回滚
       /* AbcAccount abcAccount = abcAccountRepository.findByCellphone(accountDTO.getCellPhoneFrom());

        BigDecimal totalBalance = abcAccount.getTotalBalance();
        BigDecimal frozenBalance = abcAccount.getFrozenBalance();
        BigDecimal amount = accountDTO.getAmount();

        abcAccount.setFrozenBalance(frozenBalance.subtract(amount)).setUpdateTime(LocalDateTime.now());
        abcAccountRepository.save(abcAccount);*/

        // 日志记录
        /*AbcBankLog log = new AbcBankLog();
        log.setCellphone(abcAccount.getCellphone())
                .setCurrentFrozenBalance(frozenBalance)
                .setCurrentTotalBalance(totalBalance)
                .setSerialNumber(serialNumber)
                .setTransferBalance(amount)
                .setUsername(abcAccount.getUsername())
                .setOperationType(OperationType.CANCEL)
                .setCreateTime(LocalDateTime.now());
        abcBankLogRepository.save(log);*/

        return true;
    }
}
