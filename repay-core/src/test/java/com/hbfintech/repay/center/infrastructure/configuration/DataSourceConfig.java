package com.hbfintech.repay.center.infrastructure.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.hbfintech.repay.center.infrastructure.properties.LocalProperties;
import org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final static String MAPPER_LOCATION = "classpath*:*/*Mapper.xml";

    @Resource
    private LocalProperties properties;

    @Bean({"localDataSource", "shardingDataSource"})
    @Primary
    public DataSource getDataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(properties.getUrl());
        ds.setDriverClassName(properties.getDriver());
        ds.setUsername(properties.getUsername());
        ds.setPassword(properties.getPassword());
        return ds;
    }

    @Bean(name = "localSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("localDataSource") DataSource ds) throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseGeneratedKeys(true);
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(true);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setUseColumnLabel(true);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        configuration.setDefaultStatementTimeout(25000);
        configuration.setProxyFactory(new JavassistProxyFactory());
        configuration.setLogImpl(Slf4jImpl.class);

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setConfiguration(configuration);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "localSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("localSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean(name = "localTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("localDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
