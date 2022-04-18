package com.hbfintech.repay.center.infrastructure.configuration;

import com.github.pagehelper.PageHelper;
import com.hbfintech.frame.common.utils.OptimisticPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConditionalOnMissingBean(name = "localDataSource")
@Import({OptimisticPlugin.class,PageHelper.class})
@MapperScan(basePackages = {"com.hbfintech.repay.center.**.shardingdao"}, sqlSessionFactoryRef = "shardingSqlSessionFactory")
public class ShardingDataSourceConfig {
    private final static String SHARDING_MAPPER_LOCATION = "classpath*:shardingmapper/*Mapper.xml";

    @Resource
    private OptimisticPlugin optimisticPlugin;

    @Resource
    private PageHelper pageHelper;

    /**
     * shardingSqlSessionFactory creator
     *
     * @param ds datasource
     * @return session factory
     * @throws Exception exception
     */
    @Bean(name = "shardingSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("shardingDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("reasonable", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        pageHelper.setProperties(properties);
        bean.setPlugins(new Interceptor[]{optimisticPlugin,pageHelper});
        bean.setDataSource(ds);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SHARDING_MAPPER_LOCATION));
        return bean.getObject();
    }

    /**
     * shardingSqlSessionTemplate creator
     *
     * @param sessionFactory sql session factory
     * @return session template
     */
    @Bean(name = "shardingSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("shardingSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    /**
     * shardingTransactionManager creator
     *
     * @param ds datasource
     * @return transaction manager
     */
    @Bean(name = "shardingTransactionManager")
    public DataSourceTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
