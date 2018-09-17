package com.example.jacek.trainingapp.notepad;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class NotepadAdapter extends RecyclerView.Adapter<NotepadAdapter.NoteViewHolder>
{
    private Context context;
    private ArrayList<Note> notes;
    private NotesData notesData;

    public NotepadAdapter(Context context, NotesData notesData)
    {
        this.context = context;
        this.notesData = notesData;
        notes = notesData.getAllNotes();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_notepad, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        return notes.size();
    }

    public void update()
    {
        notes = notesData.getAllNotes();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position)
    {
        holder.date.setText(notes.get(position).getDate());
        holder.time.setText(notes.get(position).getTime());
        holder.description.setText(notes.get(position).getDescription());
    }

    public Note get(String date, String time)
    {
        return notesData.get(date, time);
    }


    public void add(Note note)
    {
        notes.add(0,note);
        notesData.add(note);
        notifyItemInserted(0);
    }

    public void edit(int position, Note updated)
    {
        notesData.edit(notes.get(position), updated);
        notes.set(position, updated);
        Collections.sort(notes);
        notifyDataSetChanged();
    }

    public void delete(int position)
    {
        notesData.delete(notes.get(position));
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView time;
        TextView description;

        public NoteViewHolder(View itemView)
        {
            super(itemView);
            date = itemView.findViewById(R.id.dateNotepadRow);
            time = itemView.findViewById(R.id.timeNotepadRow);
            description = itemView.findViewById(R.id.descriptionNotepadRow);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, NoteDetailsActivity.class);
                    intent.putExtra("type","edit");
                    intent.putExtra("date",date.getText().toString());
                    intent.putExtra("time", time.getText().toString());
                    intent.putExtra(Utils.POSITION,getAdapterPosition());
                    ((AppCompatActivity)context).startActivityForResult(intent, 2);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    Utils.showPopupDelete(context, v, getAdapterPosition());
                    return false;
                }
            });
        }



    }
}
