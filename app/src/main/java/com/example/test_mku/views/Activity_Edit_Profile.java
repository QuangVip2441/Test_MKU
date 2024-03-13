package com.example.test_mku.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_mku.R;
import com.example.test_mku.models.Log_in_Activity;
import com.example.test_mku.models.Sign_Up_Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Activity_Edit_Profile extends AppCompatActivity {

    ShapeableImageView profileImg;
    EditText edtname, email, edtphone, password;
    String nameUser, emailUser, phoneUser, passwordUser, Id;
    TextView titleName;
    Button editButton;
    ProgressBar progressBar;

    DatabaseReference databaseReference;

    private static final String USERS = "user";


    private final String TAG = this.getClass().getName().toUpperCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        titleName = findViewById(R.id.titleName);
        edtname = findViewById(R.id.name);
        edtphone = findViewById(R.id.phone);
        profileImg = findViewById(R.id.profileImg);
        editButton = findViewById(R.id.editButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("user");


        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference("images").child(userID);


        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference useref = rootref.child(USERS);
        Log.v("USERID", useref.getKey());

        String imageUrl = userID;


        useref.addValueEventListener(new ValueEventListener() {
            String name, phone;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keyId : snapshot.getChildren()){
                    if (keyId.child("id").getValue().equals(userID)){
                        name = keyId.child("username").getValue(String.class);
                        phone = keyId.child("phone").getValue(String.class);
                        break;
                    }
                }
                titleName.setText(name);
                edtname.setText(name);
                edtphone.setText(phone);

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get()
                                .load(uri)
                                .into(profileImg);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String nodeName = "user";

        String userId = userID;
//        String ten = edtname.getText().toString();
//        String sdt = edtphone.getText().toString();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một đối tượng Map để cập nhật dữ liệu mới
                Map<String, Object> updatedData = new HashMap<>();
                updatedData.put("username", edtname.getText().toString());
                updatedData.put("phone",edtphone.getText().toString());

                rootref.child(nodeName).child(userId).updateChildren(updatedData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(Activity_Edit_Profile.this, Activity_Profile.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Activity_Edit_Profile.this, "Update Fails", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

//        getId();
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isNameChanged() & isPhoneChanged()){
//                    Intent intent = new Intent(Activity_Edit_Profile.this, Activity_Profile.class);
//                    startActivity(intent);
//                }else
//                    Toast.makeText(Activity_Edit_Profile.this,"No Changes Found", Toast.LENGTH_LONG);
//            }
//        });
    }

//    public boolean isNameChanged(){
//        if (!nameUser.equals(edtname.getText().toString())){
//            databaseReference.child(Id).child("username").setValue(edtname.getText().toString());
//            nameUser = edtname.getText().toString();
//            return true;
//        }else
//            return false;
//    }

//    public boolean isPhoneChanged(){
//        if (!phoneUser.equals(edtphone.getText().toString())){
//            databaseReference.child(Id).child("phone").setValue(edtphone.getText().toString());
//            phoneUser = edtphone.getText().toString();
//            return true;
//        }else
//            return false;
//    }
//
//    public void getId(){
//        Intent intent = getIntent();
//        Id = intent.getStringExtra("userID");
//    }
}