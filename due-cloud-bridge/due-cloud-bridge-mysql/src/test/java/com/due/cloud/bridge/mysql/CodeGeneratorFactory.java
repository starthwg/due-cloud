package com.due.cloud.bridge.mysql;

public class CodeGeneratorFactory {

    public static void main(String[] args) {
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.setAuthor("HanWenGang");
        codeGenerator.setOutDir("G:\\due-cloud\\com\\due\\module\\security");
        codeGenerator.setDbUrl("jdbc:mysql://192.168.26.128:3306/cloud_security?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        codeGenerator.setDbUsername("root");
        codeGenerator.setDbPassword("root");
        codeGenerator.setDbDriver("com.mysql.jdbc.Driver");

        codeGenerator.setPkParent("com.due.cloud.module.security");
        codeGenerator.addTable("tb_system_member", "tb_system_resource", "tb_system_role", "tb_system_role_member_mapping", "tb_system_role_resource_mapping");

        codeGenerator.gc();
    }
}
