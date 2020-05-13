package com.sym.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 库存DTO
 *
 * @author shenyanming
 * Created on 2020/5/13 15:17
 */
@Data
@ToString
@Accessors(chain = true)
public class CommodityDTO implements Serializable {
    private static final long serialVersionUID = -3728367837387689540L;

    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品编号
     */
    private String commodityCode;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 扣除数量
     */
    private Integer quantity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
