package cse5236.collegeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NoteFragment extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPref;
    FirebaseDatabase firebase;
    MainActivity mainActivity;
    FragmentManager fragmentManager;
    String noteId;
    String userId;
    Button deleteNoteButton;
    Button editNoteButton;
    Button updateNoteButton;
    View fragmentNoteView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentNoteView = inflater.inflate(R.layout.fragment_note, container, false);
        final TextView noteTitleTextView = fragmentNoteView.findViewById(R.id.note_title_text_view);
        final TextView noteDateTextView = fragmentNoteView.findViewById(R.id.note_date_text_view);
        final TextView noteBodyTextView = fragmentNoteView.findViewById(R.id.note_body_text_view);
        final TextView noteBodyEditText = fragmentNoteView.findViewById(R.id.note_body_edit_text);

        // Set onClick listeners
        deleteNoteButton = fragmentNoteView.findViewById(R.id.delete_note_button);
        editNoteButton = fragmentNoteView.findViewById(R.id.edit_note_button);
        updateNoteButton = fragmentNoteView.findViewById(R.id.update_note_button);
        deleteNoteButton.setOnClickListener(this);
        editNoteButton.setOnClickListener(this);
        updateNoteButton.setOnClickListener(this);

        // Get FragmentManager
        fragmentManager = getFragmentManager();

        // Get noteId
        noteId = getArguments().getString("noteId");

        // Get userId from Shared Preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String defaultUserId = getResources().getString(R.string.default_user_id_key);
        userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);

        firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference("user");
        ref.child(userId).child("portfolio").child("notes").child(noteId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final HashMap<String, String> note = (HashMap<String, String>) dataSnapshot.getValue();
                noteTitleTextView.setText(note.get("Title"));
                noteDateTextView.setText(note.get("Date"));
                String body = note.get("Body");
                noteBodyTextView.setText(body);
                noteBodyEditText.setText(body);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Change action bar nav drawer button to a back button
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        mainActivity = (MainActivity) getActivity();
        mainActivity.backButtonEnabled = true;
        return fragmentNoteView;
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
        DatabaseReference ref = firebase.getReference("user");
        TextView noteBodyTextView = fragmentNoteView.findViewById(R.id.note_body_text_view);
        TextView noteBodyEditText = fragmentNoteView.findViewById(R.id.note_body_edit_text);
        switch (v.getId()) {
            case R.id.delete_note_button:
                // Delete note, return to previous fragment
                ref.child(userId).child("portfolio").child("notes").child(noteId).removeValue();
                fragmentManager.popBackStack();
                break;
            case R.id.edit_note_button:
                // Set visibility of views and buttons
                noteBodyTextView.setVisibility(View.GONE);
                noteBodyEditText.setVisibility(View.VISIBLE);
                editNoteButton.setVisibility(View.GONE);
                deleteNoteButton.setVisibility(View.GONE);
                updateNoteButton.setVisibility(View.VISIBLE);
                break;
            case R.id.update_note_button:
                // Update note body
                String newBody = noteBodyEditText.getText().toString();
                ref.child(userId).child("portfolio").child("notes").child(noteId).child("Body").setValue(newBody);
                noteBodyTextView.setText(newBody);

                // Set visibility of views and buttons
                noteBodyTextView.setVisibility(View.VISIBLE);
                noteBodyEditText.setVisibility(View.GONE);
                editNoteButton.setVisibility(View.VISIBLE);
                deleteNoteButton.setVisibility(View.VISIBLE);
                updateNoteButton.setVisibility(View.GONE);
                break;
        }
    }
}
