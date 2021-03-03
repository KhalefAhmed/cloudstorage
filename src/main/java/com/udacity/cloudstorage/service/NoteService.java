package com.udacity.cloudstorage.service;


import com.udacity.cloudstorage.entity.Note;
import com.udacity.cloudstorage.mapper.NoteMapper;
import com.udacity.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserMapper userMapper;


    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public List<Note> getNotesByUserId(Integer userId){
        return noteMapper.getNotesByUserId(userId);
    }

    public Integer createNote(String title, String description, String userName){

        Integer userId = userMapper.getUser(userName).getUserId();
        Note note = new Note(null, title, description, userId);
        Integer noteId = noteMapper.saveNote(note);

        return noteId;
    }

    public void updateNote(Integer noteId, String title, String description) {
        noteMapper.updateNote(noteId, title, description);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNoteByNoteId(noteId);
    }

}
