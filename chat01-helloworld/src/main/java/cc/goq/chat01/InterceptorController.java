package cc.goq.chat01;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC中使用
 * {@link org.springframework.web.servlet.HandlerInterceptor}
 * 接口来表示拦截器，提供了3个默认方法。
 * preHandle: 在调用自定义的Controller之前会调用这个方法，若返回false,将跳过Controller方法的调用，否则将进入到Controller的方法中
 * postHandle: 调用自定义Controller中的方法之后会调用这个方法，此时还没有渲染视图，也就是还没有将结果输出到客户端
 * afterCompletion: 整个请求处理完毕之后，即结果输出到客户端之后，调用这个方法，此时可以做一些清理的工作，注意这个方法最后一个参数是Exception类型的，说明这个方法不管整个过程中是否有异常，这个方法都会被调用。
 * 以上3个方法中的handler参数表示处理器，通常就是我们自定义的Controller
 *
 *
 * 拦截器的用法（2个步骤）
 * step1: 定义拦截器
 * 自定义一个类，需要实现org.springframework.web.servlet.HandlerInterceptor接口(如HandlerInterceptor1.java)，然后实现具体的方法
 *
 * step2: 将自定义的拦截器添加到springmvc配置文件中
 * 配置如下，需要将自定义的拦截器添加到springmvc配置文件中
 * 1.可以同时配置多个拦截器，每个拦截器通过<mvc:interceptor>标签来定义，多个拦截器之间可以指定顺序，顺序和<mvc:interceptor>定义的顺序一致
 * 2.每个拦截器需要指定实现类、拦截的url、排除的url
 * 见springmvc.xml
 *
 *
 *
 * 多个拦截器时如何执行？
 * 当请求的url匹配到多个拦截器的时候，执行顺序如下所示：
 * preHandle方法是顺序执行的，即和定义的顺序是一致的，而拦截器中的其他2个方法postHandle、afterCompletion是逆序执行的，和preHandle的顺序相反。
 *见/doc/img_4.png
 *
 *
 * 案例：验证拦截器的执行顺序
 * 
 * 拦截器的执行过程主要位于下面的代码中
 * {@link org.springframework.web.servlet.DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)}
 */
@RestController
@RequestMapping("/user")
public class InterceptorController {
    @RequestMapping("/login")
    public String login() {
        return "login view";
    }
    @RequestMapping("/add")
    public String add() {
        return "add view";
    }
    @RequestMapping("/del")
    public String modify() {
        return "modify view";
    }
    @RequestMapping("/list")
    public String list() {
        return "list view";
    }
}
