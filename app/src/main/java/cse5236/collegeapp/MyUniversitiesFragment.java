package cse5236.collegeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MyUniversitiesFragment extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPref;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_universities, container, false);

        recyclerView = v.findViewById(R.id.my_universities_recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Get userId from Shared Preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String defaultUserId = getResources().getString(R.string.default_user_id_key);
        String userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);

        // Get Firebase instance
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        Query query = firebase.child("user").child(userId).child("portfolio").child("universities");

        FirebaseRecyclerOptions<University> options =
                new FirebaseRecyclerOptions.Builder<University>()
                        .setQuery(query, University.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<University, UniversityListViewHolder>(options) {
            @Override
            public UniversityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_university_list_item, parent, false);
                UniversityListViewHolder universityListViewHolder = new UniversityListViewHolder(view);

                universityListViewHolder.setOnClickListener(new UniversityListViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, String universityId) {
                        Bundle bundle = new Bundle();
                        bundle.putString("universityId", universityId);
                        // set UniversityFragment args
                        UniversityFragment universityFragment = new UniversityFragment();
                        universityFragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragContent, universityFragment, "myUniversities")
                                .addToBackStack("myUniversities").commit();
                    }
                });
                return universityListViewHolder;
            }

            @Override
            protected void onBindViewHolder(UniversityListViewHolder holder, int position, University model) {
                // Bind the University object to the UniversityListViewHolder
                holder.textView.setText(model.Name);
                holder.universityId = model.UniversityID;
            }
        };

        // specify adapter
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        // Change action bar nav drawer button to a back button
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionbar.setTitle(R.string.my_schools);
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
}
