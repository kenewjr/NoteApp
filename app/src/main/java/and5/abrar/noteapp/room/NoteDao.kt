package and5.abrar.noteapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert
    fun addNote(note: Note):Long
    @Query("SELECT * FROM Note")
    fun getNote(): List<Note>
    @Delete
    fun deleteNote(note: Note):Int


}