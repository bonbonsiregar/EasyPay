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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_signin;
    private Button signin_button;
    private EditText password_signin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin_button = (Button) findViewById(R.id.signin_button);
        email_signin = (EditText) findViewById(R.id.email_signin);
        password_signin = (EditText) findViewById(R.id.password_signin);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), CustomerActivity.class));

        }
        progressDialog = new ProgressDialog(this);

        signin_button.setOnClickListener(this);
        Button cancel = (Button) findViewById(R.id.cancel_action);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void userLogin(){
        String Email = email_signin.getText().toString().trim();
        String Password = password_signin.getText().toString().trim();

        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "Isi Emailnya kucing.... meong...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Isi Passwordnya juga dong... meong...", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Login....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == signin_button){
            userLogin();
        }
    }
}
