package cse5236.collegeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent;
        GoogleSignInAccount mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (mGoogleSignInAccount != null) {
            // Add user info to SharedPreferences
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.user_display_name_key), mGoogleSignInAccount.getDisplayName());
            editor.putString(getString(R.string.user_given_name_key), mGoogleSignInAccount.getGivenName());
            editor.putString(getString(R.string.user_family_name_key), mGoogleSignInAccount.getFamilyName());
            editor.putString(getString(R.string.user_id_key), mGoogleSignInAccount.getId());
            editor.putString(getString(R.string.user_email_key), mGoogleSignInAccount.getEmail());
            editor.commit();
            Log.d(TAG, sharedPref.getString(getString(R.string.user_id_key), ""));
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            intent = new Intent(SplashActivity.this,LoginActivity.class);
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
