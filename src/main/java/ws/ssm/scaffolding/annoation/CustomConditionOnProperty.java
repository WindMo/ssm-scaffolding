package ws.ssm.scaffolding.annoation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 自定义实现基于配置的条件注入注解，在mvc环境下配置文件的加载顺序是不确定的，
 * 所以使用此注解时确保{@link org.springframework.core.env.Environment#getProperty(String)}的读取结果是你想要的
 *
 * @author WindShadow
 * @version 2022-06-03.
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(CustomOnProperty.class)
public @interface CustomConditionOnProperty {

    /**
     * 前缀
     */
    String prefix() default "";

    /**
     * 配置名（一般一个就够用了无需设置为数组）
     */
    String name();

    /**
     * 预期匹配的值
     */
    String havingValue() default "";

    /**
     * 不存在对应键值时使用此属性作为匹配结果
     */
    boolean matchIfMissing() default false;
}
