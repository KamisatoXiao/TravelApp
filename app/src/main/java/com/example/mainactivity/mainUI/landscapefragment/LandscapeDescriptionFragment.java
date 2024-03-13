package com.example.mainactivity.mainUI.landscapefragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;

public class LandscapeDescriptionFragment extends Fragment {
    private TextView landscape_description;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_description,null);
        SharedPreferences sp= getContext().getSharedPreferences("index", Context.MODE_PRIVATE);
        String description=sp.getString("description","");
        landscape_description=rootView.findViewById(R.id.description);
        landscape_description.setText(description);
        return rootView;
    }
}
