package com.example.keepup_v1.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.fragment.AgendaFragment;
import com.example.keepup_v1.fragment.MainFragment;
import com.example.keepup_v1.fragment.MeFragment;
import com.example.keepup_v1.utils.BroadCastReceiverUtil;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    //@BindView(R.id.frameLayout)
    private FrameLayout frameLayout;

    //@BindView(R.id.main_radioGroup)
    private RadioGroup mainRadioGroup;

    //@BindView(R.id.rb_main)
    private RadioButton rb_main;

    //@BindView(R.id.rb_running)
    private RadioButton rb_running;

    //@BindView(R.id.rb_media)
    private RadioButton rb_media;

    //@BindView(R.id.rb_agenda)
    private RadioButton rb_agenda;

    //@BindView(R.id.rb_my)
    private RadioButton rb_my;


    private AgendaFragment agendaFragment;

    private MainFragment mainFragment;

    private MeFragment meFragment;
    private BroadcastReceiver broadcastReceiver;

    public static String JUMP_FRAGMENT = null;
    public static String usr_id;


    @Override
    protected int getResourceId() {
        return R.layout.activity_main;

    }

    @Override
    protected void initView() {
        super.initView();
        requestPermissions();

        if(JUMP_FRAGMENT == "main"){
            Log.d("frag","main");
            showFragment(0);
            ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.btn3).init();
        }else if(JUMP_FRAGMENT == "agenda"){
            Log.d("agenda","agenda");
            showFragment(1);
            ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.btn3).init();
        }else if(JUMP_FRAGMENT == "profile"){
            showFragment(4);
            ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.btn3).init();
        }

    }
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    @Override
    protected void initData() {
        super.initData();
        if(JUMP_FRAGMENT == null) {
            showFragment(0);
        }
    }

    @Override
    protected void setDrawable(){
        super.setDrawable();
        frameLayout = findViewById(R.id.frameLayout);

        Drawable draw_home = getResources().getDrawable(R.drawable.bt_home);
        draw_home.setBounds(0, 0, 50, 50);
        rb_main = findViewById(R.id.rb_main);
        rb_main.setCompoundDrawables(null,draw_home,null,null);

        Drawable draw_running = getResources().getDrawable(R.drawable.bt_running);
        draw_running.setBounds(0, 0, 50, 50);
        rb_running = findViewById(R.id.rb_running);
        rb_running.setCompoundDrawables(null,draw_home,null,null);

        Drawable draw_media = getResources().getDrawable(R.drawable.bt_media);
        draw_media.setBounds(0, 0, 50, 50);
        rb_media = findViewById(R.id.rb_media);
        rb_media.setCompoundDrawables(null,draw_media,null,null);

        Drawable draw_agenda = getResources().getDrawable(R.drawable.bt_agenda);
        draw_agenda.setBounds(0, 0, 50, 50);
        rb_agenda = findViewById(R.id.rb_agenda);
        rb_agenda.setCompoundDrawables(null,draw_agenda,null,null);

        Drawable draw_profile = getResources().getDrawable(R.drawable.bt_profile);
        draw_profile.setBounds(0, 0, 50, 50);
        rb_my = findViewById(R.id.rb_my);
        rb_my.setCompoundDrawables(null,draw_profile,null,null);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mainRadioGroup = findViewById(R.id.main_radioGroup);
        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main:
                        showFragment(0);
                        ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.btn3).init();
                        break;
                    case R.id.rb_running:
                        Intent intentRunning = new Intent(MainActivity.this,RunningActivity.class);
                        startActivity(intentRunning);
                        break;
                    case R.id.rb_media:
                        Intent intentMedia = new Intent(MainActivity.this,MediaActivity.class);
                        startActivity(intentMedia);
                        break;
                    case R.id.rb_agenda:
//                       showFragment(1
//                       ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.btn3).init(););
                        Intent intentAgenda = new Intent(MainActivity.this,AgendaActivity.class);
                        startActivity(intentAgenda);
                        break;
                    case R.id.rb_my:
                        showFragment(4);
                        ImmersionBar.with(MainActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.btn3).init();
                        break;
                }
            }
        });
    }

    public void showFragment(int i) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragments(ft);
        switch (i) {
            case 0:
                if (mainFragment != null)
                    ft.show(mainFragment);
                else {
                    mainFragment = new MainFragment();
                    ft.add(R.id.frameLayout, mainFragment);
                }

                break;
            case 1:
                if (agendaFragment != null)
                    ft.show(agendaFragment);
                else {
                    agendaFragment = new AgendaFragment();
                    ft.add(R.id.frameLayout, agendaFragment);
                }

                break;
//            case 2:
//                if (homeFragment != null)
//                    ft.show(homeFragment);
//                else {
//                    homeFragment = new HomeFragment();
//                    ft.add(R.id.frameLayout, homeFragment);
//                }
//
//                break;
//            case 3:
//                if (exercice2Fragment != null)
//                    ft.show(exercice2Fragment);
//                else {
//                    exercice2Fragment = new Exercice2Fragment();
//                    ft.add(R.id.frameLayout, exercice2Fragment);
//                }
//
//                break;
//            case 3:
//                if (exerciceFragment != null)
//                    ft.show(exerciceFragment);
//                else {
//                    exerciceFragment = new ExerciceFragment();
//                    ft.add(R.id.frameLayout, exerciceFragment);
//                }

//                break;
            case 4:
                if (meFragment != null)
                    ft.show(meFragment);
                else {
                    meFragment = new MeFragment();
                    ft.add(R.id.frameLayout, meFragment);
                }
                ImmersionBar.with(this).keyboardEnable(false).statusBarDarkFont(false).navigationBarColor(R.color.colorPrimary).init();
                break;

        }
        ft.commit();
    }

    private void hideFragments(FragmentTransaction ft2) {
        if (mainFragment != null)
            ft2.hide(mainFragment);
        if (agendaFragment != null)
            ft2.hide(agendaFragment);
        if (meFragment != null)
            ft2.hide(meFragment);
//        if (exercice2Fragment != null)
//            ft2.hide(exercice2Fragment);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadCastReceiverUtil.unRegisterBroadcastReceiver(this, broadcastReceiver);
    }


    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
                }

                if(permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
                }

                if(permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.CAMERA},0x0010);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}