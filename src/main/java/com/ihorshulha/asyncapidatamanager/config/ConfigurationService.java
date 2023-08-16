package com.ihorshulha.asyncapidatamanager.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableTransactionManagement
@Configuration
@EnableR2dbcRepositories
public class ConfigurationService {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    //    @Bean
//    public ConnectionFactory connectionFactory() {
//        return ConnectionFactories.get("r2dbc:postgresql://localhost:5432/ext_api_db");
//        return new PostgresqlConnectionFactory(
//                PostgresqlConnectionConfiguration.builder()
//                        .host("localhost")
//                        .database("ext_api_db")
//                        .username("postgresql")
//                        .password("postgresql")
//                        .codecRegistrar((connection, allocator, registry) -> {
//                            registry.addFirst(new StringCodec(allocator, PostgresqlObjectId.UNSPECIFIED, PostgresqlObjectId.VARCHAR_ARRAY));
//                            return Mono.empty();
//                        })
//                        .build());
//    }
}

