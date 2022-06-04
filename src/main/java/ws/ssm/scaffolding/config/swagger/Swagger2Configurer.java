package ws.ssm.scaffolding.config.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author WindShadow
 * @version 2021-04-04.
 */
@EnableWebMvc
@ComponentScan("springfox")
@EnableSwagger2
@Configuration(proxyBeanMethods = false)
public class Swagger2Configurer implements WebMvcConfigurer {

    private static ApiInfo buildApiInfo(Swagger2ConfigProperties swagger2ConfigProperties) {
        return new ApiInfoBuilder()
                .title(swagger2ConfigProperties.getApiInfoTitle())
                .description(swagger2ConfigProperties.getApiInfoDescription())
                .version(swagger2ConfigProperties.getApiInfoVersion())
                .build();
    }

    @Bean
    public Swagger2ConfigProperties swagger2ConfigProperties() {

        return new Swagger2ConfigProperties();
    }

    /**
     * api配置
     *
     * @return
     */
    @Bean
    public Docket api(@Autowired Swagger2ConfigProperties swagger2ConfigProperties) {

        ApiSelectorBuilder selectorBuilder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo(swagger2ConfigProperties))
                .select();
        if (swagger2ConfigProperties.getEnable()) {
            selectorBuilder.apis(RequestHandlerSelectors.basePackage(swagger2ConfigProperties.getBasePackage()));
        } else {
            selectorBuilder.apis(RequestHandlerSelectors.none());
        }
        return selectorBuilder.paths(PathSelectors.ant(swagger2ConfigProperties.getAntPattern())).build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        this.addSwagger2ResourceHandlers(registry);
    }

    /**
     * Swagger2静态资源处理
     *
     * @param registry 资源处理器注册器
     */
    private void addSwagger2ResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(0);
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
                .setCachePeriod(0);
    }
}
