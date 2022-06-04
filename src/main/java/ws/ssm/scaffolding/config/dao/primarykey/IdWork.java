package ws.ssm.scaffolding.config.dao.primarykey;

/**
 * 主键id生成器
 *
 * @author WindShadow
 * @version 2021-04-14.
 */

/**
 * id生成器
 */
public interface IdWork {

    /**
     * 下一个id
     * @return
     */
    long nextId();
}
