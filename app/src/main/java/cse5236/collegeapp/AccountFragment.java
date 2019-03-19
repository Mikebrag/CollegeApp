package cse5236.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



public class AccountFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AccountFragment";

    public static Uri profilePicture = null;

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

        Toast.makeText(getActivity(), R.string.title_activity_account, Toast.LENGTH_SHORT).show();

        View v = inflater.inflate(R.layout.fragment_account, container, false);
        Button signOutButton = v.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(this);
        Button deleteAccountButton = v.findViewById(R.id.delete_account_button);
        deleteAccountButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "Entering onStart");
        super.onStart();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String defaultGivenName = getResources().getString(R.string.default_user_given_name_key);
        String givenName = sharedPref.getString(getString(R.string.user_given_name_key), defaultGivenName);
        String defaultFamilyName = getResources().getString(R.string.default_user_family_name_key);
        String familyName = sharedPref.getString(getString(R.string.user_family_name_key), defaultFamilyName);
        TextView displayNameView = getView().findViewById(R.id.user_full_name);
        displayNameView.setText(givenName + " " + familyName);

        ImageView profilePictureView = getView().findViewById(R.id.user_profile_picture);
        if (profilePicture != null) {
            profilePictureView.setImageURI(profilePicture);
        } else {
            profilePictureView.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }
        String defaultEmail = getResources().getString(R.string.default_user_email_key);
        String email = sharedPref.getString(getString(R.string.user_email_key), defaultEmail);
        TextView emailView = getView().findViewById(R.id.user_email);
        emailView.setText(email);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.delete_account_button:
                revokeAccess();
                break;
        }
    }

    private void signOut() {
        ((MainActivity)this.getActivity()).mGoogleSignInClient.signOut()
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });
    }

    private void revokeAccess() {
        ((MainActivity)this.getActivity()).mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });
    }
}


