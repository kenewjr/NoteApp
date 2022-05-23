package and5.abrar.noteapp.fragment

import and5.abrar.noteapp.R
import and5.abrar.noteapp.adapter.AdapterNote
import and5.abrar.noteapp.datastore.NoteManager
import and5.abrar.noteapp.room.NoteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@DelicateCoroutinesApi
class NoteFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    private lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNote()
        btn_add.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_noteFragment_to_addNoteFragment)
        }
        avatar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_noteFragment_to_profileFragment)
        }
    }

    @DelicateCoroutinesApi
    fun getNote(){
        GlobalScope.launch {
            val result = noteDatabase?.notedao()?.getNote()
            requireActivity().runOnUiThread {
                if (result != null) {
                    rvNote.layoutManager = LinearLayoutManager(requireContext())
                    rvNote.adapter = AdapterNote(result) {
                        val bundle = bundleOf("detail" to it)
                        Navigation.findNavController(requireView()).navigate(R.id.action_noteFragment_to_detailFragment, bundle)
                    }
                }
                }
            }
        }
    }