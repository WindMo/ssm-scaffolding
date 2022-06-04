package ws.ssm.scaffolding.config.dao.mybatis;


/**
 * PageHelper数据库类型
 * gitee文档：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
 *
 * @author WindShadow
 * @version 2021-04-04.
 */
public enum PageHelperDialect {

    DB2("db2"),
    DIALECT("derby"),
    H2("h2"),
    HSQLDB("hsqldb"),
    INFORMIX("informix"),
    MARIADB("mariadb"),
    MYSQL("mysql"),
    ORACLE("oracle"),
    POSTGRESQL("postgresql"),
    SQLITE("sqlite"),
    SQLSERVER("sqlserver"),
    SQLSERVER_2012("sqlserver2012");

    private final String dialect;

    PageHelperDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getDialect() {
        return dialect;
    }

    @Override
    public String toString() {
        return dialect;
    }
}