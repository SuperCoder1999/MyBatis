package com.atguigu.mybatis.utils.utils_singleFactory;

public class testUtils {

    public static Factory_ factory_ = null;

    //静态代码块,初始化 静态成员变量. 保证始终只有一个Factory .  这在JDBC的JDBCUtilsByDruid.java中DataSource中也有应用
    static{
        factory_ = new Factory_();

    }
    public static Bean1 getBean1() {
        System.out.println("fact" + factory_);
        Bean1 bean1 = null;
        bean1 = factory_.build();
        return bean1;
    }
}
