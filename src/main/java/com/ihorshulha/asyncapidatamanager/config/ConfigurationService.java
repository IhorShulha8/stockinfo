package com.ihorshulha.asyncapidatamanager.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableR2dbcRepositories
public class ConfigurationService {

    @Value("${api.database.host}")
    protected String host;
    @Value("${api.database.port}")
    protected int port;
    @Value("${api.database.name}")
    protected String name;
    @Value("${api.database.username}")
    protected String username;
    @Value("${api.database.password}")
    protected String password;

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .namedParameters(true)
                .build();
    }

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
////        return ConnectionFactories.get("r2dbc:postgresql://%s:%s@%s:%s/%s".formatted(username, password, host, port, name));
//
//        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
//                .option(DRIVER, "postgresql")
//                .option(HOST, host)
//                .option(PORT, 5432)  // optional, defaults to 5432
//                .option(USER, username)
//                .option(PASSWORD, password)
//                .option(DATABASE, name)  // optional
//                .build());
//
//    }
}

