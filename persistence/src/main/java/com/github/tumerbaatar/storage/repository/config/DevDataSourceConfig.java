package com.github.tumerbaatar.storage.repository.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

//@Profile("dev")
@Slf4j
@PropertySource("classpath:hibernate-dev.properties")
@Configuration
@EnableJpaRepositories(basePackages = "org.github.tumerbaatar.storage.repository")
@EnableTransactionManagement
public class DevDataSourceConfig {
    private Environment env;

    @Autowired
    public DevDataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DriverManagerDataSource devDataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("datasource.driver-class-name"));
        driver.setUrl(env.getProperty("datasource.url"));
        driver.setUsername(env.getProperty("datasource.username"));
        driver.setPassword(env.getProperty("datasource.password"));
        return driver;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.DEFAULT);// Database.POSTGRESQL);
        vendorAdapter.setShowSql(true);

        Properties properties = new Properties();
        String sqlDialect = "org.hibernate.dialect.PostgreSQL9Dialect";
        properties.setProperty("dialect", sqlDialect);
//        String hbm2ddl = "javax.persistence.schema-generation.database.action";
//        String loadScriptSource = "javax.persistence.sql-load-script-source";

//        properties.setProperty(hbm2ddl, env.getProperty(hbm2ddl));
//        properties.setProperty(loadScriptSource, env.getProperty(loadScriptSource));

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("org.github.tumerbaatar.storage.model");
        factory.setDataSource(devDataSource());
        factory.setJpaProperties(properties);

        return factory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }
}
