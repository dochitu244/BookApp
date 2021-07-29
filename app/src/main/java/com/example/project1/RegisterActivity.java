package com.example.project1;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    EditText etFullName, etEmail, etPass, etConfirmPass;
    Button btnRegister, btnRegisterGoogle;
    TextView tvGoBack;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    String emailCheck = "[a-z9-z0-9._-]+@[a-z]+\\.+[a-z]+";
    String userId;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegisterGoogle = findViewById(R.id.btnGoogleRegister);
        tvGoBack = findViewById(R.id.tvBackLogin);
        dialog = new ProgressDialog(this);

        fAuth = FirebaseAuth.getInstance();


        //Go Back Login
        tvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        //btn Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerAuth();
            }
        });
    }

    private String name = "", email = "", password = "", confirmPassword = "";

    //check form
    private void PerAuth() {
        name = etFullName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPass.getText().toString().trim();
        confirmPassword = etConfirmPass.getText().toString().trim();
        if (!email.matches(emailCheck)) {
            etEmail.setError("Email không hợp lệ");
            return;
        }
        else if (password.isEmpty()) {
            etPass.setError("Mật khẩu không hợp lệ");
            return;
        }
        else if (!password.equals(confirmPassword)) {
            etConfirmPass.setError("Mật khẩu không khớp");
            return;
        }else {
            dialog.setMessage("Xin vui lòng đợi ...");
            dialog.setTitle("Đang khởi tạo");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            createUser();
        }
    }

    private void createUser() {

        //Create user
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        inFo();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        dialog.dismiss();

                    }
                });

    }

    private void inFo() {
        dialog.setMessage("Đang lưu user");
        long time = System.currentTimeMillis();
        userId = fAuth.getUid();
        //setup
        Map<String, Object> user = new HashMap<>();
        user.put("uid", userId);
        user.put("fullName", name);
        user.put("Email", email);
        user.put("avatar", "");//later
        user.put("role", "user");
        user.put("time", time);
        //set to db

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(userId)
                .setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"Thành công",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"Lỗi"+e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
    }


}