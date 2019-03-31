package cse5236.collegeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class UniversityFragment extends Fragment {

    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_university, container, false);
        final TextView universityNameTextView = v.findViewById(R.id.university_name_text_view);
        final TextView universityInfoTextView = v.findViewById(R.id.university_info_text_view);
        String universityId = getArguments().getString("universityId");

        // Find all users which match the child node email.
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference("university");
        ref.child(universityId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> university = (HashMap<String, String>) dataSnapshot.getValue();
                String nameString = university.get("Name");
                String idString = university.get("UniversityID");
                String cityString = university.get("City");
                String stateString = university.get("State");
                String zipString = university.get("Zip");
                String sizeString = university.get("Size");
                universityNameTextView.setText(nameString);
                universityInfoTextView.setText("Size: " + sizeString + "\nCity: " + cityString + "\nState: " + stateString + "\nZip " + zipString);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        mainActivity = (MainActivity) getActivity();
        mainActivity.backButtonEnabled = true;
        return v;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ActionBar actionbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mainActivity = (MainActivity) getActivity();
        mainActivity.backButtonEnabled = false;
    }
}
