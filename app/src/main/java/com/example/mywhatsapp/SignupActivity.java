package com.example.mywhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mywhatsapp.Models.Users;
import com.example.mywhatsapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    Button mRegisterBtn;
    EditText mFullName,mEmail,mPassword,mPhone;
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
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(task -> {
                            progressDialog.dismiss();
                            if(task.isSuccessful()) {
                                Users user = new Users(email,password);

                                String id = task.getResult().getUser().getUid();
                                database.getReference("Users").child(id).setValue(user);

                                Toast.makeText(SignupActivity.this,"user created",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(SignupActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });



    }
}