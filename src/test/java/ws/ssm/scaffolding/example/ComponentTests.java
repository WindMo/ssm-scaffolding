package ws.ssm.scaffolding.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ws.ssm.scaffolding.ExampleDemoApplicationTest;
import ws.ssm.scaffolding.example.component.ConditionComponent;

/**
 * @author WindShadow
 * @version 2022-06-03.
 */

@Slf4j
public class ComponentTests extends ExampleDemoApplicationTest {

    /** 配置条件 my.component=true | false */
    @Autowired(required = false)
    private ConditionComponent conditionComponent;

    @Test
    public void conditionComponentTest() {

        log.info("has conditionComponent: {}", conditionComponent != null);
    }
}
