package ws.ssm.scaffolding.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis逆向工程代码生成器工具类
 *
 * @author WindShadow
 * @version 2021-04-05.
 */

public final class MybatisGeneratorUtils {

    /*
        一定要1.3.7 版本其他基本全是坑！！！
        1.3.7+ ：dtd约束文件无法访问
        1.3.7- ：有的标签不支持
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.7</version>
            <scope>test</scope>
        </dependency>
     */

    /**
     * @param configFilePath
     * @param overwrite      是否覆盖
     */
    public static void generator(String configFilePath, boolean overwrite) {

        generator(new File(configFilePath), overwrite);
    }

    /**
     * @param configFile
     * @param overwrite  是否覆盖
     */
    public static void generator(File configFile, boolean overwrite) {

        try {
            List<String> warnings = new ArrayList<String>();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
