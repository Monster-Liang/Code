package com.imooc.mall.common;

import com.imooc.mall.exception.OrderException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName CommonResponse
 * @author admin
 * @version 1.0.0
 * @Description 统一的接口响应的数据格式
 * @createTime 2021年06月04日 16:43:00
 */
@Data
public class CommonResponse {

    /**
     * 响应成功的状态码
     */
    private static final int SUCCESS_CODE = 200;

    /**
     * 处理请求是否成功
     */
    @ApiModelProperty(value="请求是否处理成功")
    private boolean okFlag;
    /**
     * 请求响应状态码
     */
    @ApiModelProperty(value="请求响应状态码",example="200、400、500")
    private int code;
    /**
     * 请求结果描述
     */
    @ApiModelProperty(value="请求结果描述信息")
    private String message;
    /**
     * 请求结果数据
     */
    @ApiModelProperty(value="请求结果数据")
    private Object data;

    /**
     * 统一的接口结果响应类构造方法私有化
     */
    private CommonResponse(){}

    /**
     * 普通的响应成功的传回对象
     * @return 封装有成功信息的CommonResponse对象
     */
    public static CommonResponse success(){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setOkFlag(true);
        commonResponse.setCode(SUCCESS_CODE);
        commonResponse.setMessage("请求响应成功!");
        return commonResponse;
    }

    /**
     * 错误的响应传回对象
     * @param e 出现的异常
     * @return  封装有错误信息的CommonResponse对象
     */
    public static CommonResponse error(OrderException e){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setOkFlag(false);
        commonResponse.setCode(e.getCode());
        commonResponse.setMessage(e.getMessage());
        return commonResponse;
    }

    /**
     * 需要自定义传回信息链式调用此方法
     * @param message 自定义的传回信息
     * @return 封装好信息的CommonResponse对象
     */
    public CommonResponse messages(String message){
        this.setMessage(message);
        return this;
    }


    /**
     * 封装需要传回的数据
     * @param data 需要传回给前端的数据
     * @return 封装好信息的CommonResponse对象
     */
    public CommonResponse data(Object data){
        this.setData(data);
        return this;
    }
}
