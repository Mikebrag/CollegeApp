package cse5236.collegeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class AccountFragment extends Fragment implements View.OnClickListener {

    public static Uri profilePicture = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_account, container, false);
        Button signOutButton = v.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(this);
        Button deleteAccountButton = v.findViewById(R.id.delete_account_button);
        deleteAccountButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.delete_account_button:
                revokeAccess();
                break;
        }
    }

    private void signOut() {
        ((MainActivity) this.getActivity()).mGoogleSignInClient.signOut()
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });
    }

    private void revokeAccess() {
        ((MainActivity) this.getActivity()).mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }
                });
    }
}


