package com.example.mywhatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywhatsapp.Models.Users;
import com.example.mywhatsapp.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    Button mRegisterBtn;
    EditText mFullName,mEmail,mPassword,mConfirmPassword;
    ProgressDialog progressDialog;
// ...
// Initialize Firebase Auth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRegisterBtn= findViewById(R.id.signup);
        mEmail      = findViewById(R.id.username);
        mPassword   = findViewById(R.id.password);

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("creating your account");
        progressDialog.setMessage("We're creating your account");

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = binding.username.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                String confirm_password = binding.confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be greater than 6 Characters");
                    return;
                }

                if (TextUtils.isEmpty(confirm_password)) {
                    mPassword.setError("Confirm Password");
                    return;
                }

                if(password.equals(confirm_password))
                {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Users user = new Users(email, password);

                                    String id = task.getResult().getUser().getUid();
                                    database.getReference("Users").child(id).setValue(user);

                                    Toast.makeText(SignupActivity.this, "user created", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });
                }
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });



    }
}