package com.example.test_mku.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test_mku.models.ModuleModel;

import java.util.ArrayList;
import java.util.List;

public class ModuleSpinnerAdapter extends ArrayAdapter<ModuleModel> {
    private Context mContext;
    private ArrayList<ModuleModel> mModules;

    public ModuleSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ModuleModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mModules = objects;
    }

    @Override
    public int getCount() {
        return mModules.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textModule = (TextView) super.getView(position, convertView, parent);
        textModule.setText(mModules.get(position).getName());
        return textModule;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textModule = (TextView) super.getDropDownView(position, convertView, parent);
        textModule.setText(mModules.get(position).getName());
        return textModule;
    }
}
