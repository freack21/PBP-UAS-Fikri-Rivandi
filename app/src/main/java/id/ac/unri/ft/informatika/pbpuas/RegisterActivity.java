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

public class RegisterActivity extends AppCompatActivity {

    EditText fullnameInput, emailInput, passwordInput, phoneInput, webInput;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameInput = findViewById(R.id.fullname_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        phoneInput = findViewById(R.id.phone_input);
        webInput = findViewById(R.id.web_input);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private boolean checkValid(String fullname, String email, String password, String phone, String web) {
        boolean isValid = true;

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || web.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if (fullname.isEmpty()) {
            fullnameInput.setError("Please fill this field");
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
        if (phone.isEmpty()) {
            phoneInput.setError("Please fill this field");
            isValid = false;
        }
        if (web.isEmpty()) {
            webInput.setError("Please fill this field");
            isValid = false;
        }

        return isValid;
    }

    private void saveUserData() {
        String fullname = fullnameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String web = webInput.getText().toString().trim();

        if (!checkValid(fullname, email, password, phone, web)) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String usersJson = sharedPreferences.getString("Users", "[]");
        try {
            JSONArray usersArray = new JSONArray(usersJson);
            JSONObject newUser = new JSONObject();
            newUser.put("FullName", fullname);
            newUser.put("Email", email);
            newUser.put("Password", password);
            newUser.put("Phone", phone);
            newUser.put("Web", web);
            usersArray.put(newUser);
            editor.putString("Users", usersArray.toString());
            editor.apply();
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
