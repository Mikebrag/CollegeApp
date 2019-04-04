package cse5236.collegeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class PortfolioFragment extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_portfolio, container, false);

        fragmentManager = getFragmentManager();

        Button myUniversitiesButton = v.findViewById(R.id.my_universities_button);
        Button myNotesButton = v.findViewById(R.id.my_notes_button);

        myUniversitiesButton.setOnClickListener(this);
        myNotesButton.setOnClickListener(this);

        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setTitle(R.string.app_name);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_universities_button:
                fragmentManager.beginTransaction().replace(R.id.fragContent, new MyUniversitiesFragment(), "portfolio")
                        .addToBackStack("portfolio").commit();
                break;
            case R.id.my_notes_button:
                fragmentManager.beginTransaction().replace(R.id.fragContent, new MyNotesFragment(), "portfolio")
                        .addToBackStack("portfolio").commit();
                break;
        }
    }
}
