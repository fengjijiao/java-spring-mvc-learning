package cc.goq.chat01;

import cc.goq.chat01.common.dto.ResultDto;
import cc.goq.chat01.exception.BusException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 目前多数系统都是采用前后端分离的方式，后端只负责提供restful接口，返回json格式的数据就可以了，前端负责渲染，
 * 在springmvc提供json格式的接口的时候，需要解决2个问题
 * 问题1：所有接口的返回值采用统一的格式
 * 问题2：系统中异常处理设计的问题，采用一种非常好的方式来解决这个问题
 * <p>
 * 解决问题1：实现统一的返回值
 * 所有接口均返回ResultDto类型的数据，ResultDto类主要有4个字段
 * success: 表示接口是成功还是失败
 * code: 错误码，当有异常的时候，可以返回具体的错误码
 * msg: 提示信息，比如：操作成功、用户名有误、密码有误等等
 * data: 类型是一个泛型，表示任意类型，这个用来存放接口中具体返回的数据，可以是任意类型的对象
 * 还提供了几个静态方法，方便创建ResultDto对象
 * {@link cc.goq.chat01.common.dto.ResultDto}
 * <p>
 * 解决问题2：统一处理异常
 * 异常处理这块，我们的设计主要有2点，通过这2点来解决异常处理的问题
 * 第一点：定义一个基础的业务异常类（BusException），业务代码中手动抛出异常的时候，统一抛出这种类型的异常，异常类型中可以携带更详细的错误信息，比如错误码、提示信息、扩展信息等等
 * 第二点：采用springmvc全局来处理异常，控制器中不要捕获异常，将异常交给springmvc框架来统一处理。
 * {@link cc.goq.chat01.exception.BusException}
 * {@link cc.goq.chat01.handle.GlobalExceptionHandle2}
 * <p>
 * 案例
 * 内部提供了2个接口，接口的返回值都是ResultDto类型的
 * 代码中，没有了try catch，而是将异常类型封装成BusException类型抛出，比如验证码错误，会抛出BusException，顺便携带了错误码和错误提示信息，这些都会通过全局异常的处理，输出到客户端
 */
@RestController
@RequestMapping("/commonresp/user")
public class CommonRespController {
    @RequestMapping("/getUserName")
    public ResultDto<String> getUserName(@RequestParam("code") Integer code) {
        if (!Integer.valueOf(6666).equals(code)) {
            //验证码有误的时候，返回4001错误码
            BusException.throwBusException("4001", "验证码错误！");
        }
        return ResultDto.success("admin");
    }
    @RequestMapping("/getUserId")
    public ResultDto<String> getUserId(@RequestParam("code") Integer code) {
        if (!Integer.valueOf(6666).equals(code)) {
            //验证码有误的时候，返回4001错误码
            BusException.throwBusException("4001", "验证码错误！");
        }
        return ResultDto.success("888");
    }
}
