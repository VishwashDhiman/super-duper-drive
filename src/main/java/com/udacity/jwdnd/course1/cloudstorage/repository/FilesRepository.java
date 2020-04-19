package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.entities.Files;
import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;
// FILEID  	FILENAME  	CONTENTTYPE  	FILESIZE  	USERID  	FILEDATA

@Mapper
public interface FilesRepository {

    @Select("SELECT * FROM Files WHERE userId = #{userId}")
    public List<Files> getAllFiles(String userId);

    @Select("SELECT * FROM Files WHERE userId = #{userId} and fileName = #{fileName}")
    public Files getFileById(String userId, String fileName);

    @Delete("DELETE FROM Files WHERE userId = #{userId} AND fileId = #{fileId}")
    public int deleteFileById(String userId,String fileId);

    @Insert("INSERT INTO Files(userId, fileName, contentType, fileSize, fileData) VALUES (#{userId}, #{file.fileName}, #{file.contentType}, #{file.fileSize}, #{file.fileData})")
    public int insertFiles(String userId, Files file);

}

