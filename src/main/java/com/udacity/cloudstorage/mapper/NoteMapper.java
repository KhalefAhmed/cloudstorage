package com.udacity.cloudstorage.mapper;

import com.udacity.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotesByUserId(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int saveNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNoteByNoteId(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    void updateNote(Integer noteId, String noteTitle, String description);
}
