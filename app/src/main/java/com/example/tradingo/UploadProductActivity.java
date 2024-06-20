package com.example.tradingo;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import android.Manifest;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.karumi.dexter.Dexter;
        import com.karumi.dexter.PermissionToken;
        import com.karumi.dexter.listener.PermissionDeniedResponse;
        import com.karumi.dexter.listener.PermissionGrantedResponse;
        import com.karumi.dexter.listener.PermissionRequest;
        import com.karumi.dexter.listener.single.PermissionListener;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class UploadProductActivity extends AppCompatActivity {

    TextView ProductTitle, Description, Price, Stock;
    ImageView LinksOfImgsArray;
    Button uploadbtn, submit;
    Uri ImageUri;
    RelativeLayout relativeLayout;

    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //this line hides action bar

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        ProductTitle = findViewById(R.id.ProductTitle);
        Description = findViewById(R.id.Description);
        Price = findViewById(R.id.Price);
        Stock = findViewById(R.id.Stock);
        relativeLayout = findViewById(R.id.relative);

        uploadbtn = findViewById(R.id.uploadbtn);
        LinksOfImgsArray = findViewById(R.id.LinksOfImgsArray);

        submit = findViewById(R.id.submit);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                uploadbtn.setVisibility(View.GONE);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //diri tiwason
                progressBar.setVisibility(View.VISIBLE);

                final StorageReference reference = firebaseStorage.getReference().child("Products")
                        .child(System.currentTimeMillis()+"");

                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ArrayList<ImageLink> imageList = new ArrayList<>();
                                imageList.add(new ImageLink(ImageUri));
                                ProjectModel model = new ProjectModel(imageList, ProductTitle.getText().toString(), Description.getText().toString(), Price.getText().toString(), Stock.getText().toString());
                                List<String> linksOfImgsArray = new ArrayList<>();
                                linksOfImgsArray.add(uri.toString());
                                model.setLinksOfImgsArray(linksOfImgsArray);

                                model.setProductTitle(ProductTitle.getText().toString());

                                model.setDescription(Description.getText().toString());

                                model.setPrice(Price.getText().toString());

                                model.setStock(Stock.getText().toString());


                                DatabaseReference productsRef = database.getReference().child("Products");
                                DatabaseReference newProductRef = productsRef.push();

                                Map<String, Object> productValues = new HashMap<>();
                                productValues.put("LinksOfImgsArray", model.getLinksOfImgsArray());
                                productValues.put("ProductTitle", model.getProductTitle());
                                productValues.put("Description", model.getDescription());
                                productValues.put("Price", model.getPrice());
                                productValues.put("Stock", model.getStock());

                                newProductRef.setValue(productValues)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(UploadProductActivity.this, "Product Upload Successfully", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(UploadProductActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        });
                            }
                        });
                    }
                });

            }
        });


    }

    private void UploadImage() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(UploadProductActivity.this, "Permission Denied", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            ImageUri = data.getData();
            LinksOfImgsArray.setImageURI(ImageUri);
        }
    }
}