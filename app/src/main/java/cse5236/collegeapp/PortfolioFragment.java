package cse5236.collegeapp;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        mUpdateButton = (Button) v.findViewById(R.id.update_button);
        final TextView textView = (TextView) v.findViewById(R.id.simpleTextView);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue("Note 1");
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.removeValue();
                textView.setText("");
            }
        });

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String value = dataSnapshot.getValue(String.class);
                            textView.setText(value);
                            Log.d("showButton", "Value is: " + value);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                myRef.setValue("Note 2");
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
