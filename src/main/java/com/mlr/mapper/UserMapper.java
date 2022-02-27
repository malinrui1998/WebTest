package com.mlr.mapper;

import com.mlr.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username} and password = #{password}")
    User Login(@Param("username") String username, @Param("password") String password);

    @Select("select username from user where username = #{username}")
    User selectByUserName(String username);

    @Insert("insert into user values (null,#{username},#{password})")
    void RegisterUser(User user);
}
