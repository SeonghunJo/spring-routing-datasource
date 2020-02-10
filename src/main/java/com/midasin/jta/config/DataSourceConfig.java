package com.midasin.jta.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource masterDataSource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(
                createXADataSource("jdbc:mysql://localhost:13306/jta", "root", "root"));

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("company1.midasweb.net",
                createXADataSource("jdbc:mysql://localhost:13306/jta", "root", "root"));
        targetDataSources.put("company2.midasweb.net",
                createXADataSource("jdbc:mysql://localhost:23306/jta", "root", "root"));
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    @Bean
    public DataSource customerDataSource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(
                createXADataSource("jdbc:mysql://localhost:13306/jta", "root", "root"));

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("company1.midasweb.net",
                createXADataSource("jdbc:mysql://localhost:13306/jta", "root", "root"));
        targetDataSources.put("company2.midasweb.net",
                createXADataSource("jdbc:mysql://localhost:23306/jta", "root", "root"));
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    private DataSource createXADataSource(String url, String username, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
