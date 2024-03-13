package com.example.mainactivity.launcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.EdgeEffectCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FirstLaunchActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private int[] images=new int[]{
            R.drawable.launch_pic_1,
            R.drawable.launcher_pic_2,
            R.drawable.launcher_pic_3};
    private List<View> mImageView=new ArrayList<>();
    private List<ImageView> tips=new ArrayList<>();
    private ViewGroup group;
    private EdgeEffectCompat rightEdge;
    private TextView tvGotoMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_launch);
        group=findViewById(R.id.viewGroup);
        viewPager=findViewById(R.id.viewPager);
        tvGotoMain=findViewById(R.id.tv_goto_main);

        try {
            Field rightEdgeField=viewPager.getClass().getDeclaredField("mRightEdge");
            if(rightEdgeField!=null){
                rightEdgeField.setAccessible(true);
                rightEdge=(EdgeEffectCompat) rightEdgeField.get(viewPager);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //图片装入数组
        for (int i=0;i<images.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(images[i]);
            mImageView.add(imageView);
        }

        //圆点加入ViewGroup
        for (int i=0;i<images.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            if(i==0){
                imageView.setBackgroundResource(R.mipmap.icon_first_launcher_page_select_one);
            }else{
                imageView.setBackgroundResource(R.mipmap.icon_first_launcher_page_normal);
            }
            tips.add(imageView);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin=10;
            layoutParams.rightMargin=10;
            group.addView(imageView,layoutParams);
        }
        viewPager.setAdapter(new PreviewImageAdapter());
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        tvGotoMain.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.tv_goto_main){
                gotoMain();
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            if(position==images.length-1){
                tvGotoMain.setVisibility(View.VISIBLE);
            }else{
                tvGotoMain.setVisibility(View.INVISIBLE);
            }
            setImageBackground(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(rightEdge!=null&&!rightEdge.isFinished()){
                gotoMain();
            }
        }
    };

    /*
    * 设置选中的tip的背景
    * @param selectItems
    */
    private void setImageBackground(int selectItem){
        for(int i=0;i<tips.size();i++){
            if(i==selectItem){
                if(i==0){
                    tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_select_one);
                }else if(i==1){
                    tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_select_two);
                }else if(i==2){
                    tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_select_three);
                }
            }else{
                tips.get(i).setBackgroundResource(R.mipmap.icon_first_launcher_page_normal);
            }
        }
    }

    public class PreviewImageAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mImageView.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
            if(position<mImageView.size()){
                ((ViewPager)container).removeView(mImageView.get(position));
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull View container, int position) {
            ((ViewPager)container).addView(mImageView.get(position));
            return mImageView.get(position);
        }
    }

    /*
    * 跳转到首页
    */
    private void gotoMain(){
        setFirstLauncherBoolean();
        Intent intent=new Intent(FirstLaunchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    * 第一次启动执行完成，值设置为true
    */
    private void setFirstLauncherBoolean(){
        SharedPreferences sp=getSharedPreferences("launcher", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();
        edit.putBoolean(LauncherActivity.FIRST_LAUNCHER,true);
        edit.commit();
    }
}