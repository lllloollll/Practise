package com.example.pjava.design_patterns.a04_singleton_pattern;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a04_singleton_pattern
 * @ClassName: Singleton
 * @Description: 单例
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/20 10:47 上午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/20 10:47 上午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Singleton {

    /**
     * 私有化构造函数
     */
    private Singleton(){}

    /**
     * 饿汉式
     * 在创建的时候就初始化
     * 缺点：当实例的创建依赖参数时 将无法实现
     */
    private static final Singleton instance=new Singleton();
    public static Singleton getInstance(){
        return instance;
    }


    /**
     * 静态内部类  （推荐）
     */
    private static class SingletonHolder{
        private static final Singleton INSTANCE=new Singleton();
    }
    private static Singleton getInstance2(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 枚举法
     */
    private enum SingltonEnum{
        INSTANCE;
        private Singleton singleton;
        private SingltonEnum(){
            singleton=new Singleton();
        }
    }
    private static Singleton getInstance3(){
        return SingltonEnum.INSTANCE.singleton;
    }

}
