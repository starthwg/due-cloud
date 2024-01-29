package com.due.cloud.bridge.mysql;

public class CodeGeneratorFactory {

    public static void main(String[] args) {
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.setAuthor("HanWenGang");
        codeGenerator.setOutDir("G:\\due-cloud\\com\\due\\module\\customer");
        codeGenerator.setDbUrl("jdbc:mysql://192.168.26.128:3306/cloud_customer?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        codeGenerator.setDbUsername("root");
        codeGenerator.setDbPassword("root");
        codeGenerator.setDbDriver("com.mysql.jdbc.Driver");

        codeGenerator.setPkParent("com.due.cloud.module.customer");
        codeGenerator.addTable("tb_customer", "tb_customer_account");

        codeGenerator.gc();
    }
}
