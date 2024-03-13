package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mainactivity.communityfragment.AddNewsFragment;
import com.example.mainactivity.communityfragment.AddPictureFragment;
import com.example.mainactivity.communityfragment.AddPostFragment;
import com.example.mainactivity.communityfragment.AddVideoFragment;

public class AddJourneyActivity extends AppCompatActivity {
    private Fragment AddNewsFragment;
    private Fragment AddPictureFragment;
    private Fragment AddPostFragment;
    private Fragment AddVideoFragment;
    private int currentId=R.id.tv_post;
    private TextView tvPost,tvPic,tvVideo,tvDoing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journey);
        tvPost=findViewById(R.id.tv_post);
        tvPic=findViewById(R.id.tv_pic);
        tvVideo=findViewById(R.id.tv_video);
        tvDoing=findViewById(R.id.tv_doing);
        AddPostFragment=new AddPostFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_journey_container,AddPostFragment).commit();
        tvPost.setOnClickListener(tabClickListener);
        tvPic.setOnClickListener(tabClickListener);
        tvVideo.setOnClickListener(tabClickListener);
        tvDoing.setOnClickListener(tabClickListener);
    }
    private View.OnClickListener tabClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()!=currentId){
                changeSelect(v.getId());
                changeFragment(v.getId());
                currentId=v.getId();
            }
        }
    };

    private void changeFragment(int resId){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        if (resId==R.id.tv_post){
            if (AddPostFragment==null){
                AddPostFragment=new AddPostFragment();
                transaction.add(R.id.main_journey_container,AddPostFragment);
            }else{
                transaction.show(AddPostFragment);
            }
        }else if(resId == R.id.tv_pic) {
            if (AddPictureFragment==null){
                AddPictureFragment=new AddPictureFragment();
                transaction.add(R.id.main_journey_container,AddPictureFragment);
            }else{
                transaction.show(AddPictureFragment);
            }
        }else if(resId == R.id.tv_video) {
            if (AddVideoFragment == null) {
                AddVideoFragment = new AddVideoFragment();
                transaction.add(R.id.main_journey_container, AddVideoFragment);
            } else {
                transaction.show(AddVideoFragment);
            }
        }else if(resId == R.id.tv_doing) {
            if (AddNewsFragment == null) {
                AddNewsFragment = new AddNewsFragment();
                transaction.add(R.id.main_journey_container, AddNewsFragment);
            } else {
                transaction.show(AddNewsFragment);
            }
        }
        transaction.commit();
    }
    private void hideFragments(FragmentTransaction transaction){
        if (AddPostFragment!=null)
            transaction.hide(AddPostFragment);
        if (AddPictureFragment!=null)
            transaction.hide(AddPictureFragment);
        if (AddVideoFragment!=null)
            transaction.hide(AddVideoFragment);
        if (AddNewsFragment!=null)
            transaction.hide(AddNewsFragment);
    }
    private void changeSelect(int resId){
        tvPost.setSelected(false);
        tvPic.setSelected(false);
        tvVideo.setSelected(false);
        tvDoing.setSelected(false);

        if (resId==R.id.tv_post){
            tvPost.setSelected(true);
        }else if (resId==R.id.tv_pic){
            tvPic.setSelected(true);
        }else if (resId==R.id.tv_video){
            tvVideo.setSelected(true);
        }else if (resId==R.id.tv_doing){
            tvDoing.setSelected(true);
        }
    }
}