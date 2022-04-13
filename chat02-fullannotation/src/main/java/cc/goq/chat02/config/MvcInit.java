package cc.goq.chat02.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * 2.2在Servlet3.0环境中，容器会在类路径中查找javax.servlet.ServletContainerInitializer接口的类，如果找到的话就用它来配置Servlet容器。Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个类反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成。Spring3.2引入了一个便利的WebApplicationInitializer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDispatcherServletInitializer并将其部署到Servlet3.0容器的时候，容器胡自动发现它，并用它来配置Servlet上下文。
 * 项目启动时，servlet容器会自动加载这个类，这个类相当于web.xml的功能
 *
 * 1.创建MVC初始化类，需要继承AbstractAnnotationConfigDispatcherServletInitializer类
 */
public class MvcInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * SpringMVC容器的父容器spring配置类
     * 实际工作中我们的项目比较复杂，可以将controller层放在springmvc容器中
     * 其他层，如service层、dao层放在父容器了，bean管理起来更加清晰一些
     * 也可以没有父容器，将所有bean都放在springmvc容器中
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * 2.设置springmvc容器的spring配置类
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    /**
     * 配置DispatcherServlet的url-pattern
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 4.注册拦截器
     */
    @Override
    protected Filter[] getServletFilters() {
        //添加拦截器，解决乱码问题
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceRequestEncoding(true);
        characterEncodingFilter.setForceResponseEncoding(true);
        return new Filter[]{characterEncodingFilter};
    }
}
