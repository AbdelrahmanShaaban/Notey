package com.example.notey.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notey.presistence.NoteDatabase
import com.example.notey.presistence.NotesAdapter
import com.example.notey.R
import com.example.notey.presistence.Note
import com.example.notey.utils.loadImageFromStorage
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.Hold
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


class NotesFragment : BaseFragment() {

    private lateinit var userTv : TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var circleImageView: CircleImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var materialCard: MaterialCardView

    private lateinit var fab : FloatingActionButton
    private  lateinit var  myPath : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fragment A’s exitTransition can be set any time before Fragment A is
        // replaced with Fragment B. Ensure Hold's duration is set to the same
        // duration as your MaterialContainerTransform.
        exitTransition = Hold()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = view.findViewById(R.id.fab_Add)
        sharedPreferences = activity?.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)!!
        userTv= view.findViewById(R.id.usersText)
        circleImageView = view.findViewById(R.id.profile_image)
        userTv.text = sharedPreferences.getString("NAME", "User Name ")
        myPath = sharedPreferences.getString("Image", "").toString()

        circleImageView.setImageBitmap(requireContext().loadImageFromStorage(myPath))



        recyclerView = view.findViewById(R.id.rv)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fab.setOnClickListener {
            val bundleNote = Bundle()
            Navigation.findNavController(requireView()).navigate(
                    R.id.action_notesFragment_to_addFragment,
                    bundleNote)
        }
        userTv.setOnClickListener {
            val bundleNote = Bundle()
            Navigation.findNavController(requireView()).navigate(
                    R.id.action_notesFragment_to_profileFragment,
                    bundleNote
            )
        }
        circleImageView.setOnClickListener {
            val bundleNote = Bundle()
            Navigation.findNavController(requireView()).navigate(
                    R.id.action_notesFragment_to_profileFragment,
                bundleNote
            )
        }

        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                recyclerView.adapter = NotesAdapter(notes){ note: Note, materialCardView: MaterialCardView ->
                    val bundleNote = Bundle()
                    bundleNote.putSerializable("note" , note)
                    val extras = FragmentNavigatorExtras(materialCardView to "shared_element_container")
                    Navigation.findNavController(requireView()).navigate(R.id.action_notesFragment_to_addFragment, bundleNote, null, extras)

                }
            }
        }
    }

}