package cc.goq.chat01;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 案例2：自定义线程处理异步请求
 * 案例1中，我们使用asyncContext.start来处理异步请求，start方法内部会使用web容器中默认的线程池来处理请求，我们也可以自定义线程来处理异步请求，将案例1asyncContext.start代码替换为下面代码，大家也可以自定义一个线程池，将请求丢到线程池中去处理。
 *
 */
//1.设置@WebServlet的asyncSupported属性为true,表示支持异步处理
//@WebServlet(name="AsyncServlet2", urlPatterns="/test/async2", asyncSupported = true)
//在web.xml中有同等配置
//public class AsyncServlet2 extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long st = System.currentTimeMillis();
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//        //2.启动异步处理：调用req.startAsync(request,response)方法，获取异步处理上下文对象AsyncContext
//        //only servlet3.0
//        AsyncContext asyncContext = request.startAsync(request, response);
//        //3.自定义一个线程来处理异步请求
//       Thread thread = new Thread(() -> {
//            long st = System.currentTimeMillis();
//            System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//            try {
//                //这里休眠2s，模拟业务耗时
//                TimeUnit.SECONDS.sleep(2);
//                //这里是子线程，请求在这里被处理了
//                asyncContext.getResponse().getWrite().write("ok");
//                //4.调用complete()方法，表示请求处理完成
//                asyncContext.complete();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end,耗时:"+(System.currentTimeMillis()-st));
//        });
//       thread.setName("自定义线程");
//       thread.start();
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//doGet(request, response);
//    }
//}
