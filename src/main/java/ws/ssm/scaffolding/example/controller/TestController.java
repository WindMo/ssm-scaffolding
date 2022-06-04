package ws.ssm.scaffolding.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WindShadow
 * @date 2021-2-25
 */

@Controller
@RequestMapping("/api")
public class TestController {

    @GetMapping("/aaa")
    @ResponseBody
    public String msg00() {

        return "aaa";
    }

    @GetMapping("/bbb")
    @ResponseBody
    public String msg000() {

        return "bbb";
    }

    @GetMapping("/msg")
    @ResponseBody
    public String msg() {

        return "msg";
    }
}
