package cse5236.collegeapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UniversityListFragment extends Fragment {
    private static final String TAG = "UniversityListFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;


    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "Entering onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Entering onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Entering onCreateView");

        Toast.makeText(getActivity(), R.string.title_activity_college, Toast.LENGTH_SHORT).show();
        View v = inflater.inflate(R.layout.fragment_university_list, container, false);

        // Get Firebase instance
        Query query = FirebaseDatabase.getInstance()
                .getReference("university");

        FirebaseRecyclerOptions<University> options =
                new FirebaseRecyclerOptions.Builder<University>()
                        .setQuery(query, University.class)
                        .build();

        recyclerView = v.findViewById(R.id.university_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<University, UniversityListViewHolder>(options) {
            @Override
            public UniversityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_university_list_item, parent, false);

                return new UniversityListViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(UniversityListViewHolder holder, int position, University model) {
                // Bind the University object to the UniversityListViewHolder
                holder.textView.setText(model.getName());
                Log.d(TAG, model.getName());
            }

        };

        // specify adapter
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Listening");
        firebaseRecyclerAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public class UniversityListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public UniversityListViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.university_name_text_view);
        }
    }
}
