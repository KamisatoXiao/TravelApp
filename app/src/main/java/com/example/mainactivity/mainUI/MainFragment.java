package com.example.mainactivity.mainUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mainactivity.mainUI.LandscapeActivity;
import com.example.mainactivity.mainUI.ProvinceActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.mainUI.SearchResultActivity;
import com.example.mainactivity.adapter.BannerAdapter;

public class MainFragment extends Fragment {
    public static final int CAROUSEL_TIME=5000;//滚动间隔
    private ViewPager vpBanner;
    private ViewGroup viewGroup;//可看到ViewPager选中状态
    private BannerAdapter bannerAdapter;
    private Handler handler=new Handler();
    private int currentItem=0;//ViewPager当前位置
    private final Runnable mTicker=new Runnable() {
        @Override
        public void run() {
            long now= SystemClock.uptimeMillis();
            long next=now+(CAROUSEL_TIME-now%CAROUSEL_TIME);
            handler.postAtTime(mTicker,next);//延迟5s执行runnable，同计时器效果
            currentItem++;
            vpBanner.setCurrentItem(currentItem);
        }
    };
    private TextView tv_all_landscape,province;
    private EditText searchInfo;
    private Button search;
    private String searchSentence;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_home,null);
        vpBanner=rootView.findViewById(R.id.image_Banner);
        tv_all_landscape=rootView.findViewById(R.id.tv_all_landscape);
        tv_all_landscape.setOnClickListener(onClickListener);
        province=rootView.findViewById(R.id.province);
        searchInfo=rootView.findViewById(R.id.search_info);
        search=rootView.findViewById(R.id.search);
        search.setOnClickListener(onClickListener);
        province.setOnClickListener(onClickListener);
        SharedPreferences sp= getContext().getSharedPreferences("index", Context.MODE_PRIVATE);
        String get_province=sp.getString("province","南昌");
        province.setText(get_province);
        /*
         * 轮播图实现
         */
        bannerAdapter=new BannerAdapter(getContext());
        bannerAdapter.setOnBannerClickListener(onBannerClickListener);//图片点击监听
        vpBanner.setOffscreenPageLimit(2);
        vpBanner.setAdapter(bannerAdapter);
        vpBanner.addOnPageChangeListener(onPageChangeListener);//页面改变监听
        viewGroup=rootView.findViewById(R.id.viewGroup);
        for (int i=0;i<bannerAdapter.getBanners().length;i++){
            ImageView imageView=new ImageView(getContext());
            //图片宽高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            if(i==0){
                imageView.setBackgroundResource(R.mipmap.icon_first_launcher_page_select_three);
            }else{
                imageView.setBackgroundResource(R.mipmap.icon_first_launcher_page_normal);
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin=10;
            layoutParams.rightMargin=10;
            viewGroup.addView(imageView,layoutParams);
        }
        //设置当前页
        currentItem=bannerAdapter.getBanners().length*50;
        vpBanner.setCurrentItem(currentItem);
        handler.postDelayed(mTicker,CAROUSEL_TIME);//计时器
        /*搜索功能*/
        return rootView;
    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.tv_all_landscape){
                Intent intent=new Intent(getContext(), LandscapeActivity.class);
                startActivity(intent);
            }else if(v.getId()==R.id.province){
                Intent intent=new Intent(getContext(), ProvinceActivity.class);
                startActivity(intent);
            }else if (v.getId()==R.id.search){
                if(!searchInfo.getText().toString().isEmpty()){
                    Intent intent=new Intent(getContext(), SearchResultActivity.class);
                    searchSentence=searchInfo.getText().toString();
                    intent.putExtra("key",searchSentence);
                    startActivity(intent);
                }
            }
        }
    };
    /*
     * 轮播图相关函数
     */
    private  ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            currentItem=position;
            //选中状态
            setImageBackground(position%=bannerAdapter.getBanners().length);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    /*
     * 改变点的切换效果
     * @param selectItems 当前选中位置
     */
    private void setImageBackground(int selectItem){
        for (int i=0;i<bannerAdapter.getBanners().length;i++){
            ImageView imageView=(ImageView)viewGroup.getChildAt(i);
            imageView.setBackgroundDrawable(null);
            if (i==selectItem){
                imageView.setImageResource(R.mipmap.icon_first_launcher_page_select_three);
            }else{
                imageView.setImageResource(R.mipmap.icon_first_launcher_page_normal);
            }
        }
    }

    private View.OnClickListener onBannerClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position=(Integer)view.getTag();//从tag中取出当前点击的ImageView的位置
            int toast_show_count=position+1;
            Toast.makeText(getContext(), "当前点击位置:"+toast_show_count, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mTicker);//删除计时器
    }
}
