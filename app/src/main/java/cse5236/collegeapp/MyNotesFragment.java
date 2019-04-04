package cse5236.collegeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyNotesFragment extends Fragment implements View.OnClickListener {

    private MainActivity mainActivity;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPref;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_notes, container, false);

        fragmentManager = getFragmentManager();

        Button addNoteButton = v.findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(this);

        recyclerView = v.findViewById(R.id.my_notes_recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Get userId from Shared Preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String defaultUserId = getResources().getString(R.string.default_user_id_key);
        String userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);

        // Get Firebase instance
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        Query query = firebase.child("user").child(userId).child("portfolio").child("notes");

        FirebaseRecyclerOptions<Note> options =
                new FirebaseRecyclerOptions.Builder<Note>()
                        .setQuery(query, Note.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Note, NoteListViewHolder>(options) {
            @Override
            public NoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_university_list_item, parent, false);
                NoteListViewHolder noteListViewHolder = new NoteListViewHolder(view);

                noteListViewHolder.setOnClickListener(new NoteListViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, String noteId) {
                        Bundle bundle = new Bundle();
                        bundle.putString("noteId", noteId);
                        // set UniversityFragment args
                        NoteFragment noteFragment = new NoteFragment();
                        noteFragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragContent, noteFragment, "myNotes")
                                .addToBackStack("myNotes").commit();
                    }
                });
                return noteListViewHolder;
            }

            @Override
            protected void onBindViewHolder(NoteListViewHolder holder, int position, Note model) {
                // Bind the Note object to the NoteListViewHolder
                holder.textView.setText(model.Title);
                holder.noteId = model.NoteID;
            }
        };

        // specify adapter
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        // Change action bar nav drawer button to a back button
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionbar.setTitle(R.string.my_notes);
        mainActivity = (MainActivity) getActivity();
        mainActivity.backButtonEnabled = true;
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Change action back button to nav drawer button
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mainActivity = (MainActivity) getActivity();
        mainActivity.backButtonEnabled = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_note_button:
                fragmentManager.beginTransaction().replace(R.id.fragContent, new AddNoteFragment(), "myNotes")
                        .addToBackStack("myNotes").commit();
                break;
        }
    }
}
