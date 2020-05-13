package com.sym;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

/**
 * @author ym.shen
 * @date 2020/4/18 11:36.
 */

public class MainTest {

    @Test
    public void test0001(){
        String random = RandomStringUtils.randomNumeric(15);
        System.out.println(random);
    }
}
