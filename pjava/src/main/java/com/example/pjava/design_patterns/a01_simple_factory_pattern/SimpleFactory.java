package com.example.pjava.design_patterns.a01_simple_factory_pattern;

import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.HWPhone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.MIPhone;
import com.example.pjava.design_patterns.a01_simple_factory_pattern.meterial.i.Phone;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.design_patterns.a01_simple_factory
 * @ClassName: SimpleFactory
 * @Description: 简单工厂类
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/19 5:05 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/19 5:05 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SimpleFactory {
    /**
     * 根据传入的参数返回产品实例
     * @param type
     * @return
     */
    static Phone getPhone(int type) {
        Phone phone = null;
        switch (type) {
            case 0:
                phone = new HWPhone();
                break;
            case 1:
                phone = new MIPhone();
                break;
            default:
                break;
        }
        return phone;
    }

    public static void main(String[] args) {
        getPhone(1).createPhone();
        getPhone(0).createPhone();
    }
}
