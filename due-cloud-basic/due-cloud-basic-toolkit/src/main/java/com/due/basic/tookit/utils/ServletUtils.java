package com.due.basic.tookit.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ServletUtils {

//    /**
//     * 成功响应
//     *
//     * @param response
//     */
//    public void responseSuccess(HttpServletResponse response, Result<?> result) {
//        setResponseJSON(response, HttpServletResponse.SC_OK);
//        try {
//            writeValue(response, result);
//        } catch (Exception ex) {
//            log.error("系统异常", ex);
//        }
//    }
//
//    /**
//     * 异常响应
//     */
//    public void responseError(HttpServletResponse response,Result<?> result, int status) {
//        setResponseJSON(response, status);
//        try {
//            writeValue(response, result);
//        } catch (IOException e) {
//            log.error("异常:{}", e.getMessage());
//        }
//    }
//
//
//    private void setResponseJSON(HttpServletResponse response, int status) {
//        if (null == response) return;
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(status);
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Cache-Control", "no-cache");
//    }
//
//
//    private void writeValue(HttpServletResponse response, Result<?> result) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        //允许使用未带引号的字段名
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        //允许使用单引号
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        mapper.writeValue(response.getOutputStream(), result);
//    }
}
