package com.sym;

import com.sym.domain.AbcAccount;
import com.sym.repository.AbcAccountRepository;
import com.sym.repository.AbcBankLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author shenyanming
 * @date 2020/5/14 22:20.
 */

public class RepositoryTest {

    private ApplicationContext applicationContext;
    private AbcAccountRepository abcAccountRepository;
    private AbcBankLogRepository abcBankLogRepository;

    @Before
    public void before(){
        applicationContext = new AnnotationConfigApplicationContext(AbcBankApplication.class);
        abcAccountRepository = applicationContext.getBean(AbcAccountRepository.class);
        abcBankLogRepository = applicationContext.getBean(AbcBankLogRepository.class);
    }

    @Test
    public void test(){
        System.out.println(applicationContext);
    }

    @Test
    public void test01(){
        AbcAccount abcAccount = new AbcAccount();
        abcAccount.setCellphone("1111111111")
                .setUsername("zhangsan")
                .setTotalBalance(BigDecimal.valueOf(666))
                .setUpdateTime(LocalDateTime.now());
        AbcAccount save = abcAccountRepository.save(abcAccount);
        System.out.println(save);
    }
}
