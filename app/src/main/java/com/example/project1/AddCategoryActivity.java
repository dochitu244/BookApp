package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddCategoryActivity extends AppCompatActivity {

    private Button btnAdd;
    private TextInputEditText etName;
    private FirebaseAuth auth;
    private ProgressDialog dialog;
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        btnAdd=findViewById(R.id.btnAddCat);
        etName=findViewById(R.id.etAddCat);
        ivBack = findViewById(R.id.ic_backCat);
        auth=FirebaseAuth.getInstance();

        dialog= new ProgressDialog(this);
        dialog.setTitle("Wait..");
        dialog.setCanceledOnTouchOutside(false);

        //Click
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        

    }
    String category ="";
    private void addCategory() {
        dialog.setMessage("Adding");
        dialog.show();
        category=etName.getText().toString();
        long time = System.currentTimeMillis();
        HashMap<String, Object> cat = new HashMap<>();
        cat.put("id",""+time);
        cat.put("category",""+category);
        cat.put("time",time);
        cat.put("uid", auth.getUid());
        //add db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
        ref.child(""+time)
                .setValue(cat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(AddCategoryActivity.this,"Them moi thanh cong",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        dialog.dismiss();
                        Toast.makeText(AddCategoryActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }
}