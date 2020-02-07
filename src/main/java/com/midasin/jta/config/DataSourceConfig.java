package com.midasin.jta.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource masterDataSource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(createDataSource("jdbc:mysql://localhost:13306/jta"));

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(0, createDataSource("jdbc:mysql://localhost:13306/jta"));
        targetDataSources.put(1, createDataSource("jdbc:mysql://localhost:23306/jta"));
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    @Bean
    public DataSource customerDataSource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(createDataSource("jdbc:mysql://localhost:13306/jta"));

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(0, createDataSource("jdbc:mysql://localhost:13306/jta"));
        targetDataSources.put(1, createDataSource("jdbc:mysql://localhost:23306/jta"));
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    private DataSource createDataSource(String url) {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        properties.setProperty("url", url);

        //XA 처리를 위한 MySQL 드라이버 변경: AtomikosDataSourceBean 은 XADataSource 인터페이스를 참조하고 있다.
        dataSource.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        //XA 리소스를 식별할 고유 이름을 지정한다. 각 데이터소스별 고유한 값을 지정해도 되고 url이 각각 다르다면 식별 가능한 url로 지정해도 무방하다.
        dataSource.setUniqueResourceName(url);
        dataSource.setXaProperties(properties);

        return dataSource;
    }

    @Bean
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        //문제 해결을 위한 접근 A 항목에 해당하는 내용이다.
        //MyBatis에서 SQLSession에서 커넥션을 얻어오는 TransactionFactory 구현체를 TransactionSynchronizationManager를 이용하지 않는 ManagedTransactionFactory로 교체한다.
        //AutoConfig로 설정하거나 아무것도 설정하지 않으면 기본값은 SpringManagedTransactionFactory로 주입된다.
        factory.setTransactionFactory(new ManagedTransactionFactory());
        return factory.getObject();
    }
}
