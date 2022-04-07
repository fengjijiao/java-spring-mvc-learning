package cc.goq.chat01.handle;

import cc.goq.chat01.exception.NameException;
import cc.goq.chat01.exception.PassException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常处理类
 */
//@ControllerAdvice
public class GlobalExceptionHandle {
    /**
     * 此方法用来处理NameException类型的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({NameException.class})
    public ModelAndView doNameException(Exception ex) {
        System.out.println("-----doNameException-----");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/exception-handle/error.jsp");
        modelAndView.addObject("msg", "登录名有误！");
        modelAndView.addObject("e", ex);
        return modelAndView;
    }

    /**
     * 此方法用来处理PassException类型的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({PassException.class})
    public ModelAndView doPassException(Exception ex) {
        System.out.println("-----doPassException-----");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/exception-handle/error.jsp");
        modelAndView.addObject("msg", "密码有误！");
        modelAndView.addObject("e", ex);
        return modelAndView;
    }

    /**
     * 此方法用来处理任意类型的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public ModelAndView doException(Exception ex) {
        System.out.println("-----doException-----");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/view/exception-handle/error.jsp");
        modelAndView.addObject("msg", "系统有误！");
        modelAndView.addObject("e", ex);
        return modelAndView;
    }
}
