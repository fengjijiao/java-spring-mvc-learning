package cc.goq.chat01.handle;

import cc.goq.chat01.common.dto.ResultDto;
import cc.goq.chat01.exception.BusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常统一处理类 通用版(json)
 * 注意这里使用了@RestControllerAdvice，它和@ControllerAdvice功能类似，只是这个注解内部定义的时候上面多了一个@ResponseBody注解，表示下面这个类中处理异常的方法返回值最终都会以json格式输出到客户端
 */
@RestControllerAdvice
public class GlobalExceptionHandle2 {
    /**
     * 统一处理业务异常
     * @param ex
     * @param <T>
     * @return
     */
    @ExceptionHandler(BusException.class)
    public <T> ResultDto<T> doBusException(BusException ex) {
        //1.记录错误日志
        //2.返回结果
        return ResultDto.error(ex.getCode(), ex.getMessage(), (T) ex.getData());
    }

    /**
     * 处理其他异常
     * @param ex
     * @param <T>
     * @return
     */
    @ExceptionHandler
    public <T> ResultDto<T> doException(Exception ex) {
        //1.记录错误日志
        //2.返回结果
        return ResultDto.error("系统异常，请联系管理员，错误详情："+ex.getMessage());
    }
}
