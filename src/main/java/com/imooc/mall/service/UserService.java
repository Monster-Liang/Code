package com.imooc.mall.service;

import com.imooc.mall.model.pojo.User;

/**
 * 描述：     UserService
 */
public interface UserService {


    UserDTO authenticateUser(String name, String password);

    boolean saveUser(UserDTO userDTO);


    UserDTO getUserById(Long id);

}
