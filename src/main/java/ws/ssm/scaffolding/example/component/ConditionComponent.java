package ws.ssm.scaffolding.example.component;

import org.springframework.stereotype.Component;
import ws.ssm.scaffolding.annoation.CustomConditionOnProperty;

/**
 * @author WindShadow
 * @version 2022-06-03.
 */

@CustomConditionOnProperty(prefix = "my", name = "component", havingValue = "true")
@Component
public class ConditionComponent {
}
