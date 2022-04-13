package cc.goq.chat02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * 首页
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 测试异常情况
     */
    @RequestMapping("/testError")
    public String testError() {
        System.out.println(10/0);
        return "success";
    }

    @RequestMapping("/ok")
    public String okTest() {
        return "success";
    }
}
