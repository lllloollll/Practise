package com.example.pjava.design_patterns.a04_singleton_pattern;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a04_singleton_pattern
 * @ClassName: LazySingleton
 * @Description: 懒汉式 单例
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 6:18 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 6:18 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LazySingleton {
    private static LazySingleton instance = null;

    /**
     * 私有化构造函数
     */
    private LazySingleton() {
    }

    /**
     * 懒汉式 线程不安全
     * @return
     */
    static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    /**
     * 懒汉式 线程安全 但是不高效 同一时间只能有一个线程调用getInstance2()方法
     * @return
     */
    static synchronized LazySingleton getInstance2(){
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    /**
     * 双重检验锁 线程安全 但仍有问题
     * synchronized 不保证原子性 会发生指令重排
     * instance = new LazySingleton(); 分三步执行 1、为instance分配内存空间 2、创建Singleton实例 3、将instance指向Singleton实例
     * 指令执行顺序可能为 1-2-3 或 1-3-2
     * 当执行顺序为 1-3-2，其他线程在 3-2 之间拿到instance,判断不为空，返回的确是null（没有写完就被读）
     * 解决：需要给 instance 添加关键字 volatile,保证instance的原子性。使得instance写完才能被读。
     * private volatile static LazySingleton instance = null;
     * @return
     */
    static LazySingleton getInstance3() {
        if (instance == null)
            synchronized (LazySingleton.class) {
                if (instance == null)
                    instance = new LazySingleton();
            }
        return instance;
    }
}
