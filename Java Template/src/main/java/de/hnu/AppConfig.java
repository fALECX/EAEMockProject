package de.hnu;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Spring Boot JPA Configuration für Car-Sharing Anwendung.
 * Konfiguriert Derby Datenbank mit EclipseLink JPA Provider.
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class AppConfig {

    @Bean
    @ConfigurationProperties("app.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create().type(HikariDataSource.class);
        return dataSourceBuilder.build();
    }

    @Bean
    @ConfigurationProperties("app.jpa")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("de.hnu.model");
        factory.setDataSource(dataSource());

        // EclipseLink Weaving deaktivieren, um LoadTimeWeaver Problem zu vermeiden
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("eclipselink.weaving", "false");
        jpaProperties.put("eclipselink.logging.level", "FINE");
        factory.setJpaPropertyMap(jpaProperties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return txManager;
    }

}
