package ws.ssm.scaffolding.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * springmvc配置类
 *
 * @author WindShadow
 * @version 2021-4-4.
 */

@PropertySource("classpath:ApplicationProperties.properties") // 至少激活一个配置文件
@ComponentScan(basePackages = "ws.ssm.scaffolding")
@EnableAspectJAutoProxy // 开启AOP动态代理
@EnableWebMvc // 标注此配置为mvc配置，前提实现WebMvcConfigurer接口
@Configuration(proxyBeanMethods = false)
public class SpringMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        for (HttpMessageConverter<?> converter : converters) {

            if (converter instanceof MappingJackson2HttpMessageConverter) {

                // 设置消息转换器
                ObjectMapper objectMapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
                SimpleModule simpleModule = new SimpleModule();
                // Long数据类型转换成String响应，js以double接收Long数据可能造成精度丢失（Long长度大于17位时），一般开发必设置！
                simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
                simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
                objectMapper.registerModule(simpleModule);
            }
        }
    }
}
