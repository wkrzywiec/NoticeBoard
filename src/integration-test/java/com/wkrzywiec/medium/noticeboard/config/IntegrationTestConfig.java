package com.wkrzywiec.medium.noticeboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@TestConfiguration
public class IntegrationTestConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource){
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql/test-data.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}
