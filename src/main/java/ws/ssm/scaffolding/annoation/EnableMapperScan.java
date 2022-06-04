package ws.ssm.scaffolding.annoation;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 开启mapper扫描注入的注解
 *
 * @author WindShadow
 * @version 2022-06-04.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MybatisConfigRegistrar.class})
public @interface EnableMapperScan {

    /**
     * @return mapperBasePackage
     * @see #mapperBasePackage()
     */
    @AliasFor("mapperBasePackage")
    String value() default "";

    /**
     * Mapper扫描器要扫描的基本包
     *
     * @return mapperBasePackage
     */
    @AliasFor("value")
    String mapperBasePackage() default "";
}
