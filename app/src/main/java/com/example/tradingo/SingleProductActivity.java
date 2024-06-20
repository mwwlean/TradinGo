package com.example.tradingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class SingleProductActivity extends AppCompatActivity {

    TextView singleTitle, singleDescription, singlePrice, singleStock;
    ImageView singleImageArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //this line hides action bar
        setContentView(R.layout.activity_single_product);

        singleTitle = findViewById(R.id.singleTitle);
        singleDescription = findViewById(R.id.singleDescription);
        singlePrice = findViewById(R.id.singlePrice);
        singleStock = findViewById(R.id.singleStock);

        singleImageArray = findViewById(R.id.singleImageArray);
        List<String> imageLinks = Arrays.asList(getIntent().getStringArrayExtra("singleImageArray"));
        Picasso.get().load(imageLinks.get(0))
                .placeholder(R.drawable.sale)
                .into(singleImageArray);

        singleTitle.setText(getIntent().getStringExtra("singleTitle"));
        singleDescription.setText(getIntent().getStringExtra("singleDescription"));
        singlePrice.setText(getIntent().getStringExtra("singlePrice"));
        singleStock.setText(getIntent().getStringExtra("singleStock"));

    }
}