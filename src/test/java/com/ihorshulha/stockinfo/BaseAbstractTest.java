package com.ihorshulha.stockinfo;

import com.ihorshulha.stockinfo.mapper.CompanyMapper;
import com.ihorshulha.stockinfo.mapper.StockMapper;
import com.ihorshulha.stockinfo.client.QueueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest(properties = "scheduling.enabled=false")
@TestConfiguration
public abstract class BaseAbstractTest {

    @Autowired
    public CompanyMapper companyMapper;

    @Autowired
    public StockMapper stockMapper;

    @SpyBean
    public QueueClient queueClient;
}
