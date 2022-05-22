package and5.abrar.noteapp.room.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userdao():UserDao

    companion object{
        private var INSTANCE: UserDatabase? = null

        fun getinstance(context: Context): UserDatabase?{
            if(INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java,"favorite.db").build()
                }
            }
            return INSTANCE
        }
    }
}