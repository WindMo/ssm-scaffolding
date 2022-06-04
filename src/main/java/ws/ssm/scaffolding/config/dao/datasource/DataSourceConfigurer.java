package ws.ssm.scaffolding.config.dao.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author WindShadow
 * @version 2021-04-05.
 */

@EnableTransactionManagement
@Configuration(proxyBeanMethods = false)
public class DataSourceConfigurer {

    private static <T> void sureConsumeValue(Supplier<T> supplier, Predicate<T> predicate, Consumer<T> consumer) {

        T value = supplier.get();
        if (predicate.test(value)) {
            consumer.accept(value);
        }
    }

    @Bean
    public DataSourceConfigProperties dataSourceConfigProperties() {

        return new DataSourceConfigProperties();
    }

    @Bean
    public DataSource dataSource(@Autowired DataSourceConfigProperties dataSourceProperties) throws Exception {

        Map<String, String> dataSourceParamMap = new HashMap<>(6);
        dataSourceParamMap.put("driverClassName", dataSourceProperties.getDriverClassName());
        dataSourceParamMap.put("url", dataSourceProperties.getUrl());
        sureConsumeValue(dataSourceProperties::getUsername, StringUtils::hasText,
                v -> dataSourceParamMap.put("username", v));
        sureConsumeValue(dataSourceProperties::getPassword, StringUtils::hasText,
                v -> dataSourceParamMap.put("password", v));
        return DruidDataSourceFactory.createDataSource(dataSourceParamMap);
    }

    @Bean
    public TransactionManager transactionManager(@Autowired DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
