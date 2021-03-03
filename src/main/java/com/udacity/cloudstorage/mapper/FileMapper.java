package com.udacity.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    List<String> getFilesByUserId(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    void deleteFile(String fileName);
}
