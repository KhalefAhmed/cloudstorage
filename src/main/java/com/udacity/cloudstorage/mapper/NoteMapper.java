package com.udacity.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotesByUserId(Integer userId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(Note note);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int saveNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteNoteByNoteId(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteId}")
    void updateNote(Integer noteId, String title, String description);
}
