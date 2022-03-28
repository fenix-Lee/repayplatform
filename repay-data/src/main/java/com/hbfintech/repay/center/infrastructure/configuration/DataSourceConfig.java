package com.hbfintech.repay.center.infrastructure.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.hbfintech.frame.common.utils.OptimisticPlugin;
import org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
@Import({OptimisticPlugin.class, PageHelper.class})
@MapperScan(basePackages = {"com.hbfintech.repay.center.**", "com.zxhy.frame.**.dao"}, sqlSessionFactoryRef = "dataSqlSessionFactory")
public class DataSourceConfig {

    private final OptimisticPlugin optimisticPlugin;

    private final PageHelper pageHelper;

    private final static String REPAYCENTER_MAPPER_LOCATION = "classpath*:mapper/**/*Mapper.xml";
    private final static String FINTECH_MAPPER_LOCATION = "classpath*:com/zxhy/frame/**/mapper/*Mapper.xml";

    @Autowired
    public DataSourceConfig(OptimisticPlugin optimisticPlugin, PageHelper pageHelper) {
        this.optimisticPlugin = optimisticPlugin;
        this.pageHelper = pageHelper;
    }

    /**
     * 返回data1数据库的数据源
     *
     * @return
     */
    @Bean(name = "database")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.ds")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 返回data1数据库的会话工厂
     *
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean(name = "dataSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("database") DataSource ds) throws Exception {
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
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("reasonable", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        pageHelper.setProperties(properties);
        bean.setPlugins(new Interceptor[] {pageHelper} );
        bean.setPlugins(new Interceptor[]{optimisticPlugin,pageHelper});
        bean.setDataSource(ds);
        bean.setConfiguration(configuration);

        List<Resource> resourceList = new ArrayList<>();

        resourceList.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
                .getResources(REPAYCENTER_MAPPER_LOCATION)));
        resourceList.addAll(Arrays.asList(new PathMatchingResourcePatternResolver()
                .getResources(FINTECH_MAPPER_LOCATION)));

        Resource[] resources = new Resource[resourceList.size()];
        resources = resourceList.toArray(resources);
        bean.setMapperLocations(resources);
        return bean.getObject();
    }

    /**
     * 返回data1数据库的会话模板
     *
     * @param sessionFactory
     * @return
     */
    @Bean(name = "dataSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dataSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    /**
     * 返回data1数据库的事务
     *
     * @param ds
     * @return
     */
    @Bean(name = "dataTransactionManager0")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("database") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
