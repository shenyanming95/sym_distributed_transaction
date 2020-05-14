package com.sym.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库配置
 *
 * @author shenyanming
 * @date 2020/5/14 22:04.
 */
@Configuration
public class IcbcServiceDataSourceConfig {

    /**
     * 配置数据源
     */
    @Bean
    public DataSource dataSource() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("config/hikari.properties"));
        HikariConfig config = new HikariConfig(properties);
        return new HikariDataSource(config);
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.sym.domain");
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter());

        entityManagerFactory.setJpaPropertyMap(jpaProperties());
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private JpaVendorAdapter hibernateJpaVendorAdapter(){
        HibernateJpaVendorAdapter JpaVendorAdapter = new HibernateJpaVendorAdapter();
        JpaVendorAdapter.setGenerateDdl(true);
        JpaVendorAdapter.setShowSql(true);
        return JpaVendorAdapter;
    }

    private Map<String,String> jpaProperties(){
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.hbm2ddl.auto", "validate");
        return jpaProperties;
    }
}
