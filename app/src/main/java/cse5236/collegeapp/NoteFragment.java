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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {
    private static final String TAG = "NoteFragment";

    SharedPreferences sharedPref;
    FirebaseDatabase firebase;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_note, container, false);
        final TextView noteTitleTextView = v.findViewById(R.id.note_title_text_view);
        final TextView noteBodyTextView = v.findViewById(R.id.note_body_text_view);
        String noteId = getArguments().getString("noteId");

        // Get userId from Shared Preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String defaultUserId = getResources().getString(R.string.default_user_id_key);
        String userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);

        firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference("user");
        ref.child(userId).child("portfolio").child("notes").child(noteId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final HashMap<String, String> note = (HashMap<String, String>) dataSnapshot.getValue();
                noteTitleTextView.setText(note.get("Title"));
                noteBodyTextView.setText(note.get("Body"));
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
        return v;
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
}
