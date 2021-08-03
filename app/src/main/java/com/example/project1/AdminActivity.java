package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView tvEmail;
    private Button btnLogOut;
    private LinearLayout llCategory, llProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auth = FirebaseAuth.getInstance();
        tvEmail= findViewById(R.id.tvEmail);
        btnLogOut= findViewById(R.id.btnLogOutA);
        llCategory = findViewById(R.id.llAdmin1);
        llProduct = findViewById(R.id.llAdmin2);
        checkUser();
        //LogOut
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                checkUser();
            }
        });
        //Category
        llCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,CategoryActivityAdmin.class));
            }
        });
        llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AddBookActivity.class));
            }
        });
    }

    private void checkUser() {

        if(auth.getCurrentUser() == null){
            startActivity(new Intent(AdminActivity.this,LoginActivity.class));
            finish();
        }else{
            String email = auth.getCurrentUser().getEmail();
            tvEmail.setText(email);


        }
    }
}