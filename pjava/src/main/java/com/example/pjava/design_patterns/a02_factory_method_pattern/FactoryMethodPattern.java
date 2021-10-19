package com.example.pjava.design_patterns.a02_factory_method_pattern;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.HWPhone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.MIPhone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.Phone;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a01_simple_factory
 * @ClassName: FactoryMethodPattern
 * @Description: 工厂方法模式
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 5:36 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 5:36 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FactoryMethodPattern {
    static Phone getMIPhone(){
        return new MIPhone();
    }

    static Phone getHWPhone(){
        return new HWPhone();
    }

    public static void main(String[] args) {
        getHWPhone().createPhone();
        getMIPhone().createPhone();
    }
}
