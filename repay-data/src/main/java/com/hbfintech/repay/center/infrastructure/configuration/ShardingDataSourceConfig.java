package com.hbfintech.repay.center.infrastructure.configuration;

import com.github.pagehelper.PageHelper;
import com.hbfintech.frame.common.utils.OptimisticPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import({OptimisticPlugin.class,PageHelper.class})
@MapperScan(basePackages = {"com.hbfintech.repay.center.**.shardingdao"}, sqlSessionFactoryRef = "dataSqlSessionFactory1")
public class ShardingDataSourceConfig {
    private final static String SHARDING_MAPPER_LOCATION = "classpath*:shardingmapper/*Mapper.xml";

    @Autowired
    private OptimisticPlugin optimisticPlugin;

    @Autowired
    private PageHelper pageHelper;


    /**
     * 返回data1数据库的会话工厂
     *
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean(name = "dataSqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
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
     * 返回data1数据库的会话模板
     *
     * @param sessionFactory
     * @return
     */
    @Bean(name = "dataSqlSessionTemplate1")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dataSqlSessionFactory1") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    /**
     * 返回data1数据库的事务
     *
     * @param ds
     * @return
     */
    @Bean(name = "dataTransactionManager1")
    public DataSourceTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
