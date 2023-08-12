package com.ihorshulha.asyncapidatamanager.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.codec.PostgresqlObjectId;
import io.r2dbc.postgresql.codec.StringCodec;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.client.RestTemplate;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import reactor.core.publisher.Mono;

@EnableScheduling
@Configuration
@EnableR2dbcRepositories(basePackages = {"com.ihorshulha.asyncapidatamanager"})
@EntityScan(basePackages = {"com.ihorshulha.asyncapidatamanager.entity"})
public class ConfigurationService {

    @Bean
    public ConnectionFactory connectionFactory() {
//        return ConnectionFactories.get("r2dbc:postgresql://localhost:5432/ext_api_db");
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .database("ext_api_db")
                        .username("postgresql")
                        .password("postgresql")
                        .codecRegistrar((connection, allocator, registry) -> {
                            registry.addFirst(new StringCodec(allocator, PostgresqlObjectId.UNSPECIFIED, PostgresqlObjectId.VARCHAR_ARRAY));
                            return Mono.empty();
                        })
                        .build());
    }

    @Bean
    ConnectionFactoryInitializer initializer() {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory());
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    public DatabaseClient databaseClient() {
        return DatabaseClient.create(connectionFactory());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    TransactionAwareConnectionFactoryProxy transactionAwareConnectionFactoryProxy(ConnectionFactory
                                                                                          connectionFactory) {
        return new TransactionAwareConnectionFactoryProxy(connectionFactory);
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager);
    }
}

