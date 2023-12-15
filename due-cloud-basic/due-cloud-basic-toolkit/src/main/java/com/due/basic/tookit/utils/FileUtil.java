package com.due.basic.tookit.utils;

import java.util.UUID;

public final class FileUtil {

    /**
     * 获取文件后置  png
     *
     * @param fileName 文件名称
     * @return string 文件类型
     */
    public static String fileType(String fileName) {
        if (LogicUtil.isAllBlank(fileName)) return null;
        String name = fileName;
        int index = fileName.indexOf(".");
        if (index > -1) {
            name = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return name.toLowerCase();
    }

    /**
     * 获取文件名称
     *
     * @return string类型
     */
    public static String getFileName() {
        return UUID.randomUUID().toString();
    }
}
