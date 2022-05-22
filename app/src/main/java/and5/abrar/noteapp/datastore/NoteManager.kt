package and5.abrar.noteapp.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteManager(context: Context) {
    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_pref")
    companion object{
        val NAMA = preferencesKey<String>("nama")
        val PASS = preferencesKey<String>("pass")
        val USERNAME = preferencesKey<String>("username")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }

    suspend fun saveData(nama: String, pass: String, username:String){
        dataStore.edit {
            it[NAMA]= nama
            it[PASS]= pass
            it[USERNAME]= username
        }
    }

    val Nama : Flow<String> = dataStore.data.map {
        it[NAMA] ?:""
    }

    val Pass : Flow<String> = dataStore.data.map {
        it[PASS] ?:""
    }
    val userName : Flow<String> = dataStore.data.map {
        it[USERNAME] ?:""
    }

    val ceklogin : Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }
    suspend fun hapusData(){
        dataStore.edit {
            it.clear()
        }

    }
    suspend fun setBoolean(boolean: Boolean){
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }
}