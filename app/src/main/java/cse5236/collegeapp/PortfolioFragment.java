package cse5236.collegeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class PortfolioFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getActivity(), R.string.title_activity_portfolio, Toast.LENGTH_SHORT).show();
        View v = inflater.inflate(R.layout.fragment_portfolio, container, false);
        return v;
    }

}
