package cn.iua.caas.robotdatahub.service;

import cn.iua.caas.robotdatahub.Entity.user.User;

public interface UserService {
    //登陆功能
    User login(User user);
}
