package com.example.notey.presistence

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notey.R
import com.google.android.material.card.MaterialCardView

class NotesAdapter(private val notesList : List<Note>,val clickListener: (Note,MaterialCardView) -> Unit ) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val item = LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent , false)
        return NoteViewHolder(item)

    }
    override fun getItemCount() = notesList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.title.text = notesList[position].title
        holder.noteBody.text = notesList[position].note
        holder.constraintLayout.setBackgroundColor(Color.parseColor(notesList[position].color))

        (holder as NoteViewHolder).bind(notesList[position], clickListener)

    }

    class NoteViewHolder (val view : View) : RecyclerView.ViewHolder(view) {

        var title = view.findViewById<TextView>(R.id.titletextView)
        var noteBody = view.findViewById<TextView>(R.id.notetextView)
        var constraintLayout: ConstraintLayout = view.findViewById<ConstraintLayout>(R.id.constraint)
        var cardView: MaterialCardView = view.findViewById(R.id.material_card)

        fun bind(note: Note, clickListener: (Note,MaterialCardView) -> Unit) {

            itemView.setOnClickListener { clickListener(note,cardView)}
        }
    }
}


