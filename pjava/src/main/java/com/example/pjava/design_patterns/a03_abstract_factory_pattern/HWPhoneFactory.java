package com.example.pjava.design_patterns.a03_abstract_factory_pattern;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.HWPhone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.HWPhoneCharger;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.HWPhoneUsb;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.Phone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.PhoneCharger;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.PhoneUsb;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a03_abstract_factory_pattern
 * @ClassName: HWPhoneFactory
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 6:12 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 6:12 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HWPhoneFactory implements PhoneFactory{
    @Override
    public Phone createPhone() {
        return new HWPhone();
    }

    @Override
    public PhoneUsb createUsb() {
        return new HWPhoneUsb();
    }

    @Override
    public PhoneCharger createCharger() {
        return new HWPhoneCharger();
    }
}
