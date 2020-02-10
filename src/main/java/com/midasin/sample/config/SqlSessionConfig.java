package com.midasin.sample.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SqlSessionConfig {

    private static final String CLASSPATH_SQL_XML = "classpath*:/sql/**/*.xml";
    private static final String CLASSPATH_GENERATE_SQL_XML = "classpath*:/generate-sql/**/*.xml";
    private static final String CLASSPATH_DB_XML = "classpath*:/db/**/*.xml";

    private static final String MYBATIS_CONFIG_XML = "/mybatis-config.xml";

    /**
     * master-sqlSession
     * 
     * @author jsh1026
     * @date 2019-05-22 10:15
     */
    @Bean(name = "master-sqlSession", destroyMethod = "clearCache")
    public SqlSessionTemplate masterSqlSession(
            @Autowired @Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    public SqlSessionFactoryBean masterSqlSessionFactory(
            @Autowired @Qualifier("masterDataSource") DataSource masterDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource);

        List<Resource> resources = new ArrayList<>();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        resources.addAll(Arrays.asList(pathMatchingResourcePatternResolver.getResources(CLASSPATH_GENERATE_SQL_XML)));
        resources.addAll(Arrays.asList(pathMatchingResourcePatternResolver.getResources(CLASSPATH_SQL_XML)));
        resources.addAll(Arrays.asList(pathMatchingResourcePatternResolver.getResources(CLASSPATH_DB_XML)));
        Resource[] mapperLocations = resources.toArray(new Resource[0]);

        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG_XML));
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        return sqlSessionFactoryBean;
    }


    /**
     * customer-sqlSession
     *
     * @author jsh1026
     * @date 2019-05-22 10:15
     */
    @Bean(name = {"customer-sqlSession", "sqlSession"}, destroyMethod = "clearCache")
    public SqlSessionTemplate customerSqlSession(
            @Autowired @Qualifier("customerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public SqlSessionFactoryBean customerSqlSessionFactory(
            @Autowired @Qualifier("customerDataSource") DataSource customerDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(customerDataSource);

        List<Resource> resources = new ArrayList<>();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        resources.addAll(Arrays.asList(pathMatchingResourcePatternResolver.getResources(CLASSPATH_GENERATE_SQL_XML)));
        resources.addAll(Arrays.asList(pathMatchingResourcePatternResolver.getResources(CLASSPATH_SQL_XML)));
        resources.addAll(Arrays.asList(pathMatchingResourcePatternResolver.getResources(CLASSPATH_DB_XML)));
        Resource[] mapperLocations = resources.toArray(new Resource[0]);

        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG_XML));
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        return sqlSessionFactoryBean;
    }
}
