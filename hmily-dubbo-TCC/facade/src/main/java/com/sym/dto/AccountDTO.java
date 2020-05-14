package com.sym.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 入账DTO
 *
 * @author shenyanming
 * Created on 2020/5/13 17:02
 */
@Data
@ToString
@Accessors(chain = true)
public class AccountDTO implements Serializable {
    private static final long serialVersionUID = -2439851350653188771L;

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 手机号
     */
    private String cellPhoneFrom;
    private String cellPhoneTo;

    /**
     * 银行编号
     */
    private String bankCodeFrom;
    private String bankCodeTo;

    /**
     * 转账金额
     */
    private BigDecimal amount;
}
