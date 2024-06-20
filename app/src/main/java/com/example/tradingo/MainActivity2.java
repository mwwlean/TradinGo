package com.example.tradingo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    FloatingActionButton upload, profile;
    RecyclerView recyclerView;
    ArrayList<ProjectModel> recycleList;

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //this line hides action bar
        setContentView(R.layout.activity_main2);

        upload = findViewById(R.id.upload);
        recyclerView = findViewById(R.id.recyclerView);
        profile = findViewById(R.id.profile);
        recycleList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        ProjectAdapter recyclerAdapter = new ProjectAdapter(recycleList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(recyclerAdapter);

        firebaseDatabase.getReference().child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, Object> productDetails = (HashMap<String, Object>) ds.getValue();
                    if (productDetails != null) {
                        String productTitle = (String) productDetails.get("ProductTitle");
                        String description = (String) productDetails.get("Description");
                        String stock = (String) productDetails.get("Stock");
                        String price = (String) productDetails.get("Price");
                        Object linksOfImgsArrayObj = productDetails.get("LinksOfImgsArray");
                        ArrayList<ImageLink> imageLinks = new ArrayList<>();
                        if (linksOfImgsArrayObj instanceof ArrayList) {
                            ArrayList<String> linksOfImgsArray = (ArrayList<String>) linksOfImgsArrayObj;
                            for (String link : linksOfImgsArray) {
                                imageLinks.add(new ImageLink(Uri.parse(link)));
                            }
                        }
                        if (productTitle != null && stock != null && price != null) {
                            recycleList.add(new ProjectModel(imageLinks, productTitle, description, price, stock));
                            Log.d("TAG", "Product Title: " + productTitle);
                            Log.d("TAG", "Description: " + description);
                            Log.d("TAG", "Stock: " + stock);
                            Log.d("TAG", "Price: " + price);
                            Log.d("TAG", "Image Links: " + linksOfImgsArrayObj.toString());
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "Failed to read value.", error.toException());
            }

        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, UploadProductActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
