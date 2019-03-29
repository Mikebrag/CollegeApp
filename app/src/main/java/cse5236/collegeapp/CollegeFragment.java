package cse5236.collegeapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;

public class CollegeFragment extends Fragment {
    private static final String TAG = "CollegeFragment";



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
        View v = inflater.inflate(R.layout.fragment_college, container, false);

        final TextView textView = (TextView) v.findViewById(R.id.collegeTextView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("university");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //String value = dataSnapshot.getValue(String.class);
                    ArrayList<Object> newPost = (ArrayList<Object>) dataSnapshot.getValue();

                    for(int i=1; i<newPost.size(); i++) {

                        HashMap<String, String> university = (HashMap<String, String>) newPost.get(i);
                        String nameString = university.get("Name");
                        String idString = university.get("UniversityID");
                        String cityString = university.get("City");
                        String stateString = university.get("State");
                        String zipString = university.get("Zip");
                        String sizeString = university.get("Size");
                        String value = "University: " + nameString + " ID: " + idString + " Size: "+ sizeString +"\n City: " + cityString + " State: " + stateString + " Zip " + zipString;
                        textView.append("\n\n"+value);
                    }
                    //Log.d("showButton", "Value is: " + value);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

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
