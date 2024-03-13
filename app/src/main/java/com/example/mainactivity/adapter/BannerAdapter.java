package com.example.mainactivity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.example.mainactivity.R;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private View.OnClickListener onBannerClickListener;

    //图片列表
    private int[] banners=new int[]{
            R.drawable.banner_p1,
            R.drawable.banner_p2,
            R.drawable.banner_p3,
            R.drawable.banner_p4,
            R.drawable.banner_p5};
    public BannerAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//返回int的最大值 可以一直滑动
    }

    @Override
    public boolean isViewFromObject(View view,Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*
        * position的值的范围是0至2147483647，
        * 把这个值对图片长度求余之后，
        * position的取值范围是0至banner.length-1
        */
        position %=banners.length;
        ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//图片缩放类型
        imageView.setTag(position);//当前下标
        imageView.setImageResource(banners[position]);
        imageView.setOnClickListener(onClickListener);
        container.addView(imageView);
        return imageView;
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onBannerClickListener!=null){
                onBannerClickListener.onClick(v);
            }
        }
    };

    /*
    * 设置图片点击
    * @param onBannerClickListener
    */
    public void  setOnBannerClickListener(View.OnClickListener onBannerClickListener){
        this.onBannerClickListener=onBannerClickListener;
    }

    public int[] getBanners(){
        return banners;
    }
}
