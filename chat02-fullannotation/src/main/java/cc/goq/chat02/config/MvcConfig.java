package cc.goq.chat02.config;

/**
 * 2.3 创建配置springmvc配置类，代替springmvc配置文件
 * 下面这个类相当于springmvc配置文件的功能，springmvc需要的各种组件可以在这个里面配置，这个类的特点
 * 1.需要继承WebMvcConfigurer接口，这个接口中提供了很多方法，预留给开发者用来配置springmvc中的各种组件，springmvc容器的启动过程中，会自动调用这些方法。
 * 2.标注有@Configuration注解，表示这是一个配置类
 * 3.标注有@ComponentScan注解，用来扫描组件，将bean注册到springmvc容器
 * 4.需要标注@EnableWebMvc注解，用来起来springmvc注解配置的功能，有了这个注解，springmvc容器才会自动调用WebMvcConfigurer接口中的方法
 * 5.WebMvcConfigurer接口中提供了一系列方法，用来配置视图解析器、静态资源处理器、拦截器
 * 6.在这个类中我们配置了(@2视图解析器、@3拦截器、@4静态资源处理器、@5文件上传解析器)
 */

import cc.goq.chat02.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 1.开启springmvc注解配置
 * 2.配置视图解析器
 * 3.配置拦截器
 * 4.配置静态资源访问
 * 5.配置文件上传解析器
 * 6.配置全局异常处理器
 */
@Configuration
@ComponentScan("cc.goq.chat02")
//1.使用EnableWebMvc开启springmvc注解方式配置
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor myInterceptor;

    /**
     * 3. 添加拦截器（可以添加多个）
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.myInterceptor).addPathPatterns("/**");
    }

    /**
     * 4.配置静态资源访问处理器
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /**
     * 5.配置文件上传解析器
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        //maxUploadSizePerSize：单文件大小限制（byte）
        //maxUploadSize：整个请求大小限制（byte）
        commonsMultipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024);
        commonsMultipartResolver.setMaxUploadSize(100 * 1024 * 1024);
        return commonsMultipartResolver;
    }

    /**
     * 2.添加视图解析器（可以添加多个）
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        registry.viewResolver(resolver);
    }
}
