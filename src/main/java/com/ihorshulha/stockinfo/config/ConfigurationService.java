package com.ihorshulha.stockinfo.config;

import com.ihorshulha.stockinfo.exception.RestTemplateResponseErrorHandler;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
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

import static io.r2dbc.spi.ConnectionFactoryOptions.*;


@Configuration
@EnableR2dbcRepositories
public class ConfigurationService {

//    @Value("${api.database.host}")
//    private String host;
//    @Value("${api.database.port}")
//    private int port;
//    @Value("${api.database.name}")
//    private String name;
//    @Value("${api.database.username}")
//    private String username;
//    @Value("${api.database.password}")
//    private String password;

//    @Bean
//    public ConnectionFactory connectionFactory() {
//
//        return new PostgresqlConnectionFactory(
//                PostgresqlConnectionConfiguration.builder()
//                        .host(host)
//                        .port(port)
//                        .database(name)
//                        .username(username)
//                        .password(password)
//                        .build());
//    }

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
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return ConnectionFactories.get("r2dbc:postgresql://%s:%s@%s:%s/%s".formatted(username, password, host, port, name));
//
////        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
////                .option(DRIVER, "postgresql")
////                .option(HOST, host)
////                .option(PORT, port)  // optional, defaults to 5432
////                .option(USER, username)
////                .option(PASSWORD, password)
////                .option(DATABASE, name)  // optional
////                .build());
//    }
}

