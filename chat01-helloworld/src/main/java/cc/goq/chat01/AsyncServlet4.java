package cc.goq.chat01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 案例4：设置异步处理超时时间
 * 异步请求总不能让他一直执行吧，所以咱们可以设置超时时间。
 * asyncContext.setTimeout(超时时间，毫秒，默认是30s);
 */
//1.设置@WebServlet的asyncSupported属性为true,表示支持异步处理
//@WebServlet(name="AsyncServlet4", urlPatterns="/test/async4", asyncSupported = true)
//在web.xml中有同等配置
//public class AsyncServlet4 extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long st = System.currentTimeMillis();
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//        //2.启动异步处理，调用req.startAsync(request, response)方法，获取异步处理上下文对象AsyncContext
//        AsyncContext asyncContext = request.startAsync(request, response);
//        //设置异步处理超时时间为1s
//        asyncContext.setTimeout(1000);
//        //3.调用start方法异步处理，调用这个方法之后主线程就结束了
//        asyncContext.start(()-> {
//            long stSon = System.currentTimeMillis();
//            System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//            try {
//                //这里休眠2s，模拟业务耗时
//                TimeUnit.SECONDS.sleep(2);
//                //这里是子线程，请求在这里被处理
//                asyncContext.getResponse().getWrite().write(System.currentTimeMillis()+",ok");
//                //4.调用complete方法，表示异步请求处理完成
//                asyncContext.complete();
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("子线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end，耗时："+(System.currentTimeMillis() - stSon));
//        });
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end,耗时："+(System.currentTimeMillis() - st));
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
