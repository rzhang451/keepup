package com.example.keepup_v1.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.keepup_v1.R;

import java.util.ArrayList;


public class RegistrationAdapter extends BaseAdapter {
    private Context context;
    private final int days;
    private final int week;
    private int[] dayNumber;
    private final int day;
    private ArrayList<Integer> date;
    public RegistrationAdapter(Context context, int days, int week,int day,ArrayList date) {
        this.context = context;
        this.days = days;
        this.week = week;
        this.day=day;
        this.date=date;
        getEveryDay();
    }



    @Override
    public int getCount() {
        return 42;
    }

    @Override
    public String getItem(int i) {

        return null;
    }

    @Override
    public long getItemId(int i) {
        return dayNumber[i];
    }//点击时
    private ViewHolder viewHolder;
//    private ViewHolder viewHolder2;
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_registrationadatper, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.day.setText(dayNumber[i] == 0 ? "" : dayNumber[i] + "");
        for (int j = 0; j < date.size(); j++) {
            if (dayNumber[i] != 0 && dayNumber[i] == date.get(j)) {
                viewHolder.day.setBackgroundResource(R.mipmap.member_ok);
            }
        }
            if (dayNumber[i] == day) {
                viewHolder.day.setText(" T ");
                view.setBackgroundResource(R.color.red);
                viewHolder.day.setTextColor(Color.parseColor("black"));
            }

            return view;
        }

//    public View changeview(View view2,int x,int y){
//        view2=LayoutInflater.from(context).inflate(R.layout.item_registrationadatper,null);
//        viewHolder=new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_registrationadatper,null));
//        view2.getTag();
//        if(getItemId(x)==y){
//            Log.d("!1","ss");
//            viewHolder.day.setBackgroundResource(R.mipmap.member_ok);
//        }
//        return view2;
//    }







    private class ViewHolder{
        private TextView day;

        public ViewHolder(View view) {

            this.day = (TextView) view.findViewById(R.id.day);
        }


    }

    /**
     * 得到42格子 每一格子的值
     */
    private void getEveryDay(){
        dayNumber=new int[42];

        for (int i=0;i<42;i++){
            if (i < days+week && i >= week){
                dayNumber[i]=i-week+1;
            }else{
                dayNumber[i]=0;
            }
        }
    }
}