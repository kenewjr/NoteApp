package and5.abrar.noteapp.room.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User):Long
    @Query("SELECT * FROM User")
    fun getUser(): List<User>
    @Delete
    fun deleteuser(user: User):Int

    @Query("SELECT * FROM User WHERE User.username = :username")
    fun getUserRegistered(username:String): User
}