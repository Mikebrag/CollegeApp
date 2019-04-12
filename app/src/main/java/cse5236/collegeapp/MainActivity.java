package cse5236.collegeapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;

    private NavigationView navView;

    private View headerView;

    private TextView headerTitleTextView;

    public static GoogleSignInClient mGoogleSignInClient;

    public static boolean backButtonEnabled;

    private FragmentManager fragmentManager;

    private SharedPreferences sharedPref;

    private static final int LOCATION_REQUEST_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Entering onCreate");
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        backButtonEnabled = false;

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
                                fragmentClass = MapsFragment.class;
                                break;
                            case R.id.nav_account:
                                fragmentClass = AccountFragment.class;
                                break;
                            case R.id.nav_college:
                                fragmentClass = UniversityListFragment.class;
                                break;
                        }


                        // Swap fragments
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.user_display_name_key), mGoogleSignInAccount.getDisplayName());
            editor.putString(getString(R.string.user_given_name_key), mGoogleSignInAccount.getGivenName());
            editor.putString(getString(R.string.user_family_name_key), mGoogleSignInAccount.getFamilyName());
            editor.putString(getString(R.string.user_email_key), mGoogleSignInAccount.getEmail());
            editor.commit();
            // Set nav header title to user name
            navView = findViewById(R.id.nav_view);
            headerView = navView.getHeaderView(0);
            headerTitleTextView = headerView.findViewById(R.id.header_title_text_view);
            headerTitleTextView.setText("Hello, " + mGoogleSignInAccount.getGivenName());
        } else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }

        // Start portfolio fragment
        Fragment portfolioFragment = null;
        Class portfolioFragmentClass = PortfolioFragment.class;
        try {
            portfolioFragment = (Fragment) portfolioFragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.fragContent, portfolioFragment).commit();


        // Check for fine location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                 if (backButtonEnabled) {
                     fragmentManager.popBackStack();
                 } else {
                     drawerLayout.openDrawer(GravityCompat.START);
                 }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

