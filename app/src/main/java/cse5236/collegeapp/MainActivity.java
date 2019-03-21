package cse5236.collegeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;

    public static GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Entering onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment = null;
                        Class fragmentClass = null;
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_portfolio:
                                fragmentClass = PortfolioFragment.class;
                                break;
                            case R.id.nav_maps:
                                Toast.makeText(MainActivity.this, R.string.title_activity_maps, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_account:
                                fragmentClass = AccountFragment.class;
                                break;
                        }

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragContent, fragment).commit();

                        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        //drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (mGoogleSignInAccount != null) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.user_display_name_key), mGoogleSignInAccount.getDisplayName());
            editor.putString(getString(R.string.user_given_name_key), mGoogleSignInAccount.getGivenName());
            editor.putString(getString(R.string.user_family_name_key), mGoogleSignInAccount.getFamilyName());
            editor.putString(getString(R.string.user_email_key), mGoogleSignInAccount.getEmail());
            editor.commit();
        } else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Entering onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Entering onResume");
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Entering onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Entering onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Entering onDestroy");
        super.onDestroy();
    }

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }

}

