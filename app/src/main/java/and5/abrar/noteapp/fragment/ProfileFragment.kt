package and5.abrar.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.abrar.noteapp.R
import and5.abrar.noteapp.datastore.NoteManager
import and5.abrar.noteapp.room.NoteDatabase
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteManager.userName.asLiveData().observe(viewLifecycleOwner){
            GlobalScope.async {
                val user = noteDatabase?.notedao()?.getUserRegistered(it)
                requireActivity().runOnUiThread{
                    profile_username.text = it.toString()
                    profile_name.text = user?.nama
                }
            }
        }
        btn_logout.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
            GlobalScope.launch {
                noteManager.setBoolean(false)
            }
        }
    }
}