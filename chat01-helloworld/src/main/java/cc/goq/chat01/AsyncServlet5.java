package cc.goq.chat01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 案例5: 设置监听器
 * 还可以为异步处理添加监听器，当异步处理完成、发生异常错误、出现超时的时候，会回调监听器中对应的方法
 */
//1.设置@WebServlet的asyncSupported属性为true,表示支持异步处理
//@WebServlet(name="AsyncServlet5", urlPatterns="/test/async5", asyncSupported = true)
//在web.xml中有同等配置
//public class AsyncServlet5 extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        long st = System.currentTimeMillis();
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-start");
//        //2.启动异步处理，调用req.startAsync(request, response)方法，获取异步处理上下文对象AsyncContext
//        AsyncContext asyncContext = request.startAsync(request, response);
//        //@1:设置异步处理超时时间
//        Long timeout = Long.valueOf(request.getParameter("timeout"));
//        asyncContext.setTimeout(timeout);
//        //添加监听器
//        asyncContext.addListener(new AsyncListener() {
//            @Override
//            public void onComplete(AsyncEvent event) throws IOException {
//                //异步处理完成会被回调
//                System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onComplete()");
//                event.getAsyncContext().getResponse().getWriter().write("onComplete");
//            }
//
//            @Override
//            public void onTimeout(AsyncEvent event) throws IOException {
//                //超时会被回调
//                System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onTimeout()");
//                event.getAsyncContext().getResponse().getWriter().write("onTimeout");
//            }
//
//            @Override
//            public void onError(AsyncEvent event) throws IOException {
//                //发生错误会被回调
//                System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onError()");
//                event.getAsyncContext().getResponse().getWriter().write("onError");
//            }
//
//            @Override
//            public void onStartAsync(AsyncEvent event) throws IOException {
//                //开启异步请求调用的方法
//                System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onStartAsync()");
//                event.getAsyncContext().getResponse().getWriter().write("onStartAsync");
//            }
//        });
//        //3.调用start方法异步处理，调用这个方法之后主线程就结束了
//        asyncContext.start(() -> {
//            long stSon = System.currentTimeMillis();
//            System.out.println("子线程：" + Thread.currentThread() + "-" + System.currentTimeMillis() + "-start");
//            try {
//                //@2:这里休眠2秒，模拟业务耗时
//                TimeUnit.SECONDS.sleep(2);
//                //这里是子线程，请求在这里被处理了
//                asyncContext.getResponse().getWriter().write(System.currentTimeMillis() + ",ok");
//                //4、调用complete()方法，表示异步请求处理完成
//                asyncContext.complete();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("子线程：" + Thread.currentThread() + "-" + System.currentTimeMillis() + "-end,耗时(ms):" + (System.currentTimeMillis() - stSon));
//        });
//        System.out.println("主线程："+Thread.currentThread()+"-"+System.currentTimeMillis()+"-end");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
/**
 * 当未超时时，
 * 11111111,ok
 * onComplete
 *
 * --------------
 *
 * 当为超时时，调用了如下方法
 * onTimeout
 * onComplete
 * 但是代码中会报异常
 * java.lang.IllegalStateException: AsyncContext关联的请求已经完成处理。
 * 这是因为发生超时时，onTimeout方法执行完毕之后，异步处理就结束了，此时，子线程还在运行，子线程执行到下面这样的代码，向客户端输出信息，所以报错了。
 * asyncContext.getResponse().getWriter().write(System.currentTimeMillis() + ",ok");
 */
