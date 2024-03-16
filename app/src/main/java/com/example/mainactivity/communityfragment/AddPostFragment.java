package com.example.mainactivity.communityfragment;

import static android.app.Activity.RESULT_OK;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.UploadFileRequest;
import com.example.mainactivity.R;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import jp.wasabeef.richeditor.RichEditor;

public class AddPostFragment extends Fragment {
    private ImageView addTextBold,addTextItalic,addTextStrikeThrough,addTextUnderline,addPicture,addVideo;
    private ImageView back;
    private TextView post_send;
    private RichEditor editor;
    private int CHOOSE_CODE=3;
    private int CHOOSE_VIDEO_CODE=4;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_post,container,false);
        addTextBold=rootView.findViewById(R.id.add_text_bold);
        addTextItalic=rootView.findViewById(R.id.add_text_italic);
        addTextStrikeThrough=rootView.findViewById(R.id.add_text_strike_through);
        addTextUnderline=rootView.findViewById(R.id.add_text_underline);
        addPicture=rootView.findViewById(R.id.add_pic);
        addVideo=rootView.findViewById(R.id.add_video);
        editor=rootView.findViewById(R.id.main_journey);
        back=rootView.findViewById(R.id.back);
        post_send=rootView.findViewById(R.id.post_send);
        editor.setPadding(10,10,10,10);
        editor.setFontSize(20);
        editor.setPlaceholder("请尽情发挥吧...");
        addTextBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setBold();
            }
        });
        addTextItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setItalic();
            }
        });
        addTextStrikeThrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setStrikeThrough();
            }
        });
        addTextUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.setUnderline();
            }
        });
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                albumIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 是否允许多选
                albumIntent.setType("image/*"); // 类型为图像
                startActivityForResult(albumIntent, CHOOSE_CODE); // 打开系统相册
            }
        });
//        addVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
//                startActivityForResult(intent, CHOOSE_CODE); // 打开系统视频库
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        post_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化OSSClient
                OSS client=new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
                EnvironmentVariableCredentialsProvider credentialsProvider= CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode==RESULT_OK && requestCode == CHOOSE_CODE) { // 从相册返回
            if (intent.getData() != null) { // 从相册选择一张照片
                Uri uri = intent.getData(); // 获得已选择照片的路径对象
                // 获取图片尺寸
                Point imageSize = getImageSizeFromUri(getContext(), uri);
                int width = imageSize.x;
                int height = imageSize.y;
                DisplayMetrics displayMetrics=new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth=displayMetrics.widthPixels;
                if (width>screenWidth){
                    int new_width=width/4;
                    int new_height=height/4;
                    editor.insertImage(uri.toString(), "image", new_width, new_height);
                }else{
                    editor.insertImage(uri.toString(), "image",width,height);
                }
            }
        }
    }

    private Point getImageSizeFromUri(Context context, Uri uri) {
        ContentResolver resolver = context.getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = resolver.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            int width = options.outWidth;
            int height = options.outHeight;
            return new Point(width, height);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Point(0, 0);
    }
}
