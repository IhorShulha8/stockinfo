package com.ihorshulha.stockinfo.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {
}
