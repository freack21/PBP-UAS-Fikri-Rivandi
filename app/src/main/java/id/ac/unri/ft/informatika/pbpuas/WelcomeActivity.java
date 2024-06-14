package id.ac.unri.ft.informatika.pbpuas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class WelcomeActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView fullnameText = findViewById(R.id.fullname_text);
        TextView emailText = findViewById(R.id.email_text);
        TextView webText = findViewById(R.id.web_text);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String loggedInUserJson = sharedPreferences.getString("LoggedInUser", "{}");

        try {
            JSONObject loggedInUser = new JSONObject(loggedInUserJson);
            fullnameText.setText(loggedInUser.getString("FullName"));
            emailText.setText(loggedInUser.getString("Email"));
            webText.setText(loggedInUser.getString("Web"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
