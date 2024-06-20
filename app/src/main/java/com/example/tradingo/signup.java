package com.example.tradingo;


import static com.example.tradingo.R.layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener {


    private TextView banner, registerUser;
    private TextInputEditText textInputEditTextFname, textInputEditTextLname, textInputEditTextEmail, textInputEditTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //this line hides action bar
        setContentView(layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);
        
        textInputEditTextFname = (TextInputEditText) findViewById(R.id.Fname);
        textInputEditTextLname = (TextInputEditText) findViewById(R.id.Lname);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.email);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.password);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, login.class));
                break;
            case  R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String First_Name = textInputEditTextFname.getText().toString().trim();
        String Last_Name = textInputEditTextLname.getText().toString().trim();
        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        if (First_Name.isEmpty()){
            textInputEditTextFname.setError("First Name is required!");
            textInputEditTextFname.requestFocus();
            return;
        }
        if (Last_Name.isEmpty()){
            textInputEditTextLname.setError("Last Name is required!");
            textInputEditTextLname.requestFocus();
            return;
        }
        if (email.isEmpty()){
            textInputEditTextEmail.setError("Email is required!");
            textInputEditTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEditTextEmail.setError("Please provide valid email!");
            return;
        }
        if(password.isEmpty()){
            textInputEditTextPassword.setError("Password is required!");
            textInputEditTextPassword.requestFocus();
            return;
        }
        if (password.length()< 6){
            textInputEditTextPassword.setError("Minimum length should be 6 characters!");
            textInputEditTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(First_Name,Last_Name,email);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(signup.this,"User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(signup.this, login.class));
                                                //redirect to login layout
                                            }
                                            else {
                                                Toast.makeText(signup.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(signup.this, "Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}







