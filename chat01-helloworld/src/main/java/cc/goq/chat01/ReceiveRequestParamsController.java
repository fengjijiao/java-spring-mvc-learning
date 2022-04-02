package cc.goq.chat01;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 如何接受请求中的参数
 * Controller中的方法如何接收http请求过来的参数呢？
 * 1.接收Servlet中的参数：HttpServletRequest、HttpServletResponse、HttpSession
 * 2.通过方法形参名称接收参数
 * 3.通过@RequestParam接收参数
 * 4.通过1个对象接收参数
 * 5.通过多个对象接收参数
 * 6.组合对象接收参数（对象中嵌套对象集合等等）
 * 7.通过@PathVariable接收url中的参数
 */
@Controller
public class ReceiveRequestParamsController {
    /**
     * 1.接收Servlet中的参数
     * 比如我们想在方法中用到Servlet中的对象： HttpServletRequest、HttpServletResponse、HttpSession，那么可以直接在方法的参数中声明这些对象即可，SpringMVC会自动将这些参数传递过来，用到哪个就声明哪个。
     */
    @RequestMapping("/receiveparam/test1.do")
    public ModelAndView test1(HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String msg = String.format("name:%s,age:%s", name, age);
        System.out.println(msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result.jsp");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }
    /**
     * 解决乱码问题
     * 如果直接创建一个springmvc运行上面的案例，会发现name为中文的时候，会乱码，这里需要在web.xml中添加下面的配置，解决乱码问题。
     * <!-- 添加CharacterEncodingFilter过滤器，这个类中会对request和response设置表名，解决参数乱码问题 -->
     * <filter>
     *     <filter-name>characterEncodingFilter</filter-name>
     *     <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
     *     <init-param>
     *         <param-name>encoding</param-name>
     *         <param-value>UTF-8</param-value>
     *     </init-param>
     *     <init-param>
     *         <!-- forceRequestEncoding为true，将设置request.setCharacterEncoding(encoding); -->
     *         <param-name>forceRequestEncoding</param-name>
     *         <param-value>true</param-value>
     *     </init-param>
     *     <init-param>
     *         <!-- forceRequestEncoding为true，将设置response.setCharacterEncoding(encoding); -->
     *         <param-name>forceResponseEncoding</param-name>
     *         <param-value>true</param-value>
     *     </init-param>
     * </filter>
     * <filter-mapping>
     *     <filter-name>characterEncodingFilter</filter-name>
     *     <url-pattern>/*</url-pattern>
     * </filter-mapping>
     */
    /**
     * 2.通过方法形参名称接收参数
     * 这种情况下，form表单中的参数名称和控制器方法中的参数名称一样，会按照名称一一对应进行赋值。
     */
    @RequestMapping("/receiveparam/test2.do")
    public ModelAndView test2(String name, Integer age) {
        String msg = String.format("name: %s, age: %s", name, age);
        System.out.println(msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result2.jsp");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

    /**
     * 3.通过@RequestParam接收参数
     * 如果方法的参数名称和表单中的参数名称不一致的时候，可以通过@RequetParam注解的value属性来指定表单中参数的名称。
     */
    @RequestMapping("/receiveparam/test3.do")
    public ModelAndView test3(@RequestParam(value = "pname", required = false) String name, @RequestParam(value = "page", required = false) Integer age) {
        String msg = String.format("name: %s, age: %s", name, age);
        System.out.println(msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result3.jsp");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }
    /**
     * @RequestParam用来将请求的参数和方法的参数进行绑定，这个注解还有几个属性，比较常用。
     * name、value: 参数名称
     * required: 参数是否是必须的
     * defaultValue: 默认值
     */

    /**
     * 4.通过一个对象接收参数
     * 通常方法参数不要超过5个，当http请求的参数多的时候，我们可以使用一个对象来接收，对象中的参数名称和http请求中的参数名称一致。
     * 我们可以定义一个UserInfoDto类来接收表单中的参数，这个类中有2个属性名称和上面表单中的属性名称一样。
     */
    @Data
    @ToString
    static class UserInfoDto {
        private String name;
        private Integer age;
    }

    @RequestMapping("/receiveparam/test4.do")
    public ModelAndView test4(UserInfoDto userInfoDto) {
        String msg = String.format("userDto: %s", userInfoDto);
        System.out.println(msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result4.jsp");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

    /**
     * 5.通过多个对象接收参数
     * 上面我们将form表单用一个对象来接收，实际也可使用多个对象来接收
     */
    @Data
    @ToString
    static class WorkInfoDto {
        private Integer workYears;
        private String workAddress;
    }

    @RequestMapping("/receiveparam/test5.do")
    public ModelAndView test5(UserInfoDto userInfoDto, WorkInfoDto workInfoDto) {
        String msg = String.format("userInfoDto: %s, workInfoDto: %s", userInfoDto, workInfoDto);
        System.out.println(msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result5.jsp");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

    /**
     * 6.组合对象接收参数（对象中嵌套对象，集合等等）
     *
     */
    @Data
    @ToString
    static class UserDto {
        //个人基本信息
        private UserInfoDto userInfo;
        //工作信息
        private WorkInfoDto workInfo;
        //工作经验0~n个
        private List<ExperienceInfoDto> experienceInfos;
    }

    @Data
    @ToString
    static class ExperienceInfoDto {
        private String company;
        private String position;
    }

    @RequestMapping("/receiveparam/test6.do")
    public ModelAndView test6(UserDto userDto) {
        String msg = String.format("userDto: %s", userDto);
        System.out.println(msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result6.jsp");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

    /**
     * 7.通过@PathVariable接受url中的参数
     */
    @RequestMapping("/receiveparam/{v1}/{v2}.do")
    public ModelAndView test7(@PathVariable("v1") String p1, @PathVariable("v2") String p2) {
        String msg = String.format("p1: %s, p2: %s", p1, p2);
        System.out.println(msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/result.jsp");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

}
