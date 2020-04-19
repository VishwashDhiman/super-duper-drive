package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteRepository {

    @Select("SELECT * FROM Notes WHERE userId = #{userId}")
    public List<Notes> getAllNotesById(String userId);

    @Select("SELECT * FROM Notes WHERE userId = #{userId} AND noteId = #{noteId} ")
    public Notes getNote(String userId, String noteId);

    @Delete("DELETE FROM Notes WHERE userId = #{userId} AND noteId = #{noteId}")
    public int deleteNoteById(String userId,String noteId);

    @Insert("INSERT INTO Notes(userId, noteTitle, noteDescription) VALUES (#{userId}, #{note.noteTitle}, #{note.noteDescription})")
    public int insertNotes(String userId,Notes note);

    @Update("UPDATE Notes SET noteId = #{note.noteId}, noteTitle = #{note.noteTitle}, noteDescription = #{note.noteDescription} WHERE userId = #{userId} AND noteId = #{note.noteId}")
    public int updateNote(String userId,Notes note);
}

