package cc.goq.chat01;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class JsonParserController {
    @Data
    @ToString
    static class UserInfoDto {
        private String name;
        private Integer age;
        private String address;
    }
    @Data
    @Builder
    @ToString
    static class ResultData {
        private String msg;
    }
    @RequestMapping("/jsonparser/test1.do")
    public ResultData test1(@RequestBody UserInfoDto userInfoDto) {
        String msg = String.format("userInfoDto: %s", userInfoDto);
        return ResultData.builder().msg(msg).build();
    }
    /**
     * 当完成在pom中添加jackson和在springmvc.xml中添加mvc驱动配置后，springmvc就被赋予了一个强大的功能，有能力将body中的json格式的数据转换为java对象。
     * 原理：SpringMVC容器中被添加了一个MappingJackson2HttpMessageConverter对象，这个里可以将body中json格式的数据转换为java对象，内部用到的是jackson。
     * MappingJackson2HttpMessageConverter这类就是在添加的maven包中
     *
     * 1.添加jackson包
     * 2.添加mvc驱动配置(springmvc.xml)
     * 3.当我们希望controller中处理器的方法参数的数据来源于http请求的body时，需要在参数的前面加上@RequestBody注解
     *
     *
     */


    /**
     * @RequestBody注解
     * 作用：用来接收http请求body的数据。
     * http请求中以post方式提交的请求，是有个body部分的，在springmvc中，我们希望控制器的方法中某个参数的值为http请求中的body值，那么只需要在这个参数的前面加上@RequestBody注解，springmvc会将http请求中body的数据读取出来，然后传递这个参数。
     *
     * 案例1：使用String类型接收body
     * public void m1(@RequestBody String body)
     * springmvc会将请求中body部分的数据读取出来，转换为String类型传递给这个参数。
     * 案例2：使用字节数组接收body的数据
     * public void m1(@RequestBody byte[] bytes)
     * springmvc会将请求中body部分的数据读取出来，然后转换为字节数组然后传递给bytes参数。
     */
    @PostMapping("/jsonparser/test2.do")
    public void m1(@RequestBody String body) {
        System.out.println(body);
    }

    @PostMapping("/jsonparser/test3.do")
    public void m2(@RequestBody byte[] bytes) {
        System.out.println(new String(bytes));
    }

    /**
     * 8.（重点）HandlerMethodArgumentResolver
     * 到目前我们知道可以使用@RequestParam接收表单的值，@RequestBody可以接收请求中body的数据，@PathVariable可以接收url中动态参数。
     * 那么，控制器的方法具体可以接收哪些类型的参数呢？
     * 这里包括一个关键的接口：HandlerMethodArgumentResolver（控制器方法参数解析器），这个接口特别重要，负责将http请求中的数据转换为controller中的方法能够接收的数据，就是根据控制器中方法参数信息，将http请求中的信息，转换为控制器方法需要的参数的值。
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver
     * //判断当前解析器是否能处理这个parameter这个参数，也就是说能否将请求中的数据转换为parameter指定的参数的值
     * boolean supportsParameter(MethodParameter parameter);
     *
     * //解析参数：从http请求中解析出控制器需要的参数值
     * Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactroy binderFactory) throw Exception;
     *
     * 这个接口有很多实现类
     * 实现类                                      对应控制器参数                            说明
     * PathVariableMapMethodArgumentResolver    @PathVariable标注参数                     从url中提取参数的值
     * RequestHeaderMethodArgumentResolver      @RequestHeader标注参数                  从http头中提取参数值
     * RequestParamMethodArgumentResolver       @RequestParam标注参数                   http请求参数中获取值
     * RequestPesponseBodyMethodProcessor       @RequestBody标注参数                    提取body数据，转换为参数类型
     * ServletResponseMethodArgumentResolver    ServletResponse、OutputStream、Writer这3种类型的参数         这几种类型用来控制http请求的响应输出流
     * HttpEntityMethodProcessorHttpEntity      HttpEntity类型的参数                     HttpEntity中包含了http请求头和body的所有信息
     * ExpressionValueMethodArgumentResolver    @Value标注的参数                         spEL表达式，从spring容器中获取值
     * MapMethodProrcessor                      参数为Map或者子类型
     * ModelMethodProcessor                     参数为org.springframework.ui.Model或子类型
     * {@link org.springframework.ui.Model}
     * ModelAttributeMethodProcessor            @ModelAttribute标注的参数
     *
     */

    /**
     * 9. tomcat9乱码问题
     * 控制台可能出现乱码，需要配置一下编码
     * Edit Configurations -> Tomcat -> VM options: -Dfile.encoding=utf-8
     */
}
