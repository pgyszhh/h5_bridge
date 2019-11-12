package com.shuiyinhuo.component.mixdev.dispatcher.observe;

import com.shuiyinhuo.component.mixdev.jinbean.pro.UrlBinder;
import com.shuiyinhuo.component.mixdev.jinbean.threads.MyWorker;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.JNILog;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/24 0024
 * @ Description： 线程托管器
 * =====================================
 */
public class ThreadTrusteeshiper {
    //等待同步完成：网页加载与网络请求都完成
    ExecutorService executor = Executors.newFixedThreadPool(5);
    //等待同子线程
    ExecutorService netWorkTrusteeship = Executors.newFixedThreadPool(5);

    public ThreadTrusteeshiper() {

    }

    /**
     * 等待拿到返回值
     * 阻塞方法
     * https://www.cnblogs.com/dolphin0520/p/3949310.html
     *
     * @return
     */
    public void loopMessage(UrlBinder mUrlBinder) {
        //创建FutureTask
           FutureTask<Object> futureTask = new FutureTask<>(new MyWorker(mUrlBinder));
        if (EmptyAndSizeUtils.isNotEmpty(mUrlBinder)) {
            JNILog.e("Net ","---------------> Builder a NetWorker to Woker !"+ mUrlBinder.getAlias());
            mUrlBinder.showMsg();
           // Future<Object> futureTask =
            executor.submit(futureTask);
            mUrlBinder.initFuture(futureTask);
            mUrlBinder.startSynNetWork(netWorkTrusteeship);
            mUrlBinder.showMsg();
        }else {
            JNILog.e("Net ","--------------------->  Builder a NetWorker to Woker failed !");
        }
    }

    private void tempThread(){
        //创建线程
        //Thread thread = new Thread(futureTask);
        //启动线程
        //thread.start();
        try {
            //阻塞等待线程返回
            //返回值类型为Callable定义的类型
            //backValue = futureTask.get(2000, TimeUnit.MILLISECONDS);
            //设置超时时间，毫秒
            Callable mCallable=null;
            Future<Object> mFuture=new FutureTask(null);
          ExecutorService executor = Executors.newCachedThreadPool();
          FutureTask<String >  futureTasks=new FutureTask(mCallable);
            Future<?> futureTask = executor.submit(futureTasks);
            String mS = (String) futureTask.get(1000, TimeUnit.MILLISECONDS);
            //executor.shutdown();
            //这种是不阻塞，等待5秒钟
            //future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            JNILog.e("方法执行异常：1" + e.getMessage());
            //executor.shutdown();
        } catch (TimeoutException e) {
            e.printStackTrace();
            JNILog.e("----------> 超时....... " + e.getMessage());
            //executor.shutdown();
            }
            //  return null;
            // }
    }
}
