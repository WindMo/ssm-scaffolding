package ws.ssm.scaffolding.config.swagger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author WindShadow
 * @version 2021-04-12.
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Swagger2ConfigProperties {

    @Value("#{'${swagger2.enable:}'.isEmpty() ? false : '${swagger2.enable:}'}")
    private Boolean enable;

    /**
     * api要扫描的基本包
     */
    @Value("${swagger2.basePackage:}")
    private String basePackage;

    /**
     * 要生成api文档的路径格式，以ant格式匹配
     */
    @Value("${swagger2.antPattern:}")
    private String antPattern;

    /**
     * api文档标题
     */
    @Value("${swagger2.apiInfo.title:}")
    private String apiInfoTitle;

    /**
     * api文档描述信息
     */
    @Value("${swagger2.apiInfo.description:}")
    private String apiInfoDescription;

    /**
     * api文档版本
     */
    @Value("${swagger2.apiInfo.version:}")
    private String apiInfoVersion;

    /**
     * 字段值检查
     */
    @PostConstruct
    public void fieldVerify() {
        Assert.notNull(enable, "enable is required！");
        if (enable) {
            Assert.hasText(basePackage, "basePackage must be not empty！");
            Assert.hasText(antPattern, "antPattern must be not empty！");
        }
    }
}
