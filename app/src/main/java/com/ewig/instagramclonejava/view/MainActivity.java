package com.ewig.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void clickedLogin(View view){
        String email=binding.emailText.getText().toString();
        String password=binding.passwordText.getText().toString();

        if (email.equals("")||password.equals("")){
            Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_LONG).show();
        }else {
                mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent =new Intent(MainActivity.this,FeedActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"Successfully signed In  and welcome to the Feed " +
                                "Activity\n", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    }
    public void clickedSignup(View view){
        String email=binding.emailText.getText().toString();
        String password=binding.passwordText.getText().toString();

        if (email.equals("")||password.equals("")){
            Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Successfully registered and welcome to the Feed Activity",
                            Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}