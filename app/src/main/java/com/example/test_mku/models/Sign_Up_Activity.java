package com.example.test_mku.models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test_mku.R;
import com.example.test_mku.views.Activity_Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up_Activity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editPassword;
    private EditText editPassword2;
    private EditText editUserName;
    private EditText editPhone;
    private Button buttonSignUp, buttonSignIn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPassword2 = findViewById(R.id.editPassword2);
        editUserName = findViewById(R.id.editUserName);
        editPhone = findViewById(R.id.editPhone);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignIn = findViewById(R.id.buttonSignIn);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Up_Activity.this, Log_in_Activity.class);
                startActivity(intent);
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(editEmail.getText().toString(),
                        editPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String pass1 = editPassword.getText().toString();
                        String pass2 = editPassword2.getText().toString();
                        if (pass1.equals(pass2)) {
                            if (task.isSuccessful()) {
                                DatabaseReference refUser = mDatabase.getReference().child("user");
                                DatabaseReference refUserId = refUser.child(mAuth.getCurrentUser().getUid());
                                UserModel user = new UserModel(
                                        mAuth.getCurrentUser().getUid(),
                                        editEmail.getText().toString(),
                                        editUserName.getText().toString(),
                                        editPhone.getText().toString(),
                                        "",
                                        editPassword.getText().toString()

                                );
                                refUserId.setValue(user);
                                //Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Sign_Up_Activity.this, Log_in_Activity.class);
                                startActivity(intent);
                            } else {
                                Log.d("FIREBASE", task.getResult().toString());
                            }
                        }else {
                            Toast.makeText(Sign_Up_Activity.this,"Confirmation password does not match",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}