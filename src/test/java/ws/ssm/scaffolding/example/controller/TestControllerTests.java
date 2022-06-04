package ws.ssm.scaffolding.example.controller;

import org.junit.Test;
import ws.ssm.scaffolding.ExampleDemoApplicationTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author WindShadow
 * @version 2022-06-04.
 * @see TestController
 */

public class TestControllerTests extends ExampleDemoApplicationTest {

    @Test
    public void msgTest() throws Exception {

        mockMvc.perform(get("/api/msg"))
                .andExpect(status().isOk())
                .andExpect(content().string("msg"));
    }
}
