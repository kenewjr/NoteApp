package and5.abrar.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.abrar.noteapp.R
import and5.abrar.noteapp.datastore.NoteManager
import and5.abrar.noteapp.room.Note
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login2.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            login()
        }
        btn_register.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    fun login() {
        noteManager = NoteManager(requireContext())
        val username = login_username.text.toString()
        val password = login_password.text.toString()
        var user = ""
        var pass = ""
        if (username.isNotEmpty() && password.isNotEmpty()) {
            noteManager.userName.asLiveData().observe(requireActivity()) {
                user = it.toString()
            }

            noteManager.Pass.asLiveData().observe(requireActivity()) {
                pass = it.toString()
            }
            if (username == user && pass == password) {
                Toast.makeText(requireContext(), "Berhasil login", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    noteManager.setBoolean(true)
                }
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_loginFragment_to_noteFragment)

            }
        }
    }
}