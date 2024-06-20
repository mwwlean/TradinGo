package com.example.tradingo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class dashboard extends AppCompatActivity {
    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //this line hides action bar
        setContentView(R.layout.activity_dashboard);

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        button = (Button) findViewById(R.id.explore);


        slideModels.add(new SlideModel(R.drawable.mouse, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.laptop, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.iphonezz, ScaleTypes.CENTER_INSIDE));
        slideModels.add(new SlideModel(R.drawable.iphonecool, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, MainActivity2.class));
                finish();
            }
        });
    }
}







