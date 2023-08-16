package com.ihorshulha.asyncapidatamanager.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("com.ihorshulha.asyncapidatamanager")
@ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class ScheduledConfig {
}
