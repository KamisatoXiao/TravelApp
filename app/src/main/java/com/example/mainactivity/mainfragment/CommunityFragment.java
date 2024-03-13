package com.example.mainactivity.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.mainactivity.AddJourneyActivity;
import com.example.mainactivity.R;

public class CommunityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_community,null);
        ImageView addJourney=rootView.findViewById(R.id.add_journey);
        addJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddJourneyActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
