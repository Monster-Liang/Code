package com.imooc.mall.exception;

/**
 * @Description 自定义的订单异常类型
 */
public enum OrderExceptionType {
    /**
     * 订单生成、获取错误
     */
    ORDER_ERROR(10001,"您的订单出现错误,请联系相关客服人员！"),

    /**
     * 用户输入
     */
    USER_INPUT_ERROR(100002,"您输入的信息存在错误,请联系相关客服人员！"),
    /**
     * 系统异常错误
     */
    SYSTEM_ERROR (500,"系统出现异常，请您稍后再试或联系管理员！"),
    /**
     * 未知异常错误
     */
    OTHER_ERROR(999,"系统出现未知异常，请联系管理员！");

    /**
     * 异常类型的有参构造
     * @param code 异常状态码
     * @param desc 异常信息的描述
     */
    OrderExceptionType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 异常类型中文描述
     */
    private String desc;
    /**
     * 异常状态码
     */
    private int code;

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
