package com.ihorshulha.stockinfo.util;

import com.ihorshulha.stockinfo.BaseAbstractTest;
import com.ihorshulha.stockinfo.client.ExApiExchangeClient;
import com.ihorshulha.stockinfo.dto.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest(properties = "scheduling.enabled=false")
public class ExlApiExchangeClientTest extends BaseAbstractTest {
    @SpyBean
    private ExApiExchangeClient apiClient;
    @MockBean
    private RestTemplate restTemplate;
    ResponseEntity<List<CompanyDTO>> entity;

    @Test
    void whenGetCompaniesSuccessful() {

    }

    @Test
    void getOneCompanyStock() {
    }

    @Test
    void getRefDataUrl() {
    }

    @Test
    void getStockPriceUrl() {
    }
}