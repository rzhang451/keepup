package com.example.keepup_v1.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keepup_v1.R;
import com.example.keepup_v1.bean.CourseBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class CourseAdapter extends CommonAdapter<CourseBean> {
    private TextView course_time_tv;
    private TextView course_title_tv;
    private OnClickListener mListener;
    public ImageView video;

    public CourseAdapter(Context context, List<CourseBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }
    public interface OnClickListener {

    }
    @Override
    public void convert(ViewHolder holder, CourseBean courseBean, int position) {
        course_time_tv = holder.getView(R.id.course_time_tv);
        course_title_tv = holder.getView(R.id.course_title_tv);
        video=holder.getView(R.id.video);
        course_time_tv.setText(courseBean.getDuration());
        course_title_tv.setText(courseBean.getName());
        holder.setImageBitmap(video.getId(),bitmap(courseBean.getImg())) ;
    }
    public Bitmap bitmap(String urlpath){

        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
     return map;
    }

}
