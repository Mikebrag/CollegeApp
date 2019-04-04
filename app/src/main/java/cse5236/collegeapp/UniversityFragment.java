package cse5236.collegeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class UniversityFragment extends Fragment {
    private static final String TAG = "UniversityFragment";

    SharedPreferences sharedPref;
    FirebaseDatabase firebase;
    MainActivity mainActivity;

    String size = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_university, container, false);
        final TextView universityNameTextView = v.findViewById(R.id.university_name_text_view);
        final TextView universityInfoTextView = v.findViewById(R.id.university_info_text_view);
        final RatingBar ratingBar = v.findViewById(R.id.university_rating_bar);
        final String universityId = getArguments().getString("universityId");

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference("university");
        ref.child(universityId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final HashMap<String, String> university = (HashMap<String, String>) dataSnapshot.getValue();
                String universityName = university.get("Name");
                final String universityId = university.get("UniversityID");
                String universityCity = university.get("City");
                String universityState = university.get("State");
                String universityZip = university.get("Zip");
                String universitySize = university.get("Size");
                universityNameTextView.setText(universityName);
                universityInfoTextView.setText("Size: " + universitySize + "\nCity: " + universityCity + "\nState: " + universityState + "\nZip " + universityZip);

                final Button addToPortfolioButton = v.findViewById(R.id.add_to_portfolio_button);
                final Button removeFromPortfolioButton = v.findViewById(R.id.remove_from_portfolio_button);

                // Set initial button visibility
                String defaultUserId = getResources().getString(R.string.default_user_id_key);
                final String userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);
                final DatabaseReference dbRef = firebase.getReference("user").child(userId);
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("portfolio").child("universities").hasChild(universityId)) {
                            // Set Add to Portfolio button to invisible
                            addToPortfolioButton.setVisibility(View.GONE);
                            // Set Remove from Portfolio button to visible
                            removeFromPortfolioButton.setVisibility(View.VISIBLE);
                        } else {
                            // Set Remove from Portfolio button to invisible
                            removeFromPortfolioButton.setVisibility(View.GONE);
                            // Set Add to Portfolio button to visible
                            addToPortfolioButton.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // Set onClickListener for Add to Portfolio button
                addToPortfolioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Set Add to Portfolio button to invisible
                        addToPortfolioButton.setVisibility(View.GONE);
                        // Set Remove from Portfolio button to visible
                        removeFromPortfolioButton.setVisibility(View.VISIBLE);
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.child("portfolio").child("universities").hasChild(universityId)) {
                                    // Add user info to Firebase
                                    dbRef.child("portfolio").child("universities").child(universityId).setValue(university);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                // Set onClickListener for Remove from Portfolio button
                removeFromPortfolioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Set Remove from Portfolio button to invisible
                        removeFromPortfolioButton.setVisibility(View.GONE);
                        // Set Add to Portfolio button to visible
                        addToPortfolioButton.setVisibility(View.VISIBLE);
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("portfolio").child("universities").hasChild(universityId)) {
                                    // Add user info to Firebase
                                    dbRef.child("portfolio").child("universities").child(universityId).removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                DatabaseReference ratingRef = firebase.getReference("rating");
                String rate = String.valueOf(rating);
                String defaultUserId = getResources().getString(R.string.default_user_id_key);
                final String userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);
                final Rating r = new Rating(rate, "0", universityId, userId);
//                ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            //String value = dataSnapshot.getValue(String.class);
//                            size = (((ArrayList<Object>) dataSnapshot.getValue()).size())+"";
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
                DatabaseReference myRef = firebase.getReference();
                myRef.child("rating").child(size).setValue(r);
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
