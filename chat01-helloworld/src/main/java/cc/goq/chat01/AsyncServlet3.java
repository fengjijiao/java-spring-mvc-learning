package cc.goq.chat01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 案例3: 通过asyncContext.dispatch结束异步请求
 * 上面2个案例都是通过asyncContext.complete()来结束异步请求的，结束异步请求还有另外一种方式，子线程中处理完毕业务之后，将结果放在request中，然后调用asyncContext.dispatch()转发请求，此时请求又会进入当前servlet，此时需在代码中判断请求是不是异步转发过来的，如果是的，则从request中获取结果，然后输出，这种方式就是springmvc处理异步的方式。
 */
//1.设置@WebServlet的asyncSupported属性为true,表示支持异步处理
//@WebServlet(name="AsyncServlet3", urlPatterns="/test/async3", asyncSupported = true)
//在web.xml中有同等配置
//public class AsyncServlet3 extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("请求类型：" + request.getDispatcherType());
//        //@1: 判断请求类型，如果是异步类型（DispatcherType.ASYNC），则说明是异步请求转发过来的，将结果输出
//        if (request.getDispatcherType() == DispatcherType.ASYNC) {
//            System.out.println("响应结果："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//            //从request中获取结果,然后输出
//            Object result = request.getAttribute("result");
//            response.getWriter().write(result.toString());
//            System.out.println("响应结果："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end");
//        } else {
//            long st = System.currentTimeMillis();
//            System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//            //2.启动异步处理，调用req.startAsync(request, response)方法获取异步处理上下文对象AsyncContext
//            AsyncContext asyncContext = request.startAsync(request, response);
//            //3.调用start方法异步处理，调用这个方法之后主线程就结束了
//            asyncContext.start(() -> {
//                long stSon = System.currentTimeMillis();
//                System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//                try {
//                    //这里休眠2s，模拟业务耗时
//                    TimeUnit.SECONDS.sleep(2);
//                    //将结果丢到request中
//                    asyncContext.getRequest().setAttribute("result", "ok");
//                    //转发请求，调用这个方法之后，请求又会被转发到当前的servlet，又会进入当前servlet的service方法
//                    //此时请求的类型（request.getDispatcherType()）是DispatcherType.ASYNC，所以通过这个值可以判断请求是异步还是同步
//                    //然后在request中将结果取出，对应代码@1，然后输出u
//                    asyncContext.dispatch();
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end，耗时:"+(System.currentTimeMillis() - stSon));
//            });
//            System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end,耗时："+(System.currentTimeMillis() - st));
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
