package com.due.cloud.bridge.mysql;

public class CodeGeneratorFactory {

    public static void main(String[] args) {
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.setAuthor("HanWenGang");
        codeGenerator.setOutDir("G:\\due-cloud\\com\\due\\module\\file");
        codeGenerator.setDbUrl("jdbc:mysql://192.168.26.128:3306/cloud_file?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        codeGenerator.setDbUsername("root");
        codeGenerator.setDbPassword("root");
        codeGenerator.setDbDriver("com.mysql.jdbc.Driver");

        codeGenerator.setPkParent("com.due.cloud.module.file");
        codeGenerator.addTable("tb_file_record");

        codeGenerator.gc();
    }
}
