package ws.ssm.scaffolding.config.dao.mybatis;

import com.github.pagehelper.PageInterceptor;
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
public class MybatisConfigProperties {

    /**
     * mapper.xml的位置
     */
    @Value("${mybatis.mapperLocation:}")
    private String mapperLocation;

    /**
     * PageHelper插件使用的数据库方言支持类型见{@link PageHelperDialect}
     *
     * @see PageInterceptor
     * @see PageHelperDialect
     */
    @Value("#{'${mybatis.pageHelper.dialect:}'.isEmpty() ? null : T(ws.ssm.scaffolding.config.dao.mybatis.PageHelperDialect).valueOf('${mybatis.pageHelper.dialect:}')}")
    private PageHelperDialect pageHelperDialect;

    /**
     * 字段值检查
     */
    @PostConstruct
    public void fieldVerify() {

        Assert.hasText(mapperLocation, "mapperLocation must be not empty！");
        Assert.notNull(pageHelperDialect, "pageHelperDialect must be not null！");
    }
}
