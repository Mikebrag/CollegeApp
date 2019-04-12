package cse5236.collegeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AddNoteFragment extends Fragment {
    private static final String TAG = "PortfolioFragment";

    private MainActivity mainActivity;
    private Button mAddButton;
    private Button mShowButton;
    private SharedPreferences sharedPref;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);

        // Get fragment manager
        fragmentManager = getFragmentManager();

        // get userId
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String defaultUserId = getResources().getString(R.string.default_user_id_key);
        final String userId = sharedPref.getString(getString(R.string.user_id_key), defaultUserId);

        mAddButton = v.findViewById(R.id.add_note_button);
        final EditText mTitle = v.findViewById(R.id.title_edit_text);
        final EditText mBody = v.findViewById(R.id.body_edit_text);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("user");

                String date = Calendar.getInstance().getTime().toString();

                String title = mTitle.getText().toString();
                String d = date;
                String nID = title + " " + date;
                String b = mBody.getText().toString();

                Note note1 = new Note(b, d, nID, title);
                myRef.child(userId).child("portfolio").child("notes").child(nID).setValue(note1);
                fragmentManager.popBackStack();
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
