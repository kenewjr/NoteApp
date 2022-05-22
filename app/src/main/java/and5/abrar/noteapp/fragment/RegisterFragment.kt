package and5.abrar.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.abrar.noteapp.R
import and5.abrar.noteapp.datastore.NoteManager
import and5.abrar.noteapp.room.user.User
import and5.abrar.noteapp.room.user.UserDatabase
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login2.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.btn_register
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {
    lateinit var noteManager: NoteManager
    var userDb : UserDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDb = UserDatabase.getinstance(requireContext())
        btn_register.setOnClickListener {
            tambahuser()
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    fun tambahuser(){
        noteManager = NoteManager(requireContext())
         val nama = reg_name.text.toString()
        val username = reg_username.text.toString()
        val password = reg_password.text.toString()
        val konfirmasi = reg_confrimpass.text.toString()
        if(username.isNotEmpty() && password.isNotEmpty() && nama.isNotEmpty() && konfirmasi.isNotEmpty()){
            if (password == konfirmasi){
                Toast.makeText(requireContext(), "Berhasil registrasi", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    userDb?.userdao()?.addUser(User(username,nama,password))
            }
            }else{
                Toast.makeText(requireContext(),"Password harus sama",Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(),"Semua Field harus di isi",Toast.LENGTH_SHORT).show()
        }
    }
}