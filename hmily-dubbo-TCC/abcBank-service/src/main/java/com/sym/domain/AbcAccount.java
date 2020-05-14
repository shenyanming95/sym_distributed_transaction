package com.sym.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "t_account")
public class AbcAccount {

    @Id
    private long id;

    /**
     * 用户手机号
     */
    private String cellphone;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 总余额
     */
    @Column(name = "total_balance")
    private BigDecimal totalBalance;

    /**
     * 冻结金额
     */
    @Column(name = "frozen_balance")
    private BigDecimal frozenBalance;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
