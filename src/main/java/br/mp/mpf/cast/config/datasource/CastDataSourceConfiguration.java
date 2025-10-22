package br.mp.mpf.cast.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;


/**
 * Concentra as configurações manuais relacionadas ao datasource principal,
 * bem como a adição dos listeners de auditoria
 */
@Configuration
public class CastDataSourceConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties("spring.jpa")
    public JpaProperties castJpaProperties() {

        return new JpaProperties();
    }


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties castDataSourceProperties() {

        return new DataSourceProperties();
    }


    @Bean("castDS")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource castDataSource() {

        return castDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    @Bean
    public JpaVendorAdapter castJpaVendorAdapter(
        @Qualifier("castDS") DataSource dataSource,
        @Qualifier("castJpaProperties") JpaProperties jpaProperties) {

        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());

        return adapter;
    }


    @Bean
    public EntityManagerFactoryBuilder castEntityManagerFactoryBuilder(
        @Qualifier("castJpaVendorAdapter") JpaVendorAdapter jpaVendorAdapter,
        @Qualifier("castJpaProperties") JpaProperties jpaProperties) {

        return new EntityManagerFactoryBuilder(
            jpaVendorAdapter,
            jpaProperties.getProperties(),
            null);
    }


    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        @Qualifier("castEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
        @Qualifier("castDS") DataSource ds) {

        return builder
            .dataSource(ds)
            .packages("br.mp.mpf.cast.model")
            .persistenceUnit("castPU")
            .build();
    }


    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
        @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

}
