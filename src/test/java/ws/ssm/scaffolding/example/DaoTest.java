package ws.ssm.scaffolding.example;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ws.ssm.scaffolding.ExampleDemoApplicationTest;
import ws.ssm.scaffolding.config.dao.primarykey.IdWork;
import ws.ssm.scaffolding.example.domain.User;
import ws.ssm.scaffolding.example.mapper.UserMapper;


/**
 * @author WindShadow
 * @version 2021-04-14.
 */

public class DaoTest extends ExampleDemoApplicationTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IdWork idWork;

    @Transactional
    @Rollback
    @Test
    public void addTest() {

        for (int i = 0; i < 100; i++) {
            userMapper.insert(new User(idWork.nextId(), "zs" + i, "123456"));
        }
    }
}
