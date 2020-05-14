package com.sym.domain;

import com.sym.converter.LocalDateTimeAttributeConverter;
import com.sym.enums.OperationType;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author shenyanming
 * Created on 2020/5/14 17:32
 */
@Data
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "t_log")
public class AbcBankLog {

    @Id
    private long id;

    @Column(name = "serial_number")
    private String serialNumber;

    private String cellphone;

    private String username;

    @Column(name = "current_total_balance")
    private BigDecimal currentTotalBalance;

    @Column(name = "current_frozen_balance")
    private BigDecimal currentFrozenBalance;

    @Column(name = "transfer_balance")
    private BigDecimal transferBalance;

    @Column(name = "operation_type", columnDefinition = "varchar")
    private OperationType operationType;

    @Column(name = "create_time", columnDefinition = "timestamp")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createTime;
}
