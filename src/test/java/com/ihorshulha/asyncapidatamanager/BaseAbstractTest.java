package com.ihorshulha.asyncapidatamanager;

import com.ihorshulha.asyncapidatamanager.mapper.CompanyMapper;
import com.ihorshulha.asyncapidatamanager.mapper.StockMapper;
import com.ihorshulha.asyncapidatamanager.client.QueueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;


@SpringBootTest(properties = "scheduling.enabled=false")
@TestConfiguration
public abstract class BaseAbstractTest {

    @Autowired
    public CompanyMapper companyMapper;

    @Autowired
    public StockMapper stockMapper;

    @Autowired
    public QueueClient queueClient;
}
