package com.easypay.easypay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email_signup_edit;
    private Button signup_button;
    private EditText password_signup_edit;
    //private EditText fullname_signup_edit;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        //fullname_signup_edit = (EditText) findViewById(R.id.fullname_signup_edit);
        signup_button = (Button) findViewById(R.id.signup_button);
        email_signup_edit = (EditText) findViewById(R.id.email_signup_edit);
        password_signup_edit = (EditText) findViewById(R.id.password_signup_edit);
        Button cancel = (Button) findViewById(R.id.cancel_action);


        signup_button.setOnClickListener(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void registerUser(){
        String email = email_signup_edit.getText().toString().trim();
        String password = password_signup_edit.getText().toString().trim();
        //String nama = fullname_signup_edit.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            // email empty
            Toast.makeText(this, "Emailnya di isi kucing.... meong....", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Passwordnya jg kucing.... meong....", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Register is complete meong...", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }else{
                                Toast.makeText(RegisterActivity.this, "Sorry password minimal 6 angka atau email salah", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view == signup_button){
            registerUser();
        }

    }
}
