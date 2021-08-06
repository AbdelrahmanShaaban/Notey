package com.example.notey.ui

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.example.notey.presistence.Note
import com.example.notey.presistence.NoteDatabase
import com.example.notey.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.launch


class AddFragment : BaseFragment() {

    private lateinit var fab : FloatingActionButton
    private lateinit var titleEt : EditText
    private lateinit var noteEt : EditText
    private  var note : Note? = null
    private lateinit var linearLayout : LinearLayout
    private lateinit var imageColor1 : ImageView
    private lateinit var imageColor2 : ImageView
    private lateinit var imageColor3 : ImageView
    private lateinit var imageColor4 : ImageView
    private lateinit var imageColor5 : ImageView
    private  var selectedNoteColor  : String? = null
    private lateinit var  constraintLayout :View

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayout = view.findViewById(R.id.linear)
        constraintLayout = view.findViewById(R.id.addNoteFragment)
        imageColor1 = linearLayout.findViewById(R.id.notecolor1)
        imageColor2 = linearLayout.findViewById(R.id.notecolor2)
        imageColor3 = linearLayout.findViewById(R.id.notecolor3)
        imageColor4 = linearLayout.findViewById(R.id.notecolor4)
        imageColor5 = linearLayout.findViewById(R.id.notecolor5)
        selectedNoteColor = "#263238"
        fab = view.findViewById(R.id.fab)
        titleEt=view.findViewById(R.id.titleEditText)
        noteEt=view.findViewById(R.id.noteEditText)
        note= arguments?.getSerializable("note") as Note?
        titleEt.setText(note?.title)
        noteEt.setText(note?.note)
        fab.setOnClickListener {
            val noteTitle = titleEt.text.toString().trim()
            val noteBody = noteEt.text.toString().trim()
            var noteColor = selectedNoteColor
            launch{
                context?.let {
                    val mNote = Note(noteTitle , noteBody , noteColor)
                    if(note == null)
                    {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        val bundleNote = Bundle()
                        Navigation.findNavController(requireView()).popBackStack()
                    }
                    else{
                        mNote.id=note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        val bundleNote = Bundle()
                        Navigation.findNavController(requireView()).popBackStack()

                    }
                }
            }
        }
        initDifferent()
        linearLayout.findViewById<View>(R.id.viewcolor1).setOnClickListener {
            selectedNoteColor = "#bdbdbd"
            constraintLayout.setBackgroundColor(Color.parseColor(selectedNoteColor))
            imageColor1.setImageResource(R.drawable.ic_baseline_check_24)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)

        }
        linearLayout.findViewById<View>(R.id.viewcolor2).setOnClickListener {
            selectedNoteColor = "#288F76"
            constraintLayout.setBackgroundColor(Color.parseColor(selectedNoteColor))
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(R.drawable.ic_baseline_check_24)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)

        }
        linearLayout.findViewById<View>(R.id.viewcolor3).setOnClickListener {
            selectedNoteColor = "#FF8587"
            constraintLayout.setBackgroundColor(Color.parseColor(selectedNoteColor))
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(R.drawable.ic_baseline_check_24)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)

        }
        linearLayout.findViewById<View>(R.id.viewcolor4).setOnClickListener {
            selectedNoteColor = "#EA8B1F"
            constraintLayout.setBackgroundColor(Color.parseColor(selectedNoteColor))
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(R.drawable.ic_baseline_check_24)
            imageColor5.setImageResource(0)

        }
        linearLayout.findViewById<View>(R.id.viewcolor5).setOnClickListener {
            selectedNoteColor = "#FFDA99"
            constraintLayout.setBackgroundColor(Color.parseColor(selectedNoteColor))
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(R.drawable.ic_baseline_check_24)

        }
        if (note != null && note?.color!=null)
        {
            when(note?.color)
            {
                "#bdbdbd" -> linearLayout.findViewById<View>(R.id.viewcolor1).performClick()
                "#288F76" -> linearLayout.findViewById<View>(R.id.viewcolor2).performClick()
                "#FF8587" -> linearLayout.findViewById<View>(R.id.viewcolor3).performClick()
                "#EA8B1F" -> linearLayout.findViewById<View>(R.id.viewcolor4).performClick()
                "#FFDA99" -> linearLayout.findViewById<View>(R.id.viewcolor5).performClick()
            }
        }
    }
    private fun deleteNote() {
        launch{
                    context?.let { NoteDatabase(it).getNoteDao().deleteNote(note!!) }
            }

        }
    private fun initDifferent() {
        val bottomSheetBehavior = BottomSheetBehavior.from(linearLayout)
        linearLayout.findViewById<TextView>(R.id.noteColorText).setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        if (note != null) {
            linearLayout.findViewById<LinearLayout>(R.id.layoutDelete).isVisible = true
            linearLayout.findViewById<LinearLayout>(R.id.layoutDelete).setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                deleteNote()
                Navigation.findNavController(requireView()).popBackStack()

            }
        }
    }
}