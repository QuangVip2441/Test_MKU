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

import com.example.test_mku.MainActivity;
import com.example.test_mku.R;
import com.example.test_mku.views.Activity_Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Log_in_Activity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editPassword;
    private Button buttonSignIn, buttonSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        mAuth = FirebaseAuth.getInstance();
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_in_Activity.this, Sign_Up_Activity.class);
                startActivity(intent);
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePassword() | !validateEmail() ){
                }else {
                    mAuth.signInWithEmailAndPassword(editEmail.getText().toString(),
                            editPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String userID = mAuth.getCurrentUser().getUid();
                                Intent intent = new Intent(Log_in_Activity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Log_in_Activity.this, "Password or Gmail false ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean validateEmail(){
        String val = editEmail.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()){
            editEmail.setError("Email cannot be empty");
            return false;
        }else {
            editEmail.setError(null);
            //editEmail.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validatePassword(){
        String val = editPassword.getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";
        if (val.isEmpty()){
            editPassword.setError("Password cannot be empty");
            return false;
        }else {
            editPassword.setError(null);
            //editEmail.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        if (!validatePassword() | !validateEmail() ){
            return;
        }else
            isUser();
    }

    private void isUser() {
       final String userEnteredEmail = editEmail.getText().toString().trim();
       final String userEnteredPassWord = editPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");

        Query checkUser = reference.orderByChild("email").equalTo(userEnteredEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    editEmail.setError(null);

                    String passwordFromDB = snapshot.child(userEnteredEmail).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassWord)){

                        editEmail.setError(null);

                        String emailFromDB = snapshot.child(userEnteredEmail).child("email").getValue(String.class);
                        String phoneFromDB = snapshot.child(userEnteredEmail).child("phone").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredEmail).child("username").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Activity_Profile.class);

                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneFromDB);
                        intent.putExtra("username", usernameFromDB);

                        startActivity(intent);
                    }else {
                        editPassword.setError("Wrong Password");
                        editPassword.requestFocus();
                    }
                }else {
                    editEmail.setError("No such User exist");
                    editEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkUser(){
        String userEmail = editEmail.getText().toString().trim();
        String userPassword = editPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    editEmail.setError(null);
                    String passdFromDB = snapshot.child(userEmail).child("password").getValue(String.class);

                    if (!Objects.equals(passdFromDB, userPassword)){
                        editEmail.setError(null);
                        Intent intent = new Intent(Log_in_Activity.this, Activity_Profile.class);
                                startActivity(intent);
                    }else {
                        editPassword.setError("Invalid Credentials");
                        editPassword.requestFocus();
                    }
                }else {
                    editEmail.setError("User does not exist");
                    editEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}