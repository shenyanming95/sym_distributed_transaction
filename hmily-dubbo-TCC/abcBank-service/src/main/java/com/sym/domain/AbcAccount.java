package com.sym.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ABC bank 账户信息
 *
 * @author shenyanming
 * Created on 2020/5/13 17:22
 */
@Data
@ToString
@Accessors(chain = true)
public class AbcAccount {

    private int id;

    /**
     * 用户手机号
     */
    private String cellphone;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 总余额
     */
    private BigDecimal totalBalance;

    /**
     * 冻结金额
     */
    private BigDecimal frozenBalance;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
