package com.example.instajewelry.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instajewelry.HomeActivity;
import com.example.instajewelry.JewelryListFragment;
import com.example.instajewelry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email_et;
    EditText password_et;
    FirebaseAuth auth;
    Button loginBtn;
    Button movetosignupBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_et = findViewById(R.id.login_email_ed);
        password_et = findViewById(R.id.login_password_et);

        movetosignupBtn = findViewById(R.id.login_notmember_tv);
        loginBtn = findViewById(R.id.login_login_btn);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.login_progressBar);

        movetosignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    email_et.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    password_et.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    password_et.setError("Password must be up to 6 characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // auth the user

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "user is login successful");
                            Toast.makeText(LoginActivity.this, "User logged in" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Log.d("TAG", "user login failed");
                            Toast.makeText(LoginActivity.this, "Error - User login failed! " +task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });


    }
}