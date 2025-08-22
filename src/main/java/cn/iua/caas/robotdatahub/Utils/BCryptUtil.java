package cn.iua.caas.robotdatahub.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtil {

    //设置强度
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);//强度10-12

    public String encode(String password) {
        //自动生成salt+哈希
        return encoder.encode(password);
    }

    public boolean matches(String password, String encodedPassword) {
        //密码检测
        return encoder.matches(password, encodedPassword);
    }

}
