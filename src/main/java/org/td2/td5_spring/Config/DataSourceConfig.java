package org.td2.td5_spring.Config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/product_management_db")
                .username("product_manager_user")
                .password("123456")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}