package ws.ssm.scaffolding.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ws.ssm.scaffolding.config.dao.primarykey.IdWork;
import ws.ssm.scaffolding.config.dao.primarykey.Snowflakes;

/**
 * @author WindShadow
 * @version 2021-4-4.
 */
@Configuration
public class BusinessConfig {

    @Scope("prototype")
    @Bean
    public IdWork idWork() {

        return new Snowflakes(1L, 1L, 1L);
    }
}
