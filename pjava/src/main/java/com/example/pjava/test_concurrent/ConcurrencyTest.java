package com.example.pjava.test_concurrent;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.test_concurrent
 * @ClassName: ConcurrencyTest
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2022/5/23 11:32 上午
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/23 11:32 上午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConcurrencyTest {
    private static final long count=100001;
    public static void main(String[] args) throws InterruptedException{
        concurrency();
        serial();
    }

    private static void concurrency() throws InterruptedException{
        long start=System.currentTimeMillis();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int a=0;
                for (long i=0;i<count;i++){
                    a+=5;
                }
            }
        });
        thread.start();
        int b=0;
        for (long i=0;i<count;i++){
            b--;
        }
        long time=System.currentTimeMillis()-start;
        thread.join();
        System.out.println("concurrency:"+time+"ms,b="+b);
    }

    private static void serial(){
        long start=System.currentTimeMillis();
        int a=0;
        for (long i=0;i<count;i++){
            a+=5;
        }
        int b=0;
        for (long i=0;i<count;i++){
            b--;
        }
        long time=System.currentTimeMillis()-start;
        System.out.println("serial:"+time+"ms,b="+b+",a="+a);
    }
}
