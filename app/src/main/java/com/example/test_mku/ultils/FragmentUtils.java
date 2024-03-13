package com.example.test_mku.ultils;

import static com.example.test_mku.R.id.fragmentContainerView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.test_mku.R;

public class FragmentUtils {
    public static void replaceFragment(FragmentManager manager,
                                       Fragment fragment,
                                       boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }
}
