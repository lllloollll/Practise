package com.example.pjava.design_patterns.a04_singleton_pattern;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a04_singleton_pattern
 * @ClassName: Singleton
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 6:18 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 6:18 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Singleton {
    private static Singleton instance = null;

    private Singleton() {
    }

    /**
     * 懒汉式 线程不安全
     * @return
     */
    static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    /**
     * 懒汉式 线程安全 但是不高效 同一时间只能有一个线程调用getInstance2()方法
     * @return
     */
    static synchronized Singleton getInstance2(){
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    /**
     * 双重检验锁 线程安全 但仍有问题
     * @return
     */
    static Singleton getInstance3() {
        if (instance == null)
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
            }
        return instance;
    }
}
