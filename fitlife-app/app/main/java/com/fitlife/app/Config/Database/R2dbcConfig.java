package com.fitlife.app.Config.Database;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.fitlife.app.ReactiveRepository")
public class R2dbcConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host("localhost")
                        .port(5432)
                        .database("DataSA")
                        .username("postgres")
                        .password("root")
                        .build()
        );
    }
    @Bean
    DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .namedParameters(true)
                .build();
    }
}
