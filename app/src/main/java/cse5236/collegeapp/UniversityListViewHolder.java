package cse5236.collegeapp;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

public class UniversityListViewHolder extends RecyclerView.ViewHolder {

    private UniversityListViewHolder.ClickListener mClickListener;
    public TextView textView;
    public String universityId;

    public UniversityListViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.university_name_text_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(v, universityId);
                }
            }

        });
    }

    // Interface to send callbacks
    public interface ClickListener {
        void onItemClick(View view, String universityId);
    }

    public void setOnClickListener(UniversityListViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
