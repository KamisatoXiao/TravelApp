package com.example.mainactivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mainactivity.mainfragment.CommunityFragment;
import com.example.mainactivity.mainUI.MainFragment;
import com.example.mainactivity.mainfragment.MessageFragment;
import com.example.mainactivity.mainfragment.RouteFragment;
import com.example.mainactivity.mainfragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    private MainFragment mainFragment;
    private CommunityFragment communityFragment;
    private RouteFragment routeFragment;
    private MessageFragment messageFragment;
    private UserFragment userFragment;
    private int currentId=R.id.tv_main;
    private TextView tvMain,tvCommunity,tvRoute,tvMessage,tvUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMain=findViewById(R.id.tv_main);
        tvMain.setSelected(true);
        tvCommunity=findViewById(R.id.tv_community);
        tvRoute=findViewById(R.id.tv_route);
        tvMessage=findViewById(R.id.tv_message);
        tvUser=findViewById(R.id.tv_user);

        //默认加载首页
        mainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,mainFragment).commit();
        tvMain.setOnClickListener(tabClickListener);
        tvCommunity.setOnClickListener(tabClickListener);
        tvMessage.setOnClickListener(tabClickListener);
        tvUser.setOnClickListener(tabClickListener);
        tvRoute.setOnClickListener(tabClickListener);
    }

    private View.OnClickListener tabClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()!=currentId){
                changeSelect(v.getId());
                changeFragment(v.getId());
                currentId=v.getId();
            }
        }
    };

    private void changeFragment(int resId){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        if (resId==R.id.tv_main){
            if(mainFragment==null){
                mainFragment=new MainFragment();
                transaction.add(R.id.main_container,mainFragment);
            }else{
                transaction.show(mainFragment);
            }
        }else if(resId==R.id.tv_community){
            if(communityFragment==null){
                communityFragment=new CommunityFragment();
                transaction.add(R.id.main_container,communityFragment);
            }else{
                transaction.show(communityFragment);
            }
        }else if (resId==R.id.tv_route){
            if(routeFragment==null){
                routeFragment=new RouteFragment();
                transaction.add(R.id.main_container,routeFragment);
            }else{
                transaction.show(routeFragment);
            }
        } else if(resId==R.id.tv_message){
            if(messageFragment==null){
                messageFragment=new MessageFragment();
                transaction.add(R.id.main_container,messageFragment);
            }else{
                transaction.show(messageFragment);
            }
        }else if(resId==R.id.tv_user){
            if(userFragment==null){
                userFragment=new UserFragment();
                transaction.add(R.id.main_container,userFragment);
            }else{
                transaction.show(userFragment);
            }
        }
        transaction.commit();
    }
    private void  hideFragments(FragmentTransaction transaction){
        if (mainFragment!=null)
            transaction.hide(mainFragment);
        if (communityFragment!=null)
            transaction.hide(communityFragment);
        if (routeFragment!=null)
            transaction.hide(routeFragment);
        if (messageFragment!=null)
            transaction.hide(messageFragment);
        if (userFragment!=null)
            transaction.hide(userFragment);
    }

    private void changeSelect(int resId){
        tvMain.setSelected(false);
        tvCommunity.setSelected(false);
        tvMessage.setSelected(false);
        tvUser.setSelected(false);
        tvRoute.setSelected(false);

        if (resId==R.id.tv_main){
            tvMain.setSelected(true);
        }else if(resId==R.id.tv_community) {
            tvCommunity.setSelected(true);
        }else if(resId==R.id.tv_message) {
            tvMessage.setSelected(true);
        }else if(resId==R.id.tv_user) {
            tvUser.setSelected(true);
        }else if(resId==R.id.tv_route){
            tvRoute.setSelected(true);
        }
    }
}