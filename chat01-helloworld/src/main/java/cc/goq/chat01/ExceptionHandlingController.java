package cc.goq.chat01;

import cc.goq.chat01.exception.NameException;
import cc.goq.chat01.exception.PassException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常处理
 *
 * 使用try catch处理异常的不便：
 * 对于会抛出异常的方法，每个方法都进行一次try catch进行对业务异常处理。当发生异常的时候，我们通常为了方便排查错误，会在catch中将异常信息记录到日志文件中。
 * 如果发生了严重的异常，我们需要第一时间让开发人员介入，我们可以在catch中发生短信给开发者，让其第一时间介入解决。
 * 此时catch中又需要添加代码，要改的代码是大量的（许多的try catch），得重新测试一遍，此时对于开发和测试都是一种灾难。
 *
 * 如何更好的解决这个问题？
 * 采用aop的方式，将异常处理和业务代码进行分离，让框架拦截所有方法的执行，目标方法中不需要在捕获异常了，直接将异常跑出去，由统一的地方进行处理。
 * 此时异常处理和业务代码分离开了，没有耦合在一起了，此时如果需要调整异常的处理逻辑，会非常方便，只需要修改统一处理异常的代码，就ok了。
 * 如果对spring的aop比较熟悉，实现起来还是很容易的，只需要添加一个环绕拦截器就可以了。
 * springmvc中提供了类似的方法来统一处理系统所有的异常，Controller中的所有方法都无需捕获异常，只需要做一些配置，springmvc框架就会自动捕获异常。
 *
 *
 * 案例
 * 需求：写个登录方法，方法中验证用户名和密码，验证失败的时候分别抛出对应的异常，成功了跳转到success.jsp页面
 *
 * 具体实现步骤
 * step1: 创建全局异常处理类（非常关键）
 * 这个步骤是重点，包含3个小步骤。
 * 第一步：创建一个普通类，作为全局异常处理类
 * 第二步：在类上添加@ControllerAdvice注解，从注解的名称包含了Advice可以看出，aop中我们接触过Advice(通知)，用来对bean的功能进行增强，而这个注解是对Controller的功能进行增强，用来集中处理Controller的所有异常
 * 第三步：添加处理异常的方法，方法上需要加上@ExceptionHandler注解，这个注解有个value属性，用来指定匹配的异常类型，当springmvc捕获到控制器异常后，会和这个异常类型进行匹配，匹配成功了，将调用@ExceptionHandler标注的 方法；如果未指定value的值，表示捕获所有类型的异常。
 *
 */
@Controller
@RequestMapping("/exception/handling/user")
public class ExceptionHandlingController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String name, @RequestParam("password") Integer pass) throws NameException, PassException {
        if (!"admin".equals(name)) {
            throw new NameException("用户名有误！");
        }
        if (!Integer.valueOf(666).equals(pass)) {
            throw new PassException("密码有误！");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", name);
        modelAndView.setViewName("/WEB-INF/view/exception-handle/success.jsp");
        return modelAndView;
    }
    @GetMapping("/login")
    public String login() {
        return "/WEB-INF/view/exception-handle/index.jsp";
    }

    /**
     * 总结
     * 本文详细介绍了springmvc集中统一异常处理的具体用法，主要用到了2个注解：@ControllerAdvice和@ExceptionHandler
     * 目前多数系统中都是采用前后端分离，后端所有的接口都返回json格式的数据，
     */
}
