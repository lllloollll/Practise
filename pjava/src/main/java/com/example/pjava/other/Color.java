package com.example.pjava.other;

/**
 * @ProjectName: Practise
 * @Package: com.example.pjava.other
 * @ClassName: Color
 * @Description: 枚举类测试
 * @Author: 毛毛虫
 * @CreateDate: 2021/10/20 3:06 下午
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/10/20 3:06 下午
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public enum Color {
    RED("红色",1),GREEN("绿色",2);
    private String name;
    private int index;
    private Color(String name,int index){
        this.name=name;
        this.index=index;
    }

    public static void showName(int index){
        for (Color color : Color.values()){
            if (color.index==index){
                System.out.println("The color is "+color.name);
            }
        }
    }

    public static void main(String[] args) {
        showName(2);
        showName(1);
        showName(0);
    }
}
