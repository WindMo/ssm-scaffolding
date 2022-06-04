package ws.ssm.scaffolding.config.dao.datasource;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataSourceConfigProperties {

    @Value("${application.dataSource.driverClassName:}")
    private String driverClassName;
    @Value("${application.dataSource.url:}")
    private String url;
    @Value("${application.dataSource.username:}")
    private String username;
    @Value("${application.dataSource.password:}")
    private String password;

    /**
     * 字段值检查
     */
    @PostConstruct
    public void fieldVerify() {

        Assert.hasText(driverClassName, "driverClassName must be not empty！");
        Assert.hasText(url, "url must be not empty！");
    }

}
