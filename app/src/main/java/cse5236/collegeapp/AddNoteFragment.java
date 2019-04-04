package cse5236.collegeapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddNoteFragment extends Fragment {
    private static final String TAG = "PortfolioFragment";

    private MainActivity mainActivity;
    private Button mAddButton;
    private Button mDeleteButton;
    private Button mShowButton;
    private Button mUpdateButton;
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);

        // get userId
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String defaultUserId = getResources().getString(R.string.default_user_id_key);
        final String userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);

        mAddButton = (Button) v.findViewById(R.id.add_button);
        mDeleteButton = (Button) v.findViewById(R.id.delete_button);
        mShowButton = (Button) v.findViewById(R.id.show_button);
        //mUpdateButton = (Button) v.findViewById(R.id.update_button);
        final TextView textView = (TextView) v.findViewById(R.id.simpleTextView);
        final EditText mTitle = (EditText) v.findViewById(R.id.edittext_title);
        final EditText mDate = (EditText) v.findViewById(R.id.edittext_date);
        final EditText mId = (EditText) v.findViewById(R.id.edittext_id);
        final EditText mBody = (EditText) v.findViewById(R.id.edittext_body);
        final EditText mDelete = (EditText) v.findViewById(R.id.edittext_delete);

        //get the spinner from the xml.
        Spinner dropdown = v.findViewById(R.id.collegeList);
        // create a list of items for the spinner.
        final ArrayList<String> spinnerItems = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("university");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //String value = dataSnapshot.getValue(String.class);
                    ArrayList<Object> newPost = (ArrayList<Object>) dataSnapshot.getValue();
                    for (int i = 1; i < newPost.size(); i++) {
                        HashMap<String, String> university = (HashMap<String, String>) newPost.get(i);
                        String nameString = university.get("Name");
                        spinnerItems.add(nameString);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("user");

                String title = mTitle.getText().toString();
                String d = mDate.getText().toString();
                String nID = mId.getText().toString();
                String b = mBody.getText().toString();

                Note note1 = new Note(b, d, nID, title);
                myRef.child(userId).child("portfolio").child("notes").child(nID).setValue(note1);
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("user");
                String nID = mDelete.getText().toString();
                myRef.child(userId).child("portfolio").child("notes").child(nID).removeValue();
                //textView.setText("");
            }
        });

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("user");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            //String value = dataSnapshot.getValue(String.class);
                            HashMap<String, String> note = (HashMap<String, String>) dataSnapshot.getValue();
                            dataSnapshot.child(userId).child("portfolio").child("notes").getValue();
                            String titleString = note.get("Title");
                            String dateString = note.get("Date");
                            String noteIDString = note.get("NoteID");
                            String bodyString = note.get("Body");
                            String value = "Title: " + titleString + " Date: " + dateString + " NoteID: " + noteIDString + " Body: " + bodyString;
                            textView.append("\n\n" + value);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
