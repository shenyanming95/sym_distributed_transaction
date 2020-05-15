package com.sym;

import com.sym.api.IIcbcBank;
import com.sym.dto.AccountDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * 工商银行(转账)服务启动类
 *
 * @author shenyanming
 * @date 2020/5/15 5:15.
 */
@Configuration
@ComponentScan({"com.sym","org.dromara.hmily.*"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableJpaRepositories("com.sym.repository")
//@EnableTransactionManagement
public class IcbcBankApplication {
    public static void main(String[] args) throws IOException {
        // 启动服务
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(IcbcBankApplication.class);

        // 同时发起调用
        IIcbcBank icbcBank = applicationContext.getBean(IIcbcBank.class);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setSerialNumber(UUID.randomUUID().toString())
                .setAmount(BigDecimal.valueOf(250))
                .setBankCodeFrom("ICBC")
                .setBankCodeTo("ABC")
                .setCellPhoneFrom("12580")
                .setCellPhoneTo("10000");
        icbcBank.transferOut(accountDTO);

        // 保证服务运行
        System.in.read();
    }
}
