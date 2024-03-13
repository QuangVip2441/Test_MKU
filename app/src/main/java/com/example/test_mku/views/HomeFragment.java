package com.example.test_mku.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test_mku.R;
import com.example.test_mku.models.ModuleModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    private MaterialButton buttonQuizModule;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList<ModuleModel> models = new ArrayList<>();
        models.add(new ModuleModel("dp7OLmrhP43fOxYfD7m0","IU01","Hiểu biết về CNTT cơ bản",50));
        models.add(new ModuleModel("KJgiMny2wQpnZ8UCwiyc","IU02","Sử dụng máy tính cơ bản",50));
        models.add(new ModuleModel("C3l6DLBNocOn2ho4SMGj","IU03","Xử lý văn bản cơ bản",50));
        models.add(new ModuleModel("p25xVy0FGbvUeCTet8lG","IU04","Sử dụng bảng tính cơ bản",50));
        models.add(new ModuleModel("SFevDrt8D3rjHDqn5KeX","IU05","Sử dụng trình chiếu cơ bản",50));
        models.add(new ModuleModel("3lsHiGSUMaFQkdkagIx0","IU06","Sử dụng Internet cơ bản",50));

        List<Integer> buttonIds = Arrays.asList(
                R.id.buttonQuizModule1,
                R.id.buttonQuizModule2,
                R.id.buttonQuizModule3,
                R.id.buttonQuizModule4,
                R.id.buttonQuizModule5,
                R.id.buttonQuizModule6
        );

        for (Integer buttonId : buttonIds) {
            buttonQuizModule = view.findViewById(buttonId);
            buttonQuizModule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = buttonIds.indexOf(view.getId());
                    if (index >= 0 && index < models.size()) {
                        String moduleID = models.get(index).getId();
                        StartFragment startFragment = new StartFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("Key",moduleID);

                        startFragment.setArguments(bundle);


                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, startFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }
        return view;
    }

}