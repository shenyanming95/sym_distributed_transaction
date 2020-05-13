package com.sym.api;

import com.sym.dto.AccountDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * ABC银行, dubbo接口(入账用户)
 *
 * @author shenyanming
 * Created on 2020/5/13 16:58
 */
public interface IAbcBank {

    /**
     * 入账接口, 其实就是TCC模式下的try接口
     *
     * @param accountDTO 入账信息
     * @return true-操作成功, false-操作失败
     */
    @Hmily
    boolean transferIn(AccountDTO accountDTO);

}
