package com.ihorshulha.stockinfo;

import com.ihorshulha.stockinfo.client.ExApiExchangeClientImpl;
import com.ihorshulha.stockinfo.mapper.CompanyMapper;
import com.ihorshulha.stockinfo.mapper.StockMapper;
import com.ihorshulha.stockinfo.repository.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(properties = "scheduling.enabled=false")
@TestConfiguration
public abstract class BaseAbstractTest {

    @MockBean
    public ExApiExchangeClientImpl apiClient;
    @Autowired
    public CompanyMapper companyMapper;
    @Autowired
    public StockMapper stockMapper;
    @MockBean
    public CustomRepository customRepository;
}
