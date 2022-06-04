package ws.ssm.scaffolding.annoation;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import ws.ssm.scaffolding.config.ConstantOfConfig;

import java.util.Map;

/**
 * Mybatis配置相关注册器
 *
 * @author WindShadow
 * @version 2021-04-24.
 */

class MybatisConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        String importDefaultSpringMybatisConfigClassName = EnableMapperScan.class.getName();
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(importDefaultSpringMybatisConfigClassName);
        Assert.notNull(annotationAttributes, "Not Found EnableMapperScan Annotation");
        String mapperBasePackage = (String) annotationAttributes.get("mapperBasePackage");
        Assert.hasText(mapperBasePackage, "The mapperBasePackage cannot be blank");
        this.registerMapperScannerConfigurer(ConstantOfConfig.SQL_SESSION_FACTORY_BEAN_NAME, mapperBasePackage, registry);
    }

    /**
     * 注册mapper扫描器{@link MapperScannerConfigurer}
     *
     * @param sqlSessionFactoryBeanName sqlSessionFactory bean名称，非空
     * @param mapperBasePackage         扫描mapper的基本包，非空
     * @param registry                  BeanDefinition注册器
     * @see MapperScannerConfigurer
     */
    protected void registerMapperScannerConfigurer(String sqlSessionFactoryBeanName, String mapperBasePackage, BeanDefinitionRegistry registry) {

        GenericBeanDefinition mapperScannerConfigurerBeanDefinition = new GenericBeanDefinition();
        mapperScannerConfigurerBeanDefinition.setBeanClass(MapperScannerConfigurer.class);
        MutablePropertyValues propertyValues = mapperScannerConfigurerBeanDefinition.getPropertyValues();
        propertyValues.add("sqlSessionFactoryBeanName", sqlSessionFactoryBeanName);
        propertyValues.add("basePackage", mapperBasePackage);
        registry.registerBeanDefinition("mapperScannerConfigurer", mapperScannerConfigurerBeanDefinition);
    }
}
