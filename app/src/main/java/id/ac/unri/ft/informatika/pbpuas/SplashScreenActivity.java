package id.ac.unri.ft.informatika.pbpuas;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("IsLoggedIn", false);

                Intent i;
                if (isLoggedIn) {
                    i = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(i);
                } else {
                    i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
