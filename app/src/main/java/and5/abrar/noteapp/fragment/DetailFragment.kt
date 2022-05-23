package and5.abrar.noteapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import and5.abrar.noteapp.R
import and5.abrar.noteapp.datastore.NoteManager
import and5.abrar.noteapp.room.Note
import and5.abrar.noteapp.room.NoteDatabase
import android.content.Intent
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class DetailFragment : Fragment() {
    private var noteDatabase : NoteDatabase? = null
    lateinit var noteManager: NoteManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteDatabase = NoteDatabase.getinstance(requireContext())
        noteManager = NoteManager(requireContext())
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteDetail = arguments?.getParcelable<Note>("detail") as Note
        detail_judul.text = noteDetail.judul
        detail_note.text = noteDetail.isi
        detail_waktu.text = noteDetail.waktu

        btn_Home.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_noteFragment)
        }
        btn_Share.setOnClickListener {
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,noteDetail.isi)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }
        btn_Edit.setOnClickListener {
            val bundle = bundleOf("detailEdit" to noteDetail)
            Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_editFragment, bundle)
        }
        btn_Delete.setOnClickListener {
            GlobalScope.async {
                val result = noteDatabase?.notedao()?.deleteNote(noteDetail)
                requireActivity().runOnUiThread{
                    if (result == 1){
                        Toast.makeText(requireContext(),"note dihapus",Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(requireView()).navigate(R.id.action_detailFragment_to_noteFragment)
                    }else{
                        Toast.makeText(requireContext(), "Gagal Menghapus", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}