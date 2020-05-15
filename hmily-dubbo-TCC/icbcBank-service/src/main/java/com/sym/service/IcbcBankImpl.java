package com.sym.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sym.api.IAbcBank;
import com.sym.api.IIcbcBank;
import com.sym.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.common.bean.context.HmilyTransactionContext;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 它去调用服务{@link com.sym.api.IAbcBank}
 *
 * @author shenyanming
 * @date 2020/5/15 5:20.
 */
@Service
@Slf4j
public class IcbcBankImpl implements IIcbcBank {

    /**
     * 作为dubbo消费者去调用ABC bank服务, 同时自身也作为Hmily的事务参与者, 所以它也要遵循TCC协议
     */
    @Reference
    private IAbcBank abcBank;

//    @Autowired
//    private IcbcAccountRepository icbcAccountRepository;
//
//    @Autowired
//    private IcbcBankLogRepository icbcBankLogRepository;

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
        HmilyTransactionContext context = HmilyTransactionContextLocal.getInstance().get();
        log.info("【ICBC-try操作】参数信息: {}, 事务上下文: {}", accountDTO, context);

        String serialNumber = accountDTO.getSerialNumber();

        // 防止TCC悬挂
        /*if(icbcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.CANCEL)){
            log.info("【ICBC-try操作】发生悬挂, 参数信息：{}, 事务ID: {}", accountDTO, transId);
            return false;
        }*/

        // 判断用户余额是否充足
        /*IcbcAccount icbcAccount = icbcAccountRepository.findByCellphone(accountDTO.getCellPhoneFrom());
        BigDecimal totalBalance = icbcAccount.getTotalBalance();
        BigDecimal frozenBalance = icbcAccount.getFrozenBalance();

        BigDecimal decimal = totalBalance.subtract(accountDTO.getAmount());
        if(decimal.compareTo(BigDecimal.ZERO) < 0){
            log.info("【ICBC-try操作】账户余额不足");
            return false;
        }*/

        // 冻结账户资金
        /*icbcAccount.setFrozenBalance(frozenBalance.add(accountDTO.getAmount()))
                .setTotalBalance(totalBalance.subtract(accountDTO.getAmount()))
                .setUpdateTime(LocalDateTime.now());
        icbcAccountRepository.save(icbcAccount);*/

        // 同时插入try操作日志
        /*IcbcBankLog log = new IcbcBankLog();
        log.setCellphone(icbcAccount.getCellphone())
                .setCurrentFrozenBalance(frozenBalance)
                .setCurrentTotalBalance(totalBalance)
                .setSerialNumber(serialNumber)
                .setTransferBalance(accountDTO.getAmount())
                .setUsername(icbcAccount.getUsername())
                .setOperationType(OperationType.TRY)
                .setCreateTime(LocalDateTime.now());
        icbcBankLogRepository.save(log);*/

        // 发起转账
        abcBank.transferIn(accountDTO);

        return true;
    }

    /**
     * 确认扣钱, 相当于TCC模式下的confirm接口
     * 需要保证幂等性
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    public boolean reduceMoney(AccountDTO accountDTO) {
        HmilyTransactionContext context = HmilyTransactionContextLocal.getInstance().get();
        log.info("【ICBC-confirm操作】参数信息: {}, 事务信息: {}", accountDTO, context);

//        String serialNumber = accountDTO.getSerialNumber();

        // 幂等控制
        /*if (icbcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.CONFIRM)) {
            log.info("【ICBC-confirm操作】发生重复提交, 事务ID：{}", transId);
            return false;
        }*/

        // 扣除用户的资金
        /*IcbcAccount icbcAccount = icbcAccountRepository.findByCellphone(accountDTO.getCellPhoneFrom());
        BigDecimal oldTotalBalance = icbcAccount.getTotalBalance();
        BigDecimal oldFrozenBalance = icbcAccount.getFrozenBalance();

        icbcAccount.setFrozenBalance(oldFrozenBalance.subtract(accountDTO.getAmount())).setUpdateTime(LocalDateTime.now());
        icbcAccountRepository.save(icbcAccount);*/

        // 新增日志
        /*IcbcBankLog log = new IcbcBankLog();
        log.setCellphone(icbcAccount.getCellphone()).setCurrentFrozenBalance(oldFrozenBalance).setCurrentTotalBalance(oldTotalBalance).setSerialNumber(serialNumber).setTransferBalance(accountDTO.getAmount()).setUsername(icbcAccount.getUsername()).setOperationType(OperationType.CONFIRM).setCreateTime(LocalDateTime.now());
        icbcBankLogRepository.save(log);*/

        return true;
    }

    /**
     * 取消扣钱, 相当于TCC模式下的cancel接口，
     * 需要解决幂等、空回滚
     *
     * @param accountDTO 入账信息
     * @return true-操作成功
     */
    public boolean increaseMoney(AccountDTO accountDTO) {
        HmilyTransactionContext context = HmilyTransactionContextLocal.getInstance().get();
        log.info("【ICBC-cancel操作】参数信息: {}, 事务消息: {}", accountDTO, context);

//        String serialNumber = accountDTO.getSerialNumber();

        // 防止空回滚
        /*if (!icbcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.TRY)) {
            // 如果try操作都未执行, 那么canel被回调了就是空回滚,
            log.info("【ICBC-cancel操作】发生空回滚, 事务ID:{}", transId);
            return false;
        }*/

        // 幂等
        /*if (icbcBankLogRepository.existsBySerialNumberAndOperationType(serialNumber, OperationType.CANCEL)) {
            log.info("【ICBC-cancel操作】发生重复提交, 事务ID: {}", transId);
            return false;
        }*/

        // 回滚，将冻结的钱加回到用户资金上
        /*String cellPhoneFrom = accountDTO.getCellPhoneFrom();
        BigDecimal amount = accountDTO.getAmount();

        IcbcAccount icbcAccount = icbcAccountRepository.findByCellphone(cellPhoneFrom);
        BigDecimal totalBalance = icbcAccount.getTotalBalance();
        BigDecimal frozenBalance = icbcAccount.getFrozenBalance();

        icbcAccount.setTotalBalance(totalBalance.add(amount)).setFrozenBalance(frozenBalance.subtract(amount)).setUpdateTime(LocalDateTime.now());
        icbcAccountRepository.save(icbcAccount);*/

        // 日志记录
        /*IcbcBankLog log = new IcbcBankLog();
        log.setCellphone(cellPhoneFrom).setCurrentFrozenBalance(frozenBalance).setCurrentTotalBalance(totalBalance).setSerialNumber(serialNumber).setTransferBalance(amount).setUsername(icbcAccount.getUsername()).setOperationType(OperationType.CANCEL).setCreateTime(LocalDateTime.now());
        icbcBankLogRepository.save(log);*/

        return true;
    }
}
