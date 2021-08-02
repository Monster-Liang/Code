package com.imooc.mall.controller;





import com.imooc.mall.common.CommonResponse;
import com.imooc.mall.common.TokenUtil;
import com.imooc.mall.exception.OrderException;
import com.imooc.mall.exception.OrderExceptionType;
import com.imooc.mall.pojo.dto.UserDTO;
import com.imooc.mall.pojo.vo.UserVO;
import com.imooc.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description 用户操作对应接口
 */
@Slf4j
@RestController
@RequestMapping("/listmode/user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户的登录
     * @param name 用户名
     * @param password 密码
     * @return 返回CommonResponse得到的结果
     */
    @GetMapping("/login")
    @ApiOperation(value = "login",notes = "用户登录",tags = "UserLogin",httpMethod = "GET")
    public CommonResponse Login(@RequestParam("userName") String name,@RequestParam("password") String password){
        if (name != null && password != null){
            UserDTO userDTO = userService.authenticateUser(name,password);
            if (userDTO != null){
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userDTO, userVO);
                String userToken = TokenUtil.sign(userVO);
                return CommonResponse.success().messages("登录成功!").data(userToken);
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("未能找到对应用户！");
            }
        } else {
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR);
        }
    }

    /**
     * 用户注册
     * @param userVO 前端传入的用户注册信息
     * @return 返回CommonResponse得到的结果
     */
    @ApiOperation(value = "register",notes = "用户注册",tags = "UserRegister",httpMethod = "POST")
    @PostMapping("/register")
    public CommonResponse Register(@RequestBody @Valid UserVO userVO, BindingResult bindingResult){
        if (userVO != null){
            if (bindingResult.hasErrors()){
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("您的用户名或密码格式不正确!");
            }
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userVO,userDTO);
            boolean saveFlag = userService.saveUser(userDTO);
            if (saveFlag){
                return CommonResponse.success().messages("注册成功!");
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("输入信息存在错误!未能注册成功!");
            }
        } else {
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR);
        }
    }

}
