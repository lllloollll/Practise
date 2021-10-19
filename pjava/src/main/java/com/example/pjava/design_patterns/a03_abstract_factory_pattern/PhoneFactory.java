package com.example.pjava.design_patterns.a03_abstract_factory_pattern;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.Phone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.PhoneCharger;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.PhoneUsb;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a03_abstract_factory_pattern
 * @ClassName: PhoneFactory
 * @Description: java类作用描述
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 6:11 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 6:11 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface PhoneFactory {
    Phone createPhone();
    PhoneUsb createUsb();
    PhoneCharger createCharger();
}
