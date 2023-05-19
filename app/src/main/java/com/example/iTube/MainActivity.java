package com.example.iTube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iTube.data.DatabaseHelper;
import com.example.iTube.model.User;

/**
 * MainActivity is an activity class that represents the main login screen of the iTube application.
 * It provides the user with the ability to login using their username and password,
 * and also provides a way to navigate to the SignupActivity screen to register a new account.
 *
 * @author Navin
 */
public class MainActivity extends AppCompatActivity {
    // Database helper object for user operations
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signUpButton);

        db = new DatabaseHelper(this);

        // Add click listener to the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Fetch user data from database
                    boolean result = db.fetchUser(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    if (result) {
                        // If user exists, start the Home activity
                        User user = db.fetchUserObject(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        intent.putExtra("user_id", user.getUser_id());
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show error message for non-existing user
                        Toast.makeText(MainActivity.this, "The user does not exist.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // Show error message for non-existing user
                    Toast.makeText(MainActivity.this, "The user does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add click listener to the signup button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start SignupActivity to register a new account
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        // Add focus change listener to the username EditText to hide the keyboard when it loses focus
        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        // Add focus change listener to the password EditText to hide the keyboard when it loses focus
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    // Method to hide the keyboard
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
