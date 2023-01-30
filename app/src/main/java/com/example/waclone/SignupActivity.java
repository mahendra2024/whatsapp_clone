package com.example.waclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.waclone.databinding.ActivitySignupBinding;
import com.example.waclone.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().hide();


        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.textPassword.getText().toString().isEmpty() && !binding.texUserName.getText().toString().isEmpty() && !binding.txtEmail.getText().toString().isEmpty()){
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString() , binding.textPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    Users users = new Users(binding.texUserName.getText().toString() , binding.txtEmail.getText().toString() , binding.textPassword.getText().toString());
                                    String id = task.getResult().getUser()  .getUid();
                                    database.getReference().child("Users").child(id).setValue(users);
                                    if(task.isSuccessful()){

                                        Toast.makeText(SignupActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this , MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignupActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else {
                    Toast.makeText(SignupActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });



        binding.txtAllReadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}