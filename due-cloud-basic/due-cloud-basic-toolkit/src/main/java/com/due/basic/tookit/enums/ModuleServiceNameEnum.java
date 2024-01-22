package com.due.basic.tookit.enums;

/**
 *  默认名称
 */
public enum ModuleServiceNameEnum {

    /**
     *  用户基础模块
     */
    ModuleCustomer,

    /**
     *  文件基础模块
     */
    ModuleFile,

    /**
     *  安全基础模块
     */
    ModuleSecurity;


    public static void main(String[] args) {
        System.out.println(ModuleServiceNameEnum.ModuleCustomer.name());
    }
}
