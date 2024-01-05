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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
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
    private static final String ENV_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String ENV_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String ENV_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String ENV_HIBERNATE_DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String NON_CONTEXTUAL_CREATION = "hibernate.jdbc.lob.non_contextual_creation";
    private static final String GENERATE_DDL = "spring.jpa.generate-ddl";

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
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.fitlife.app.Model");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        emf.setJpaProperties(jpaProperties());
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        return emf;
    }

    private Properties jpaProperties() {
        Properties extraProperties = new Properties();

        extraProperties.setProperty(ENV_HIBERNATE_FORMAT_SQL, env.getProperty(ENV_HIBERNATE_FORMAT_SQL));
        extraProperties.setProperty(ENV_HIBERNATE_SHOW_SQL, env.getProperty(ENV_HIBERNATE_SHOW_SQL));
        extraProperties.setProperty(ENV_HIBERNATE_DDL_AUTO, env.getProperty(ENV_HIBERNATE_DDL_AUTO));
        extraProperties.setProperty(NON_CONTEXTUAL_CREATION, env.getProperty(NON_CONTEXTUAL_CREATION));
        extraProperties.setProperty(GENERATE_DDL, env.getProperty(GENERATE_DDL));


        if (log.isDebugEnabled()) {
            log.debug(" hibernate.dialect @" + env.getProperty(ENV_HIBERNATE_DIALECT));
        }
        if (env.getProperty(ENV_HIBERNATE_DIALECT) != null) {
            extraProperties.setProperty(ENV_HIBERNATE_DIALECT, env.getProperty(ENV_HIBERNATE_DIALECT));
        }
        return extraProperties;
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.env = environment;
    }
}
