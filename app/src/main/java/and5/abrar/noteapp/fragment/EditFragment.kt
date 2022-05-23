package and5.abrar.noteapp.fragment

import and5.abrar.noteapp.R
import and5.abrar.noteapp.datastore.NoteManager
import and5.abrar.noteapp.room.Note
import and5.abrar.noteapp.room.NoteDatabase
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Suppress("DeferredResultUnused")
@DelicateCoroutinesApi
class EditFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    private lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteDetail = arguments?.getParcelable<Note>("detailEdit") as Note
        edit_judul.setText(noteDetail.judul)
        edit_isi.setText(noteDetail.isi)

        btn_edit.setOnClickListener {
            val judul = edit_judul.text.toString()
            val isi = edit_isi.text.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            val up = Note(noteDetail.id,judul,formatted.toString(),isi)
            GlobalScope.async {
                val result = noteDatabase?.notedao()?.updateNote(up)

                requireActivity().runOnUiThread{
                    if (result != 0){
                        Navigation.findNavController(view).navigate(R.id.action_editFragment_to_noteFragment)
                    }
                }
            }
        }
    }
}