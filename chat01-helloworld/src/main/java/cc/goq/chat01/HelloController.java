package cc.goq.chat01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 这个类中创建了一个hello方法，方法上面添加了一个@RequestMapping注解，是SpringMVC中的一个注解，value属性用来指定一个url列表，SpringMVC会将这些指定的url请求转发给当前方法处理。
 * <p>
 * 我们希望访问/hello.do的时候，跳转到/WEB-INF/view/hello.jsp这个页面，这个页面中输出一段内容。
 */
@Controller
public class HelloController {
    /**
     * @RequestMapping： 用来表示url和方法的映射
     * value属性用来指定一个url列表，SpringMVC会将这些指定的url请求转发给当前方法处理
     * @return
     */
    @RequestMapping("/hello.do")
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/hello.jsp");
        //稍后将这个信息显示在hello.jsp中，modelAndView.addObject相当于request.setAttribute(name, value)
        modelAndView.addObject("msg", "这是第一个SpringBoot程序！");
        return modelAndView;
    }
}
