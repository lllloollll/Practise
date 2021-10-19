package com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.Phone;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a01_simple_factory
 * @ClassName: MIPhone
 * @Description: 小米手机
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 4:59 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 4:59 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MIPhone implements Phone {
    String TAG=MIPhone.class.getSimpleName();

    @Override
    public void createPhone() {
        System.out.println("I am xiaomi phone!");
    }
}
