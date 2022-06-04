package ws.ssm.scaffolding;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import ws.ssm.scaffolding.config.web.SpringMvcConfigurer;
import ws.ssm.scaffolding.initializer.ExampleDemoApplication;

/**
 * 由于过滤器配置在{@link WebApplicationInitializer}接口的实现类{@link ExampleDemoApplication}中，
 * 目前没找到使用该接口进行初始化操作进行单元测试的方法，所以http接口的单元测试中过滤器是不生效的
 *
 * @author WindShadow
 * @version 2021-04-08.
 */

@SpringJUnitWebConfig(classes = {SpringMvcConfigurer.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleDemoApplicationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void initMockMvc() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void emptyTest() {

    }
}



