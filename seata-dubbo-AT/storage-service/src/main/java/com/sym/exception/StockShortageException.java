package com.sym.exception;

/**
 * 库存不足
 *
 * @author ym.shen
 * @date 2020/4/18 11:57.
 */

public class StockShortageException extends RuntimeException {

    private static final long serialVersionUID = -6199776969199948211L;

    public StockShortageException() {
    }

    public StockShortageException(String message) {
        super(message);
    }

    public StockShortageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockShortageException(Throwable cause) {
        super(cause);
    }

    public StockShortageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
