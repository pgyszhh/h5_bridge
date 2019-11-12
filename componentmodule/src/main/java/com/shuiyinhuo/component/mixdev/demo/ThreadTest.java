package com.shuiyinhuo.component.mixdev.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/8/17 0017
 * @ Description：测试异步线程等待执行结果
 * =====================================
 */
public class ThreadTest {
    public void test(){
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                //线程执行体
                System.out.println("Test");
                //返回值
                return "Test";
            }
        };

        //创建FutureTask
        FutureTask<String> futureTask = new FutureTask<>(callable);
       //创建线程
        Thread thread = new Thread(futureTask);
        //启动线程
        thread.start();

        try {
            //阻塞等待线程返回
            //返回值类型为Callable定义的类型
            String result = futureTask.get();
            //这种是不阻塞，等待5秒钟
            //future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
