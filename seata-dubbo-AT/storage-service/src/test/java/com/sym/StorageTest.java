package com.sym;

import com.sym.api.IStorageService;
import com.sym.domain.Commodity;
import com.sym.dto.CommodityDTO;
import com.sym.repository.IStorageRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

/**
 * @author ym.shen
 * Created on 2020/4/17 12:35
 */
public class StorageTest {

    private IStorageRepository storageRepository;
    private IStorageService storageService;

    @Before
    public void before(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dubbo-storage.xml");
        storageRepository = applicationContext.getBean(IStorageRepository.class);
        storageService = applicationContext.getBean(IStorageService.class);
    }

    @Test
    public void insertTest(){
        Commodity entity = new Commodity();
        LocalDateTime now = LocalDateTime.now();
        entity.setCommodityCode("a0001")
                .setCommodityName("外星人笔记本")
                .setStock(10)
                .setCreateTime(now)
                .setUpdateTime(now);
        storageRepository.save(entity);
    }

    @Test
    public void updateTest(){
        CommodityDTO dto = new CommodityDTO();
        dto.setCommodityCode("a0001").setQuantity(2);
        storageService.deduct(dto);
    }
}
