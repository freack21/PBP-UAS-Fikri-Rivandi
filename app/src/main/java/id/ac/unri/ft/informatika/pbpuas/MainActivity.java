package id.ac.unri.ft.informatika.pbpuas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle mToggle;
    private TextView userNameTextView, userEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        userEmailTextView = navigationView.getHeaderView(0).findViewById(R.id.user_email);

        setUserInfo();

        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        if (item.getItemId() == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (item.getItemId() == R.id.nav_gallery) {
            selectedFragment = new GalleryFragment();
        } else if (item.getItemId() == R.id.nav_news) {
            selectedFragment = new NewsFragment();
        } else if (item.getItemId() == R.id.nav_contact) {
            selectedFragment = new ContactFragment();
        } else if (item.getItemId() == R.id.nav_logout) {
            handleLogout();
        } else if (item.getItemId() == R.id.nav_delete) {
            clearSavedData();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, selectedFragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleLogout() {
        clearLoginSession();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void clearLoginSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("IsLoggedIn");
        editor.remove("LoggedInUser");
        editor.apply();
    }

    private void clearSavedData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setUserInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String loggedInUserJson = sharedPreferences.getString("LoggedInUser", "{}");

        try {
            JSONObject loggedInUser = new JSONObject(loggedInUserJson);

            userNameTextView.setText(loggedInUser.getString("FullName"));
            userEmailTextView.setText(loggedInUser.getString("Email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
