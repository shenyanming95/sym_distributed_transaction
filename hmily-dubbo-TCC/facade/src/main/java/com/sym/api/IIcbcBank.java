package com.sym.api;

import com.sym.dto.AccountDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * ICBC银行, dubbo接口(转账用户)
 *
 * @author shenyanming
 * Created on 2020/5/13 16:58
 */
public interface IIcbcBank {

    /**
     * 转账接口, 其实就是TCC模式下的try接口
     *
     * @param accountDTO 转账信息
     * @return true-操作成功, false-操作失败
     */
    @Hmily
    boolean transferOut(AccountDTO accountDTO);

}
