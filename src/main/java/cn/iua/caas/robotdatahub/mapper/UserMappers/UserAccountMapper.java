package cn.iua.caas.robotdatahub.mapper.UserMappers;

import cn.iua.caas.robotdatahub.Entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserAccountMapper {

    @Select("select * from users where username =  #{username}")
    User getUsernameAndPassword(User user);
}
