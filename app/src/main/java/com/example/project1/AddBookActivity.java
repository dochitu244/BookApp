package com.example.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.Model.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AddBookActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private ShapeableImageView imgProduct;
    private EditText etTitle, etPrice, etAuthor, etContent, etQuantity;
    private TextView tvCate;
    private Button btnIns;
    private String[] cameraPM;
    private String[] storagePM;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private Uri image_uri;
    private FirebaseAuth auth;
    private ArrayList<Category> cateList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initView();
        initPermission();
        loadCategories();

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });
        tvCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cateDialog();
            }
        });
        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    //input data
    private String bookTitle, bookContent, category, bookQuantity, bookPrice, bookAuthor;

    private void addData() {
        bookTitle = etTitle.getText().toString().trim();
        bookContent = etContent.getText().toString().trim();
        bookPrice = etPrice.getText().toString().trim();
        bookQuantity = etQuantity.getText().toString().trim();
        category = tvCate.getText().toString().trim();
        bookAuthor = etAuthor.getText().toString().trim();
        //validate
        if (bookTitle.isEmpty()) {
            Toast.makeText(this, "Title......", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bookPrice.isEmpty()) {
            Toast.makeText(this, "Price.....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bookQuantity.isEmpty()) {
            Toast.makeText(this, "Quantity......", Toast.LENGTH_SHORT).show();
            return;
        }
        if (category.isEmpty()) {
            Toast.makeText(this, "Category......", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bookAuthor.isEmpty()) {
            Toast.makeText(this, "Author......", Toast.LENGTH_SHORT).show();
            return;
        }
        addBook();
    }

    private void addBook() {
        progressDialog.setMessage("Adding Book...................");
        progressDialog.show();
        final String time = "" + System.currentTimeMillis();
        if (image_uri == null) {
            HashMap<String, Object> bookMap = new HashMap<>();
            bookMap.put("uid", "" + auth.getUid());
            bookMap.put("bookId", "" + time);
            bookMap.put("bookTitle", "" + bookTitle);
            bookMap.put("bookAuthor", "" + bookAuthor);
            bookMap.put("bookPrice", "" + bookPrice);
            bookMap.put("bookContent", "" + bookContent);
            bookMap.put("bookQuantity", "" + bookQuantity);
            bookMap.put("category", "" + category);
            bookMap.put("bookImage", "");
            bookMap.put("time", "" + time);
            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("books");
            ref1.child("" + time)
                    .setValue(bookMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            Toast.makeText(AddBookActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        } else {
            String filePathAddName = "book_images/" + "" + time;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAddName);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadImageUri = uriTask.getResult();
                            if (uriTask.isSuccessful()) {
                                HashMap<String, Object> bookMap = new HashMap<>();
                                bookMap.put("uid", "" + auth.getUid());
                                bookMap.put("bookId", "" + time);
                                bookMap.put("bookTitle", "" + bookTitle);
                                bookMap.put("bookAuthor", "" + bookAuthor);
                                bookMap.put("bookPrice", "" + bookPrice);
                                bookMap.put("bookContent", "" + bookContent);
                                bookMap.put("bookQuantity", "" + bookQuantity);
                                bookMap.put("category", "" + category);
                                bookMap.put("bookImage", "" + downloadImageUri);
                                bookMap.put("time", "" + time);
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("books");
                                ref1.child("" + time)
                                        .setValue(bookMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(AddBookActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }
    }

    //fill category
    private void loadCategories() {
        cateList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cateList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Category category = ds.getValue(Category.class);
                    cateList.add(category);
                    Log.e("Name Cat", category.getCategory());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Dialog category
    private void cateDialog() {
        String[] categoriesArr = new String[cateList.size()];
        for (int i = 0; i < cateList.size(); i++) {
            categoriesArr[i] = cateList.get(i).getCategory();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chon Category")
                .setItems(categoriesArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String category = categoriesArr[which];
                        tvCate.setText(category);
                    }
                }).show();
    }

    //Dialog image
    private void showImageDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chon")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (checkCameraPer()) {
                                imageCamera();
                            } else {
                                requestCameraPer();
                            }
                        } else {
                            if (checkStoragePer()) {
                                imageGallery();
                            } else {
                                requestStoragePer();
                            }

                        }
                    }
                }).show();
    }

    //Pick img tu gallery
    private void imageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    //Pick image tu camera
    private void imageCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image_Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    //check PM
    private boolean checkStoragePer() {
        boolean kq = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return kq;
    }

    private void requestStoragePer() {
        ActivityCompat.requestPermissions(this, storagePM, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPer() {
        boolean kq = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean kq1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return kq && kq1;
    }

    private void requestCameraPer() {
        ActivityCompat.requestPermissions(this, cameraPM, CAMERA_REQUEST_CODE);
    }

    //xl PM kq
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccept && storageAccept) {
                        imageCamera();
                    } else {
                        Toast.makeText(this, "Per1 are required", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccept) {
                        imageGallery();
                    } else {
                        Toast.makeText(this, "Per2 are required", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //xl img kq
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_uri = data.getData();
                imgProduct.setImageURI(image_uri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                imgProduct.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //PM Arr
    private void initPermission() {
        cameraPM = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePM = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    //Anh Xa
    private void initView() {
        ibBack = findViewById(R.id.ibBack);
        imgProduct = findViewById(R.id.imgProduct);
        ibBack = findViewById(R.id.ibBack);
        etTitle = findViewById(R.id.etProductName);
        etPrice = findViewById(R.id.etPrice);
        etAuthor = findViewById(R.id.etPrice);
        etContent = findViewById(R.id.etContent);
        etQuantity = findViewById(R.id.etQuantity);
        tvCate = findViewById(R.id.tvCategory);
        btnIns = findViewById(R.id.btnInsProduct);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Wait.......");
        progressDialog.setCanceledOnTouchOutside(false);
    }
}