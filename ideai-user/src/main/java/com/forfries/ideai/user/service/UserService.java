package com.forfries.ideai.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forfries.ideai.user.model.User;
import com.forfries.ideai.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
