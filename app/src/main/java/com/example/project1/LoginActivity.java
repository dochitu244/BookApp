package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPass;
    private Button btnLogin, btnLoginGoogle;
    private TextView tvGoRegister,tvForgotPass;
    FirebaseAuth fAuth;
    String userId;
    ProgressDialog dialog;
    String emailCheck = "[a-z9-z0-9._-]+@[a-z]+\\.+[a-z]+";
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmailL);
        etPass = findViewById(R.id.etPassL);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginGoogle = findViewById(R.id.btnGoogleLogin);
        tvGoRegister = findViewById(R.id.tvGoSignUp);
        tvForgotPass = findViewById(R.id.tvForgotPassword);
        dialog = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance();
        //Go Register
        tvGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //btn Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();


            }
        });
        //Reset Password
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
    private String email ="", password ="";
    private void check() {

        email=etEmail.getText().toString().trim();
        password=etPass.getText().toString().trim();
        if (!email.matches(emailCheck)) {
            etEmail.setError("Email không hợp lệ");
            return;
        }
        else if (password.isEmpty()) {
            etPass.setError("Mật khẩu không hợp lệ");
            return;
        }else {
            dialog.setMessage("Xin vui lòng đợi ...");
            dialog.setTitle("Đang khởi tạo");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            login();
        }

    }

    private void login() {
        dialog.setMessage("Loading ..");
        dialog.show();
        fAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        checkUser();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Loi"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        dialog.dismiss();
                        String role = ""+snapshot.child("role").getValue();
                        if(role.equals("user")){
                            startActivity(new Intent(LoginActivity.this,UserActivity.class));
                            finish();
                        }else if(role.equals("admin")){
                            startActivity(new Intent(LoginActivity.this,AdminActivity.class));
                            finish();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }


}