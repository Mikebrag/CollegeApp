package cse5236.collegeapp;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortfolioFragment extends Fragment {
    private static final String TAG = "PortfolioFragment";

    private Button mAddButton;
    private Button mDeleteButton;
    private Button mShowButton;
    private Button mUpdateButton;




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

        Toast.makeText(getActivity(), R.string.title_activity_portfolio, Toast.LENGTH_SHORT).show();
        View v = inflater.inflate(R.layout.fragment_portfolio, container, false);

        mAddButton = (Button) v.findViewById(R.id.add_button);
        mDeleteButton = (Button) v.findViewById(R.id.delete_button);
        mShowButton = (Button) v.findViewById(R.id.show_button);
        //mUpdateButton = (Button) v.findViewById(R.id.update_button);
        final TextView textView = (TextView) v.findViewById(R.id.simpleTextView);
        final EditText mSubject = (EditText) v.findViewById(R.id.edittext_subject);
        final EditText mDate = (EditText) v.findViewById(R.id.edittext_date);
        final EditText mId = (EditText) v.findViewById(R.id.edittext_id);
        final EditText mBody = (EditText) v.findViewById(R.id.edittext_body);
        final EditText mDelete = (EditText) v.findViewById(R.id.edittext_delete);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                String subj = mSubject.getText().toString();
                String d = mDate.getText().toString();
                String nID = mId.getText().toString();
                String b = mBody.getText().toString();

                Note note1 = new Note(b, d, nID, subj);
                //myRef.setValue("Note 1");
                myRef.child("note").child(nID).setValue(note1);
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                String nID = mDelete.getText().toString();
                myRef.child("note").child(nID).removeValue();
                //textView.setText("");
            }
        });

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("note");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            //String value = dataSnapshot.getValue(String.class);
                            ArrayList<Object> newPost = (ArrayList<Object>) dataSnapshot.getValue();

                            for(int i=1; i<newPost.size(); i++) {

                                HashMap<String, String> note = (HashMap<String, String>) newPost.get(i);
                                String subjectString = note.get("Subject");
                                String dateString = note.get("Date");
                                String noteIDString = note.get("NoteID");
                                String bodyString = note.get("Body");
                                String value = "Subject: " + subjectString + " Date: " + dateString + " NoteID: " + noteIDString + " Body: " + bodyString;
                                textView.append("\n\n"+value);
                            }
                            //Log.d("showButton", "Value is: " + value);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "Entering onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "Entering onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "Entering onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "Entering onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Entering onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "Entering onDetach");
        super.onDetach();
    }
}
