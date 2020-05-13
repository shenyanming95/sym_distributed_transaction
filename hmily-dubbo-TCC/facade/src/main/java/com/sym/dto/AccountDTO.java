package com.sym.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 入账DTO
 *
 * @author shenyanming
 * Created on 2020/5/13 17:02
 */
public class AccountDTO implements Serializable {
    private static final long serialVersionUID = -2439851350653188771L;

    /**
     * 对方手机号
     */
    private String cellPhone;

    /**
     * 对方银行编码
     */
    private String bankCode;

    /**
     * 转账金额
     */
    private BigDecimal amount;
}
