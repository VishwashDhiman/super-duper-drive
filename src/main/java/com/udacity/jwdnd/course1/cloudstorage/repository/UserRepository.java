package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM Users WHERE username = #{username}")
    public User findByUsername(String username);

    @Delete("DELETE FROM Users WHERE username = #{username}")
    public int deleteByUsername(String username);

    @Insert("INSERT INTO Users(userid, username, password, salt, firstname, lastname) VALUES (#{userid}, #{username}, #{password}, null, #{firstname}, #{lastname})")
    public int insert(User User);
}
