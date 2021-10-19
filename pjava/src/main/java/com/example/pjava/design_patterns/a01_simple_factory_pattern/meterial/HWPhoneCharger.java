package com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.PhoneCharger;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial
 * @ClassName: HWPhoneCharger
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 6:06 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 6:06 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HWPhoneCharger implements PhoneCharger {
    @Override
    public void createPhoneCharger() {
        System.out.println("I am huawei phone charger!");

    }
}
