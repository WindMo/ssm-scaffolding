package ws.ssm.scaffolding.annoation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author WindShadow
 * @version 2022-06-03.
 */

public class CustomOnProperty implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        Map<String, Object> attributes = annotatedTypeMetadata.getAnnotationAttributes(CustomConditionOnProperty.class.getName());
        Assert.notNull(attributes, "Not Found CustomConditionOnProperty Annotation");
        String prefix = (String) attributes.get("prefix");
        String name = (String) attributes.get("name");
        String havingValue = (String) attributes.get("havingValue");
        boolean matchIfMissing = (boolean) attributes.get("matchIfMissing");

        String key = prefix + "." + name;
        Environment env = conditionContext.getEnvironment();
        if (env.containsProperty(key)) {

            return havingValue.equals(env.getProperty(key));
        }
        return matchIfMissing;
    }
}
