package and5.abrar.noteapp.room.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey var username : String,
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "pass") var pass : String
):Parcelable
