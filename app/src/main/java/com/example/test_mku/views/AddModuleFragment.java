package com.example.test_mku.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test_mku.R;
import com.example.test_mku.ultils.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddModuleFragment extends Fragment {

    private FirebaseFirestore mFirestore;
    private CollectionReference mRefCollectionModule;

    public AddModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_module, container, false);

        mFirestore = FirebaseFirestore.getInstance();

        addModule("IU01", "Microsoft Word", 50);
        addModule("IU02", "Microsoft Excel", 50);
        addModule("IU03", "Microsoft Power Point", 50);
        return view;
    }

    private void addModule(String name, String introduction, int numberQuestions) {
        Map<String, Object> module = new HashMap<>();
        module.put(Constant.Database.Module.NAME, name);
        module.put(Constant.Database.Module.INTRODUCTION, introduction);
        module.put(Constant.Database.Module.NUMBER_QUESTIONS, numberQuestions);

        mRefCollectionModule = mFirestore.collection(Constant.Database.Module.COLLECTION_MODULE);
        mRefCollectionModule.add(module).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String id = documentReference.getId();
                Map<String, Object> update = new HashMap<>();
                update.put(Constant.Database.Module.ID, id);
                mRefCollectionModule.document(id).update(update);
            }
        });
    }
}