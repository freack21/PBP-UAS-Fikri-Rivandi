package id.ac.unri.ft.informatika.pbpuas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.et_email);
        passwordInput = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkValid(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if (email.isEmpty()) {
            emailInput.setError("Please fill this field");
            isValid = false;
        }
        if (password.isEmpty()) {
            passwordInput.setError("Please fill this field");
            isValid = false;
        }

        return isValid;
    }

    private void checkLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!checkValid(email, password)) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String usersJson = sharedPreferences.getString("Users", "[]");


        try {
            JSONArray usersArray = new JSONArray(usersJson);
            boolean loginSuccess = false;

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                if (user.getString("Email").equals(email) && user.getString("Password").equals(password)) {
                    loginSuccess = true;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("IsLoggedIn", true);
                    editor.putString("LoggedInUser", user.toString());
                    editor.apply();
                    break;
                }
            }

            if (loginSuccess) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                emailInput.setError("Invalid email or password");
                passwordInput.setError("Invalid email or password");
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
