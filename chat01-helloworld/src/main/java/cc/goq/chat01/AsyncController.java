package cc.goq.chat01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

/**
 * 异步处理
 * springmvc中异步处理
 * step1: servlet开启异步处理支持
 * web.xml
 * <async-supported>true</async-supported>
 * step2: Filter中添加异步支持
 * 如果我们的异步请求需要经过Filter的，那么需要在web.xml对这个Filter添加异步支持
 * <filter>
 *    <async-support>true</async-support>
 * </filter>
 * <filter-mapping>
 *     <dispatcher>REQUEST</dispatcher>
 *     <dispatcher>ASYNC</dispatcher>
 * </filter-mapping>
 * step3: 接口返回值为DeferredResult
 * 当需要异步响应请求的时候，返回值需要为DeferredResult。
 * 第一步：创建DeferredResult<返回值类型>(超时时间[ms], 超时回调的代码)
 * 第二步：在子线程中异步处理业务，调用DeferredResult的setResult方法，设置最终返回到客户端的结果，此方法调用以后，客户端将接收到返回值，然后响应过程请求就结束了
 * 第三步：将DeferredResult作为方法返回值
 */
@Controller
public class AsyncController {
    @ResponseBody
    @RequestMapping("/async/m2/{timeout}.do")
    public DeferredResult<String> m2(@PathVariable("timeout") long timeout) {
        long st = System.currentTimeMillis();
        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-开始");
        //1.创建DeferredResult
        DeferredResult<String> result = new DeferredResult<String>(timeout, () -> {
            System.out.println("超时了");
            return "timeout";
        });
        //2.异步处理业务
        new Thread(() -> {
            //开启一个异步线程，在异步线程中进行业务处理操作
            try {
                TimeUnit.SECONDS.sleep(3);
                //3.调用DeferredResult的setResult方法，设置最终返回到客户端的结果，此方法调用以后，客户端将收到返回值。
                result.setResult("ok");
            } catch (InterruptedException e) {
                result.setResult("发生了异常："+e.getMessage());
            }
        }).start();
        long et = System.currentTimeMillis();
        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-结束，耗时:"+(et - st));
        return result;
    }
}

/**
 * 上面的m2方法有个timeout参数，调用者通过这个参数来指定接口的超时时间，未超时的情况下，也就是说timeout大于3s的时候，此时会输出ok，否则将出现超时，此时会将DeferredResult构造器第二个参数的执行结果作为最终的响应结果，即会向客户端输出timeout。
 * 使用建议：案例开启了一个新的子线程来执行业务操作，生产环境中，建议大家采用线程池的方式，效率更高。
 *
 */


/**
 * 测试
 * 当timeout大于3s时，控制台输出如下，可以看到主线程瞬间就结束了。
 * 主线程：Thread[http-nio-8080-exec-6,5,main],1624891886020,开始
 * 主线程：Thread[http-nio-8080-exec-6,5,main],1624891886020,结束,耗时(ms):0
 *
 * 当timeout小于3s
 * 控制台输出如下，输出了超时信息，且通过前两行输出看出主线程瞬间就结束了，不会被请求阻塞。
 * 主线程：Thread[http-nio-8080-exec-1,5,main],1624892109695,开始
 * 主线程：Thread[http-nio-8080-exec-1,5,main],1624892109695,结束,耗时(ms):0
 * 超时了
 *
 */

/**
 * 总结
 * 当接口中有大量的耗时的操作，且这些耗时的操作让线程处于等待状态的时候，此时为了提升系统的性能，可以让接口调整为异步处理请求的方式。
 */
