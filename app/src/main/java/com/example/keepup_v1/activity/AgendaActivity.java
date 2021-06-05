package com.example.keepup_v1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.adapters.RegistrationAdapter;
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.bean.CourseBean;
import com.example.keepup_v1.calender.SpecialCalendar;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;


public class AgendaActivity extends BaseActivity implements GridView.OnItemClickListener{
    @BindView(R.id.registration_calendar_gv)
    GridView registration_calendar_gv;
    @BindView(R.id.today)
    TextView today;
    @BindView(R.id.affiche)
    Button affiche;
    String usr_id=MyApplication.user_id;
    String url= Constants.BASE_URL;
    private static RequestQueue mQueue= MyApplication.requestQueue;
    private RegistrationAdapter regisadapter;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    @Nullable
    @BindView(R.id.day)
    TextView view1;
    private RadioGroup rg_agenda;
    private RadioButton rb_home;
    private RadioButton rb_running;
    private RadioButton rb_media;
    private RadioButton rb_agenda;
    private RadioButton rb_profile;
    private Drawable draw_home;
    private Drawable draw_running;
    private Drawable draw_media;
    private Drawable draw_agenda;
    private Drawable draw_profile;
    int mYear=0;//年
    int mMonth=0;//月
    int mDay=0;//日
    private static ArrayList<Integer> date = new ArrayList<>();
    @Override
    protected int getResourceId() {
        return R.layout.activity_agenda;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        date.add(1);
//        date.add(5);
//        date.add(7);
//        date.add(19);
        getData();
        rg_agenda = findViewById(R.id.agenda_radioGroup);

        rb_home = findViewById(R.id.rb_agen_main);
        if(rb_home == null){
            Log.d("error","rb_home null");
        }
        Drawable draw_home = getResources().getDrawable(R.drawable.bt_home);
        draw_home.setBounds(0,0,50,50);
        rb_home.setCompoundDrawables(null,draw_home,null,null);

        rb_running = findViewById(R.id.rb_agen_running);
        Drawable draw_running = getResources().getDrawable(R.drawable.bt_running);
        draw_running.setBounds(0,0,50,50);
        rb_running.setCompoundDrawables(null,draw_running,null,null);

        rb_media = findViewById(R.id.rb_agen_media);
        Drawable draw_media = getResources().getDrawable(R.drawable.bt_media);
        draw_media.setBounds(0,0,50,50);
        rb_media.setCompoundDrawables(null,draw_media,null,null);

        rb_agenda = findViewById(R.id.rb_agen_agenda);
        Drawable draw_agenda = getResources().getDrawable(R.drawable.bt_agenda);
        draw_agenda.setBounds(0,0,50,50);
        rb_agenda.setCompoundDrawables(null,draw_agenda,null,null);

        rb_profile = findViewById(R.id.rb_agen_my);
        Drawable draw_profile = getResources().getDrawable(R.drawable.bt_profile);
        draw_profile.setBounds(0,0,50,50);
        rb_profile.setCompoundDrawables(null,draw_profile,null,null);

        rg_agenda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            Intent intentMain = new Intent(AgendaActivity.this,MainActivity.class);
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_agen_main:
                        MainActivity.JUMP_FRAGMENT = "main";
                        startActivity(intentMain);
                        Log.d("jump","ok");
                        break;
                    case R.id.rb_agen_running:
                        Intent intentRunning = new Intent(AgendaActivity.this,RunningActivity.class);
                        startActivity(intentRunning);
                        break;
                    case R.id.rb_run_media:
                        Intent intentMedia = new Intent(AgendaActivity.this,MediaActivity.class);
                        startActivity(intentMedia);
                        break;
                    case R.id.rb_run_agenda:
                        break;
                    case R.id.rb_run_my:
                        MainActivity.JUMP_FRAGMENT = "profile";
                        startActivity(intentMain);
                        break;
                }
            }
        });
    }
//

    private void getData() {

        ArrayList<Integer> date1 = new ArrayList<>();
        String URLline = url + "/getdate?id=" + usr_id;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals("200")) {
                                String data = jsonObject.getString("checked");
                                List<String> list = Arrays.asList(data.split(","));
                                if(list.size()!=0) {
                                    for (int i = 0; i < list.size(); i++) {
                                        if (i == 0) {
                                            date.add(Integer.parseInt(list.get(i).substring(2, 3)));
                                        } else {
                                            date.add(Integer.parseInt(list.get(i).substring(1, 2)));
                                        }
                                    }
                                    System.out.println("q" + date);
                                    regisadapter.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(AgendaActivity.this, "You don't have checked yet", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Log.d("Response Sendback", "error");
                                String msg = jsonObject.getString("msg");
//                                tv_notation.setText(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgendaActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);
        Calendar calendar=Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) +1;
        mDay = calendar.get(Calendar.DAY_OF_MONTH) ;

        SpecialCalendar mCalendar=new SpecialCalendar();
        boolean isLeapYear =mCalendar.isLeapYear(mYear);
        int mDays=mCalendar.getDaysOfMonth(isLeapYear,mMonth);
        int week =mCalendar.getWeekdayOfMonth(mYear,mMonth)+4;
        System.out.println(date);
        regisadapter=new RegistrationAdapter(AgendaActivity.this,mDays,week,mDay,date);
        registration_calendar_gv.setAdapter(regisadapter);

        registration_calendar_gv.setOnItemClickListener(this);
        today.setText(mYear+" / "+mMonth+" / "+mDay+"  ");
    }

//       for(int i=0;i<30;i++){
//           for(int j=0;j<date.size();j++){
//                   regisadapter.changeview(view1,i,j);
//           }
//       }
//       Log.d("msg","111");
//       regisadapter.notifyDataSetChanged();
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String today=mYear+"-"+mMonth+"-"+l;
        if (l!=0){
            if (l==mDay){
                TextView today_tv= (TextView) view.findViewById(R.id.day);
                today_tv.setBackgroundResource(R.mipmap.member_ok);
                today_tv.setTextColor(Color.WHITE);
                today_tv.setText(l+"");
                view.setBackgroundColor(Color.parseColor("yellow"));
                Toast.makeText(view.getContext(),"Good work",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(view.getContext(),"The date you choose:"+today,Toast.LENGTH_SHORT).show();
            }
        }
    }
}