package ws.ssm.scaffolding.config.dao.mybatis;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import ws.ssm.scaffolding.annoation.EnableMapperScan;
import ws.ssm.scaffolding.config.ConstantOfConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Mybatis配置，默认开启分页插件
 *
 * @author WindShadow
 * @version 2021-04-04.
 */

@EnableMapperScan("ws.ssm.scaffolding.example.mapper")
@Configuration(proxyBeanMethods = false)
public class MybatisConfigurer {

    private static Resource[] getMapperXMLResources(String mapperLocation) throws IOException {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources(mapperLocation);
    }

    @Bean
    public MybatisConfigProperties mybatisConfigProperties() {

        return new MybatisConfigProperties();
    }

    /**
     * 自定义其它mybatis配置
     *
     * @return Configuration of Mybatis
     */
    @Bean
    public org.apache.ibatis.session.Configuration customMybatisConfiguration() {

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 指定日志实现
        configuration.setLogImpl(Slf4jImpl.class);
        return configuration;
    }

    @Bean(ConstantOfConfig.SQL_SESSION_FACTORY_BEAN_NAME)
    public SqlSessionFactory sqlSessionFactory(
            @Autowired DataSource dataSource,
            @Autowired MybatisConfigProperties mybatisConfigProperties,
            @Autowired PageInterceptor pageHelper,
            @Autowired(required = false) org.apache.ibatis.session.Configuration configuration) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setMapperLocations(getMapperXMLResources(mybatisConfigProperties.getMapperLocation()));
        factoryBean.setDataSource(dataSource);
        // 设置pagehelper分页插件
        factoryBean.setPlugins(pageHelper);
        if (configuration != null) {
            factoryBean.setConfiguration(configuration);
        }
        return factoryBean.getObject();
    }

    @Bean
    public PageInterceptor pageInterceptor(@Autowired MybatisConfigProperties mybatisConfigProperties) {

        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        // 配置数据库方言
        properties.setProperty("helperDialect", mybatisConfigProperties.getPageHelperDialect().getDialect());
        properties.setProperty("reasonable ", Boolean.TRUE.toString());
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
