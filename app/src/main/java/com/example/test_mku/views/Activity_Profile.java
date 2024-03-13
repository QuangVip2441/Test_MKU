package com.example.test_mku.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_mku.R;
import com.example.test_mku.models.Log_in_Activity;
import com.example.test_mku.models.Sign_Up_Activity;
import com.example.test_mku.models.UserModel;
import com.example.test_mku.ultils.Constant;
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
import com.google.firestore.v1.TransactionOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Activity_Profile extends AppCompatActivity {

    private ImageView imagePhoto;
    private TextView Name, Email, Phone, Name1;
    private Button logoutButton, EditProfileButton;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user";
    private static final String Kname = "name";
    private static final String Kemai = "email";
    private static final String Kphone = "phone";

    private final String TAG = this.getClass().getName().toUpperCase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imagePhoto = findViewById(R.id.profileImg);
        logoutButton = findViewById(R.id.logoutButton);
        EditProfileButton = findViewById(R.id.EditProfileButton);
        Name = findViewById(R.id.txt_Name);
        Email = findViewById(R.id.txt_Email);
        Phone = findViewById(R.id.txt_Phone);
        Name1 = findViewById(R.id.txtName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        userID = user.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference("images").child(userID);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String name = sharedPreferences.getString(Kname,null);
        String name1 = sharedPreferences.getString(Kname,null);
        String phone = sharedPreferences.getString(Kphone,null);
        String email = sharedPreferences.getString(Kemai,null);

        if (name != null || phone != null || email != null){
            Email.setText(email);
            Name.setText(name);
            Phone.setText(phone);
            Name1.setText(name);

        }
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .into(imagePhoto);
            }
        });

//        Intent intent = getIntent();
//        String userID = intent.getStringExtra("userID");

//        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            String name, phone, email;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               UserModel userModel = snapshot.getValue(UserModel.class);
//
//               if (userModel != null){
//                   name = userModel.getUsername();
//                   email = userModel.getEmail();
//                   phone = userModel.getPhone();
//
//                   Email.setText(email);
//                   Name.setText(name);
//                   Phone.setText(phone);
//                   Name1.setText(name);


//               }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG,"Failed to read value", error.toException());
//            }
//        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Activity_Profile.this, Log_in_Activity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Profile.this, Activity_Edit_Profile.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Profile.this, Upload_Image_Profile_Activity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}