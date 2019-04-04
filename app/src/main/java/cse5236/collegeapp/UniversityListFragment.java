package cse5236.collegeapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UniversityListFragment extends Fragment {
    private static final String TAG = "UniversityListFragment";

    private RecyclerView recyclerView;
    private SearchView searchView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_university_list, container, false);

        recyclerView = v.findViewById(R.id.university_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        searchView = v.findViewById(R.id.university_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                firebaseRecyclerAdapter = searchFirebase(searchText.toLowerCase());
                recyclerView.setAdapter(firebaseRecyclerAdapter);
                firebaseRecyclerAdapter.startListening();
                Log.d(TAG, "Query submitted");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                if (searchText.length() == 0) {
                    firebaseRecyclerAdapter = searchFirebase(searchText.toLowerCase());
                    recyclerView.setAdapter(firebaseRecyclerAdapter);
                    firebaseRecyclerAdapter.startListening();
                    Log.d(TAG, "Query submitted");
                }
                return false;
            }
        });

        // Get Firebase instance
        Query query = FirebaseDatabase.getInstance().getReference("university");

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
                        fragmentManager.beginTransaction().replace(R.id.fragContent, universityFragment, "universityList")
                                .addToBackStack("universityList").commit();
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

    public FirebaseRecyclerAdapter searchFirebase(String searchText) {
        // Get Firebase instance
        Query query;
        if (searchText.length() > 0) {
            query = FirebaseDatabase.getInstance().getReference("university")
                    .orderByChild("QueryTag")
                    .startAt(searchText)
                    .endAt(searchText+"\uf8ff");
        } else {
            query = FirebaseDatabase.getInstance().getReference("university");
        }

        FirebaseRecyclerOptions<University> options =
                new FirebaseRecyclerOptions.Builder<University>()
                        .setQuery(query, University.class)
                        .build();

        return new FirebaseRecyclerAdapter<University, UniversityListViewHolder>(options) {
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
                        fragmentManager.beginTransaction().replace(R.id.fragContent, universityFragment, "universityList")
                                .addToBackStack("universityList").commit();
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
    }
}
