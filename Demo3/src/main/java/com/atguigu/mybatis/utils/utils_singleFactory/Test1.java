package com.atguigu.mybatis.utils.utils_singleFactory;

public class Test1 {
    public static void main(String[] args) {
        Bean1 bean1 = testUtils.getBean1();
        System.out.println(bean1);
        Bean1 bean2 = testUtils.getBean1();
        System.out.println(bean2);
    }
}
