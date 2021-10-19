package com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.Phone;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a01_simple_factory
 * @ClassName: HWPhone
 * @Description: 华为手机
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 5:03 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 5:03 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HWPhone implements Phone {
    String TAG=HWPhone.class.getSimpleName();

    @Override
    public void createPhone() {
        System.out.println("I am huawei phone!");
    }
}
