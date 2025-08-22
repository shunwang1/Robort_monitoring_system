package cn.iua.caas.robotdatahub.service.impl;

import cn.iua.caas.robotdatahub.Entity.user.User;
import cn.iua.caas.robotdatahub.Utils.BCryptUtil;
import cn.iua.caas.robotdatahub.mapper.UserMappers.UserAccountMapper;
import cn.iua.caas.robotdatahub.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserAccountMapper userMapper;

    private BCryptUtil BCryptUtil = new BCryptUtil();
    /**
     * 登陆功能
     * @param user 用户输入的用户和密码
     */
    @Override
    public User login(User user) {
        User u = userMapper.getUsernameAndPassword(user);
        if (u == null) {
            return null;
        } else if (BCryptUtil.matches(user.getPassword(),u.getPassword())) {
            return u;
        }
        return null;
    }


}

