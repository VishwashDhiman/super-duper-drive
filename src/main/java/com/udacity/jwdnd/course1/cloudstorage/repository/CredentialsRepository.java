package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsRepository {

    @Select("SELECT * FROM Credentials WHERE userId = #{userId}")
    public List<Credentials> getAllCredentialsById(String userId);

    @Select("SELECT * FROM Credentials WHERE userId = #{userId} AND credentialId = #{credentialId} ")
    public Credentials getCredential(String userId, String credentialId);

    @Delete("DELETE FROM Credentials WHERE userId = #{userId} AND credentialId = #{credentialId}")
    public int deleteCredentialById(String userId,String credentialId);

    @Insert("INSERT INTO Credentials(userId, url, userName, password) VALUES (#{userId}, #{credential.url}, #{credential.userName}, #{credential.password})")
    public int insertCredentials(String userId, Credentials credential);

    @Update("UPDATE Credentials SET credentialId = #{credential.credentialId}, url = #{credential.url}, userName = #{credential.userName}, password = #{credential.password} WHERE userId = #{userId} AND credentialID = #{credential.credentialId}")
    public int updateCredentials(String userId,Credentials credential);
}
