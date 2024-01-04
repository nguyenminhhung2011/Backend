package com.fitlife.app.Config.Database;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.fitlife.app.Repository")
@EntityScan(basePackages = "com.fitlife.app.Model")
@EnableTransactionManagement
@Slf4j
public class JpaConfig  implements EnvironmentAware {
    private static final String ENV_HIBERNATE_DIALECT = "spring.jpa.properties.hibernate.dialect";
    private static final String ENV_HIBERNATE_SHOW_SQL = "spring.jpa.show-sql";
    private static final String ENV_HIBERNATE_FORMAT_SQL = "spring.jpa.properties.hibernate.format_sql";

    private static final String ENV_HIBERNATE_DDL_AUTO = "spring.jpa.hibernate.ddl-auto";


    private Environment env;

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(
                Objects.requireNonNull(env.getProperty("spring.datasource.url")),
                Objects.requireNonNull(env.getProperty("spring.datasource.username")),
                Objects.requireNonNull(env.getProperty("spring.datasource.password"))
        );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.fitlife.app.Model");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    private Properties jpaProperties() {
        Properties extraProperties = new Properties();
        extraProperties.put(ENV_HIBERNATE_FORMAT_SQL, env.getProperty(ENV_HIBERNATE_FORMAT_SQL));
        extraProperties.put(ENV_HIBERNATE_SHOW_SQL, env.getProperty(ENV_HIBERNATE_SHOW_SQL));
        extraProperties.put(ENV_HIBERNATE_DDL_AUTO, env.getProperty(ENV_HIBERNATE_DDL_AUTO));
        if (log.isDebugEnabled()) {
            log.debug(" hibernate.dialect @" + env.getProperty(ENV_HIBERNATE_DIALECT));
        }
        if (env.getProperty(ENV_HIBERNATE_DIALECT) != null) {
            extraProperties.put(ENV_HIBERNATE_DIALECT, env.getProperty(ENV_HIBERNATE_DIALECT));
        }
        return extraProperties;
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.env = environment;
    }
}
