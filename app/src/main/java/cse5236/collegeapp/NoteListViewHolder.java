package cse5236.collegeapp;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteListViewHolder extends RecyclerView.ViewHolder {

    private NoteListViewHolder.ClickListener mClickListener;
    public TextView textView;
    public String noteId;

    public NoteListViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.university_name_text_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(v, noteId);
                }
            }

        });
    }

    // Interface to send callbacks
    public interface ClickListener {
        void onItemClick(View view, String noteId);
    }

    public void setOnClickListener(NoteListViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
