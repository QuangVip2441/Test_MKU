package com.example.test_mku;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test_mku.models.ChoiceModel;
import com.example.test_mku.models.QuestionModel;
import com.example.test_mku.models.UserModel;
import com.example.test_mku.ultils.FragmentUtils;
import com.example.test_mku.views.Activity_Profile;
import com.example.test_mku.views.AddModuleFragment;
import com.example.test_mku.views.AddQuestionFragment;
import com.example.test_mku.views.ExportPDFFragment;
import com.example.test_mku.views.HomeFragment;
import com.example.test_mku.views.QuestionFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;

    HomeFragment homeFragment = new HomeFragment();
    AddQuestionFragment addQuestionFragment = new AddQuestionFragment();
    ExportPDFFragment exportPDFFragment = new ExportPDFFragment();

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user";
    private static final String Kname = "name";
    private static final String Kemai = "email";
    private static final String Kphone = "phone";


    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;

    private final String TAG = this.getClass().getName().toUpperCase();

    String name, phone, email;

    private ArrayList<QuestionModel> mQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                if (userModel != null){
                    name = userModel.getUsername();
                    email = userModel.getEmail();
                    phone = userModel.getPhone();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,"Failed to read value", error.toException());
            }
        });

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        bottom_nav = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.Home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.Results) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, exportPDFFragment).commit();
                    return true;
                }
                else if (itemId == R.id.AddQuestion) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, addQuestionFragment).commit();
                    return true;
                }else if (itemId == R.id.Profile) {
                    Intent profileIntent = new Intent(MainActivity.this, Activity_Profile.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Kname,name);
                    editor.putString(Kemai,email);
                    editor.putString(Kphone,phone);
                    editor.apply();
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            }
        });

        mQuestions = new ArrayList<>();
        mQuestions = getMCQ();

        replaceFragment(new HomeFragment());
    }
    private void replaceFragment(Fragment fragment) {
        FragmentUtils.replaceFragment(
                getSupportFragmentManager(),
                fragment,
                false);
    }
    private ArrayList<QuestionModel> getMCQ() {
        ArrayList<QuestionModel> questions = new ArrayList<>();

        questions.clear();

        ArrayList<ChoiceModel> choices = new ArrayList<>();
        choices.add(new ChoiceModel("1", "Word"));
        choices.add(new ChoiceModel("2", "Excel"));
        choices.add(new ChoiceModel("3", "Power Point aaaaaaa aaaa aaaaa aaaaaaa aaaa aaaaa aaaaaa aaaa aaaaa aaaaaa aaaa aaaaa aaaaaaa"));
        choices.add(new ChoiceModel("4", "Calculator"));
        questions.add( new QuestionModel(
                "1",
                "Trong Windows, phan mem may tinh co ten gì?",
                choices,
                "4"
        ));

        choices = new ArrayList<>();
        choices.add(new ChoiceModel("1", "Word"));
        choices.add(new ChoiceModel("2", "Excel"));
        choices.add(new ChoiceModel("3", "Power Point"));
        choices.add(new ChoiceModel("4", "Calculator"));
        questions.add( new QuestionModel(
                "2",
                "Trong Windows, phan mem go van ban?",
                choices,
                "1"
        ));

        choices = new ArrayList<>();
        choices.add(new ChoiceModel("1", "Mathlab"));
        choices.add(new ChoiceModel("2", "Excel"));
        choices.add(new ChoiceModel("3", "Google"));
        choices.add(new ChoiceModel("4", "Calculator"));
        questions.add( new QuestionModel(
                "3",
                "Trong Windows, phan mem xu ly bang tinh?",
                choices,
                "3"
        ));

        return questions;
    }
}