package com.example.tradingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

//di na hilabtan


    @Override
    public void onStart() {
        super.onStart();
        // if nakalog in na ang user daan so dretso na sya sa dashboard
        //gibutang sa nakos main activity 2 pang test
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //this line hides action bar
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.email);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView)  findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, signup.class));
                break;

            case R.id.signIn:
                userlogin();
                break;

                case R.id.forgotPassword:
                    startActivity(new Intent(this, ForgotPassword.class));
                    break;
        }
    }

    private void userlogin() {
        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();


        if(email.isEmpty()){
            textInputEditTextEmail.setError("Email is required!");
            textInputEditTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEditTextEmail.setError("Please enter a valid email!");
            return;
        }

        if(password.length() < 6){
            textInputEditTextPassword.setError("Minimum password is 6 characters");
            textInputEditTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                //magsend ug verification sa mga bag o na user ig mu log in
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        //redirect to dashboard ni
                        //ako sa gi butangs main activity 2
                       Intent intent = new Intent(getApplicationContext(),dashboard.class);
                       startActivity(intent);
                       finish();
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(login.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(login.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

