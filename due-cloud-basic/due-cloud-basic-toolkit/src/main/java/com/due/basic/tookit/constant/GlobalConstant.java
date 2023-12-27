package com.due.basic.tookit.constant;


/**
 * 全局常量
 */
public final class GlobalConstant {

    public final static String PAGE_PARAMS_START = "start";
    public final static String PAGE_PARAMS_LIMIT = "limit";


    public final static Long DEFAULT_PAGE_PARAMS_START = 0L;
    public final static Long DEFAULT_PAGE_PARAMS_LIMIT = 10L;


    /**
     * 项目基础路径
     */
    public final static String PROJECT_BASE_PATH = "/due";

    /**
     * app基础路径
     */
    public final static String PROJECT_APP_PATH = "/due/mobile";

    /**
     * back基础路径
     */
    public final static String PROJECT_BACK_PATH = "/due/back";

    /**
     * 调用module模块的透彻参数的key
     */
    public final static String DUE_RPC_MODULE_REQUEST = "due_rpc_request";


    /**
     *  feign的方式就行rpc
     */
    public final static String DUE_RPC_FEIGN_REQUEST = "due_rpc_feign_request";


    /**
     * 项目的生产环境表示
     */
    public final static String PROJECT_ACTIVE_PROD = "dev";

    /**
     * 服务名称
     */
    public static final String MODULE_NAME = "spring.application.name";


}
