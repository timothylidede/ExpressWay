package com.savala.expressway.driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.savala.expressway.R;
import com.savala.expressway.model.ModelDriver;

import java.util.HashMap;
import java.util.UUID;

public class Documents extends AppCompatActivity {

    private TextView mExpress, mWay;

    private CardView mNextButton;

    private String first_name, last_name, bus_manufacturer, year, number_plate, national_id, number_of_seats,
            dl_ref_no, rating;

    private ProgressBar mProgressBar;

    private ImageView mUploadIDImage, mUploadPPImage, mUploadRegularDLImage, mUploadGoodConductImage, mUploadNTSA;

    private ImageView mBack;

    private Uri imageOneUri, imageTwoUri, imageThreeUri, imageFourUri, imageFiveUri;
    private String imageOne, imageTwo, imageThree, imageFour, imageFive;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_documents);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Intent intent = getIntent();
        first_name = intent.getStringExtra("first_name");
        last_name = intent.getStringExtra("last_name");
        bus_manufacturer = intent.getStringExtra("bus_manufacturer");
        year = intent.getStringExtra("year");
        number_plate = intent.getStringExtra("number_plate");
        national_id = intent.getStringExtra("national_id");
        dl_ref_no = intent.getStringExtra("dl_ref_no");
        number_of_seats = intent.getStringExtra("number_of_seats");
        rating = "5.0";

        mUploadIDImage = findViewById(R.id.uploadID_image);
        mUploadGoodConductImage = findViewById(R.id.uploadGoodConduct_image);
        mUploadPPImage = findViewById(R.id.uploadPP_image);
        mUploadRegularDLImage = findViewById(R.id.uploadRegularDL_image);
        mUploadNTSA = findViewById(R.id.uploadNTSA);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mNextButton = (CardView) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                next();
            }
        });

        mExpress = findViewById(R.id.express_title);
        mWay = findViewById(R.id.way_title);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");

        mExpress.setTypeface(tf);
        mWay.setTypeface(tf);

        mUploadIDImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseIDImage();
            }
        });

        mUploadPPImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePPImage();
            }
        });

        mUploadNTSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseNTSAImage();
            }
        });

        mUploadRegularDLImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseRegularDLImage();
            }
        });

        mUploadGoodConductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGoodConductImage();
            }
        });
    }

    private void chooseGoodConductImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }

    private void chooseRegularDLImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 4);
    }

    private void chooseNTSAImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 3);
    }

    private void chooseIDImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private void choosePPImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageOneUri = data.getData();
            mUploadIDImage.setImageURI(imageOneUri);
            uploadPictureOne();
        }
        if(requestCode == 2 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageTwoUri = data.getData();
            mUploadPPImage.setImageURI(imageTwoUri);
            uploadPictureTwo();
        }
        if(requestCode == 3 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageThreeUri = data.getData();
            mUploadNTSA.setImageURI(imageThreeUri);
            uploadPictureThree();
        }
        if(requestCode == 4 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageFourUri = data.getData();
            mUploadRegularDLImage.setImageURI(imageFourUri);
            uploadPictureFour();
        }
        if(requestCode == 5 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageFiveUri = data.getData();
            mUploadGoodConductImage.setImageURI(imageFiveUri);
            uploadPictureFive();
        }
    }

    private void uploadPictureFive() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference ref = storageReference.child("images/" + randomKey);

        ref.putFile(imageFiveUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),
                                "Image Uploaded", Snackbar.LENGTH_LONG).show();

                        UploadTask.TaskSnapshot firebaseUrl = task.getResult();
                        imageFive = firebaseUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Documents.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }

    private void uploadPictureFour() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference ref = storageReference.child("images/" + randomKey);

        ref.putFile(imageFourUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),
                                "Image Uploaded", Snackbar.LENGTH_LONG).show();

                        UploadTask.TaskSnapshot firebaseUrl = task.getResult();
                        imageFour = firebaseUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Documents.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }

    private void uploadPictureThree() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference ref = storageReference.child("images/" + randomKey);

        ref.putFile(imageThreeUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),
                                "Image Uploaded", Snackbar.LENGTH_LONG).show();

                        UploadTask.TaskSnapshot firebaseUrl = task.getResult();
                        imageThree = firebaseUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Documents.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }

    private void uploadPictureOne() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference ref = storageReference.child("images/" + randomKey);

        ref.putFile(imageOneUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),
                                "Image Uploaded", Snackbar.LENGTH_LONG).show();

                        UploadTask.TaskSnapshot firebaseUrl = task.getResult();
                        imageOne = firebaseUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Documents.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }

    private void uploadPictureTwo() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference ref = storageReference.child("images/" + randomKey);

        ref.putFile(imageTwoUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),
                                "Image Uploaded", Snackbar.LENGTH_LONG).show();

                        UploadTask.TaskSnapshot firebaseUrl = task.getResult();
                        imageTwo = firebaseUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Documents.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });
    }

    private void next() {
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_id", FirebaseAuth.getInstance().getCurrentUser());
        hashMap.put("role", "driver");

        FirebaseDatabase.getInstance().getReference("Role")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(hashMap);

        ModelDriver modelDriver = new ModelDriver(first_name, last_name, bus_manufacturer, year,
                number_plate, national_id, dl_ref_no, imageOne, imageTwo, imageFour,
                imageThree, imageFive, number_of_seats, rating);

        FirebaseDatabase.getInstance().getReference("Driver")
                .child(user_id)
                .setValue(modelDriver)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Documents.this, "You are now an ExpressWay Driver", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }
}