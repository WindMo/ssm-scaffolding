package ws.ssm.scaffolding.initializer;

import ws.ssm.scaffolding.config.web.SpringMvcConfigurer;
import ws.ssm.scaffolding.example.fiter.MyFilter;

/**
 * @author WindShadow
 * @version 2022-06-04.
 */

public class ExampleDemoApplication extends BaseSpringMvcAnnotationConfigDispatcherServletInitializer {

    /**
     * 初始化要代理的过滤器
     */
    @Override
    protected void initProxyFilter() {

        this.registerProxyFilter(MyFilter.class, "/*");
    }

    /**
     * Specify {@code @Configuration} and/or {@code @Component} classes for the
     * {@linkplain #createServletApplicationContext() Servlet application context}.
     *
     * @return the configuration for the Servlet application context, or
     * {@code null} if all configuration is specified through root config classes.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfigurer.class};
    }
}
