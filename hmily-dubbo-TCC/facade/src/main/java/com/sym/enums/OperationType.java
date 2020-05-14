package com.sym.enums;

/**
 * @author shenyanming
 * Created on 2020/5/14 17:37
 */
public enum OperationType {
    /**
     * TCC分布式事务-try操作
     */
    TRY,

    /**
     * TCC分布式事务-confirm操作
     */
    CONFIRM,

    /**
     * TCC分布式事务-cancel操作
     */
    CANCEL;
}
