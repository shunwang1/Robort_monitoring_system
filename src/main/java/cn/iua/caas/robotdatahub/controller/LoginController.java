package cn.iua.caas.robotdatahub.controller;

import cn.iua.caas.robotdatahub.common.Result;
import cn.iua.caas.robotdatahub.Entity.user.User;
import cn.iua.caas.robotdatahub.Utils.JwtUtils;
import cn.iua.caas.robotdatahub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    public Result login(@RequestBody User user) {
        log.debug("处理登陆");
        User u = userService.login(user);
        if(u == null){
            return Result.error("登陆失败");
        }
        else{
            //登陆成功
            Map<String,Object> map = new HashMap<>();
            map.put("id",u.getId());
            map.put("username",u.getUsername());
            map.put("role",u.getRole());
            //生成令牌
            String jwt = JwtUtils.generateToken(map);
            log.debug("令牌生成");

            //把生成的令牌封装到响应数据中
            return Result.success(jwt);
        }
    }

}
