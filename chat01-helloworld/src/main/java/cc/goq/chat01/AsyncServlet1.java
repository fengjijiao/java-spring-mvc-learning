package cc.goq.chat01;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * servlet3.0中异步处理使用步骤
 * step1: 开启异步支持
 * 设置@WebServlet的asyncSupported的属性值为true，表示支持异步处理。
 * step2: 启动异步请求
 * 启动异步请求：调用req.startAsync(request, response)方法，获取异步处理上下文对象AsyncContext
 * AsyncContext asyncContext = request.startAsync(request, response);
 * step3: 异步处理业务&完成异步处理
 * 其他线程中执行业务操作，输出结果，并调用asyncContext.complete()完成异步处理，比如下面2种方式：
 * 方式1：启动一个新的线程来处理请求
 * `
 * new Thread(()-> {
 *     System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
 *     try {
 *         //这里休眠2s模拟业务耗时
 *         TimeUnit.SECONDS.sleep(2);
 *         //这里是子线程，请求在这里被处理了
 *         asyncContext.getResponse().getWrite().write("ok");
 *         //调用complete()方法，表示请求处理完成
 *         asyncContext.complete();
 *     }catch(Exception e) {
 *         e.printStackTrace();
 *     }
 *     System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMills+"-end");
 * });
 * `
 * 方式2：如下代码，调用asyncContext.start方法来处理请求，传递的是一个Runnable对象，asyncContext.start会将传递的Runnable放在新的线程中去执行。
 * `
 * asyncContext.start(() -> {
 *     System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMills+"-start");
 *     try {
 *         //这里休眠2s，模拟业务耗时
 *         TimeUnit.SECONDS.sleep(2);
 *         //这里是子线程，请求在这里被处理了
 *         asyncContext.getResponse().getWrite().write("ok");
 *         //调用complete()方法，表示请求处理完成
 *         asyncContext.complete();
 *     } catch(Exception e) {
 *         e.printStackTrace();
 *     }
 *     System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMills+"-end");
 * });
 * `
 *
 */

/**
 * 案例1：使用asyncContext.start处理异步请求
 * 下面的案例代码会输出4条日志，注意日志中包含的信息:时间、线程信息、耗时，通过这些信息可以分析主线程什么时候结束的。
 */
//1.设置@WebServlet的asyncSupported属性为true,表示支持异步处理
//@WebServlet(name="AsyncServlet1", urlPatterns="/test/async1", asyncSupported = true)
//在web.xml中有同等配置
//public class AsyncServlet1 extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long st = System.currentTimeMillis();
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//        //2.启动异步处理：调用req.startAsync(request,response)方法，获取异步处理上下文对象AsyncContext
//        //only servlet3.0
//        AsyncContext asyncContext = request.startAsync(request, response);
//        //3.调用start方法异步处理，调用这个方法之后主线程就结束了
//        asyncContext.start(() -> {
//            long stSon = System.currentTimeMillis();
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
//            System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end,耗时："+(System.currentTimeMills()-st));
//        });
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
