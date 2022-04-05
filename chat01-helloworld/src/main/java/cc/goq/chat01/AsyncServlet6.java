package cc.goq.chat01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 案例6：对案例5 进行改造
 *
 * @3：通过一个原子变量来控制请求是否处理完毕了，代码中有3处可能会修改这个变量，通过cas操作来控制谁会修改成功，修改成功者，将结果设置到request.setAttribute中，然后调用asyncContext.dispatch()；转发请求，这种处理方式很好的解决了案例5中的异常问题，springmvc中异步处理过程和这个过程类似，所以这段代码大家一定要好好看看，若能够理解,springmvc中异步处理的代码可以秒懂。
 */
//1.设置@WebServlet的asyncSupported属性为true,表示支持异步处理
//@WebServlet(name="AsyncServlet6", urlPatterns="/test/async6", asyncSupported = true)
//在web.xml中有同等配置
//public class AsyncServlet6 extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if (request.getDispatcherType() == DispatcherType.ASYNC) {
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().write(request.getAttribute("result").toString());
//        } else {
//            long st = System.currentTimeMillis();
//            System.out.println("主线程：" + Thread.currentThread() + "-" + System.currentTimeMillis() + "-start");
//            //2.启动异步处理
//            AsyncContext asyncContext = request.startAsync(request, response);
//            //@1设置异步处理超时时间
//            Long timeout = Long.valueOf(request.getParameter("timeout"));
//            asyncContext.setTimeout(timeout);
//            //@3: 用来存储异步是否结束，在这3个地方（子线程中处理完毕时、onComplete、onTimeout）将其更新
//            AtomicBoolean finish = new AtomicBoolean(false);
//            //添加监听器
//            asyncContext.addListener(new AsyncListener() {
//                @Override
//                public void onComplete(AsyncEvent event) throws IOException {
//                    //异步处理完成会被回调
//                    System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onComplete()");
//                    if (finish.compareAndSet(false, true)) {
//                        event.getAsyncContext().getRequest().setAttribute("result", "onComplete");
//                        //转发请求
//                        asyncContext.dispatch();
//                    }
//                }
//
//                @Override
//                public void onTimeout(AsyncEvent event) throws IOException {
//                    //超时会被回调
//                    System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onTimeout()");
//                    if (finish.compareAndSet(false, true)) {
//                        event.getAsyncContext().getRequest().setAttribute("result", "onTimeout");
//                        //转发请求
//                        asyncContext.dispatch();
//                    }
//                }
//
//                @Override
//                public void onError(AsyncEvent event) throws IOException {
//                    //发生错误会被回调
//                    System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onError()");
//                    event.getAsyncContext().getResponse().getWriter().write("onError");
//                }
//
//                @Override
//                public void onStartAsync(AsyncEvent event) throws IOException {
//                    //开启异步请求调用的方法
//                    System.out.println(Thread.currentThread() + "-" + System.currentTimeMillis() + "-onStartAsync()");
//                    event.getAsyncContext().getResponse().getWriter().write("onStartAsync");
//                }
//            });
//            //3、调用start方法异步处理，调用这个方法之后主线程就结束了
//            asyncContext.start(() -> {
//                long stSon = System.currentTimeMillis();
//                System.out.println("子线程：" + Thread.currentThread() + "-" + System.currentTimeMillis() + "-start");
//                try {
//                    //@2:这里休眠2秒，模拟业务耗时
//                    TimeUnit.SECONDS.sleep(2);
//                    if (finish.compareAndSet(false, true)) {//expectedValue: false, newValue: true
//                        asyncContext.getRequest().setAttribute("result", "ok");
//                        //转发请求
//                        asyncContext.dispatch();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println("子线程：" + Thread.currentThread() + "-" + System.currentTimeMillis() + "-end,耗时(ms):" + (System.currentTimeMillis() - stSon));
//            });
//            System.out.println("主线程：" + Thread.currentThread() + "-" + System.currentTimeMillis() + "-end,耗时：" + (System.currentTimeMillis() - st));
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
