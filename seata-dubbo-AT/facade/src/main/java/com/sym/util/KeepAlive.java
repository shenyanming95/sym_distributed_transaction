package com.sym.util;

import lombok.SneakyThrows;

/**
 * @author ym.shen
 * @date 2020/4/18 11:46.
 */

public class KeepAlive {
    private final static Object LOCK_OBJECT = new Object();

    @SneakyThrows
    public synchronized static void sync(){
        // Runtime.getRuntime().addShutdownHook(new Thread(KeepAlive::exit));
        LOCK_OBJECT.wait();
    }

    public synchronized static void exit(){
        LOCK_OBJECT.notifyAll();
    }
}
