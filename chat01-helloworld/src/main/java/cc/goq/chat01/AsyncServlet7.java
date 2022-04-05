package cc.goq.chat01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 案例7：模拟一个业务场景
 * ServiceA接受到一个请求之后，将请求发送到mq,然后主线程就结束了，另外一个服务ServiceB从mq中取出这条消息，然后对消息进行处理，将处理结果又丢到mq中，ServiceA中监听mq中的结果，然后再将结果输出。
 */
//1.设置@WebServlet的asyncSupported属性为true,表示支持异步处理
//@WebServlet(name="AsyncServlet7", urlPatterns="/test/async7", asyncSupported = true)
//在web.xml中有同等配置
//public class AsyncServlet7 extends HttpServlet {
//    Map<String, AsyncContext> orderIdAsyncContextMap = new ConcurrentHashMap();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String orderId = request.getParameter("orderId");
//        String result = request.getParameter("result");
//        AsyncContext async;
//        if (orderId != null && result != null && (async = orderIdAsyncContextMap.get(orderId)) != null) {
//            async.getResponse().getWriter().write(String.format("%s:%s:result:%s", Thread.currentThread(), System.currentTimeMillis(), result));
//            async.complete();
//        } else {
//            AsyncContext asyncContext = request.startAsync(request, response);
//            orderIdAsyncContextMap.put("1", asyncContext);
//            asyncContext.getResponse().setContentType("text/html;charset=utf-8");
//            asyncContext.getResponse().getWriter().write(String.format("%s:%s:%s", Thread.currentThread(), System.currentTimeMillis(), "start"));
//        }
//    }
//
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
/**
 * step1、启动项目
 * step2、浏览器中访问：http://localhost:8080/asyncServlet7，会发现浏览器中请求一直处于等待中
 * step3、等待5秒，用来模拟ServiceB处理耗时
 * step4、浏览器中访问：http://localhost:8080/asyncServlet7?orderId=1&result=success；用来模拟将结果通知给请求者，这步执行完毕之后，step2会立即收到响应
 */

/**
 * 扩展
 * 通常我们的项目是集群部署的，假如这个业务场景中ServiceA是集群部署的，有3台机器（ServiceA、ServiceB、ServiceC），如果ServiceB将处理完成的结果消息丢到mq后，如果消息类型是点对点的，那么消息只能被一台机器消费，需要确保ServiceA中接受用户请求的及其和最终接受mq中消息结果的机器是一台机器才可以，如果接受请求的机器是ServiceA，而消费结果消息的机器是ServiceB，那么ServiceA就一直拿不到结果，直到超时，如何解决？
 *
 * 此时需要广播消息，ServiceB将处理结果广播出去，其他所有机器都会监听到这条消息
 * 可以使用redis的发布订阅功能解决这个问题。
 */


/**
 * 总结
 * 开启异步处理：request.startAsync(request, response);获取异步处理上下文对象AsyncContext
 * 设置异步处理超时时间：asyncContext.setTimeout(ms);
 * 设置异步处理监听器: asyncContext.addListener();可以添加多个监听器
 * 完成异步处理的2种方式：asyncContext.dispatch()或者asyncContext.complete()
 */