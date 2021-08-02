package com.imooc.mall.service.impl;

package com.boss.ljt.listmode.service.impl;/**
 * @author ljt
 * @create 2021-06-04 19:00
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.mall.exception.OrderException;
import com.imooc.mall.exception.OrderExceptionType;
import com.imooc.mall.mapper.UserMapper;
import com.imooc.mall.pojo.dto.UserDTO;
import com.imooc.mall.pojo.po.UserPO;
import com.imooc.mall.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * @Description 用户业务逻辑实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO authenticateUser(String name, String password) {
        try {
            //创建queryWrapper进行条件查询
            QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("f_user_name", name);
            queryWrapper.eq("f_user_password", DigestUtils.md5DigestAsHex(password.getBytes()));
            //根据queryWrapper查询对应的用户信息
            UserPO userPO = userMapper.selectOne(queryWrapper);
            return getUserDTO(userPO);
        } catch (Exception e){
            throw new OrderException(OrderExceptionType.SYSTEM_ERROR, "用户查询出现异常!");
        }

    }

    /**
     * 将userPO转化为UserDTO
     * @param userPO 需要转换的userPO
     * @return 根据userPO封装好的userDTO
     */
    private UserDTO getUserDTO(UserPO userPO) {
        if (userPO != null){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userPO.getId());
            userDTO.setPassword(userPO.getPassword());
            userDTO.setUserName(userPO.getName());
            return userDTO;
        }
        return null;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        try {
            //验证是否已经存在相同的用户
            UserDTO authenticateUser = authenticateUser(userDTO.getUserName(), userDTO.getPassword());
            if (authenticateUser != null){
                return false;
            }
            UserPO userPO = new UserPO();
            userPO.setName(userDTO.getUserName());
            userPO.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
            int insert = userMapper.insert(userPO);
            if (insert > 0){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            throw new OrderException(OrderExceptionType.SYSTEM_ERROR, "用户添加出现异常!");
        }

    }

    @Override
    public UserDTO getUserById(Long id) {
        if (id != null && id > 0){
            UserPO userPO = userMapper.selectById(id);
            return getUserDTO(userPO);
        }
        return null;
    }
}

