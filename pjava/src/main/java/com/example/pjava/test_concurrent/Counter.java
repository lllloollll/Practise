package com.example.pjava.test_concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.test_concurrent
 * @ClassName: TestCAS
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2022/5/24 10:27 上午
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/24 10:27 上午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Counter {
    private AtomicInteger atomicT = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> ts = new ArrayList<>(600);
        Long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        cas.count();
                        cas.safeCount();
                    }
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
        //等待所有的线程执行完成
        int size = ts.size();
        for (int i = 0; i < size; i++) {
            Thread t = ts.get(i);
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i + " " + cas.i + " " + cas.atomicT.get() + " " + (System.currentTimeMillis() - start));
        }
    }

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = atomicT.get();
            boolean suc = atomicT.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        i++;
    }

}
