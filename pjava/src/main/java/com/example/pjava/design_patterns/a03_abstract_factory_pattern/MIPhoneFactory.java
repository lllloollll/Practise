package com.example.pjava.design_patterns.a03_abstract_factory_pattern;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.MIPhone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.MIPhoneCharger;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.MIPhoneUsb;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.Phone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.PhoneCharger;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.PhoneUsb;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a03_abstract_factory_pattern
 * @ClassName: MIPhoneFactory
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 6:13 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 6:13 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MIPhoneFactory implements PhoneFactory {
    @Override
    public Phone createPhone() {
        return new MIPhone();
    }

    @Override
    public PhoneUsb createUsb() {
        return new MIPhoneUsb();
    }

    @Override
    public PhoneCharger createCharger() {
        return new MIPhoneCharger();
    }
}
