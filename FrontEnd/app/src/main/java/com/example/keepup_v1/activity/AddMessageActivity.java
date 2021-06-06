package com.example.keepup_v1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.SignIn.LoginActivity;
import com.example.keepup_v1.funcs.ConnectQueue;
import com.example.keepup_v1.funcs.PhotoUtils;
import com.example.keepup_v1.funcs.getPhotoFromPhotoAlbum;
import com.example.keepup_v1.http.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public class AddMessageActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    public static String usr_id = MyApplication.user_id;
    RequestQueue mQueue = MyApplication.requestQueue;

    private EditText et_message;
    private ImageView iv_photo;
    private Button bt_send;
    private Button bt_album;
    private Button bt_camera;

    private final int ALBUM = 2;
    private final int CAMERA = 1;
    private String photoPath = null;
    private File cameraSavePath;
    private Uri uri;
    private String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Bitmap bitmap;
    private String imageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        et_message = findViewById(R.id.et_add_msg_message);
        iv_photo = findViewById(R.id.iv_add_msg_photo);
        bt_send = findViewById(R.id.bt_add_msg_send);
        bt_album = findViewById(R.id.bt_add_msg_album);
        bt_camera = findViewById(R.id.bt_add_msg_camera);

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath()+"/"+System.currentTimeMillis()+".jpg");

        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EasyPermissions.hasPermissions(AddMessageActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE})){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,ALBUM);
                }else{
                    EasyPermissions.requestPermissions(AddMessageActivity.this,"Need Your Album Permission", 1, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE});
                }
            }
        });

        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EasyPermissions.hasPermissions(AddMessageActivity.this,new String[] {Manifest.permission.CAMERA})){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        uri = FileProvider.getUriForFile(AddMessageActivity.this,"com.example.hxd.pictest.fileprovider",cameraSavePath);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                        AddMessageActivity.this.startActivityForResult(intent,CAMERA);
                    }
                }else{
                    EasyPermissions.requestPermissions(AddMessageActivity.this,"Need Your Album Permission", 1, new String[] {Manifest.permission.CAMERA});
                }
            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_message();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResult,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA && requestCode == RESULT_OK) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                photoPath = String.valueOf(cameraSavePath);
            }else{
                photoPath = uri.getEncodedPath();
            }
            Log.d("Photo Path:",photoPath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Bitmap srcBitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream(photoPath));
            //iv_photo.setImageBitmap(srcBitmap);
            addPhotoToFlow(photoPath);
            //Glide.with(AddMessageActivity.this).load(photoPath).into(iv_photo);
        }else if(requestCode == ALBUM && requestCode == RESULT_OK){
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this,data.getData());
            //Bitmap srcBitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream(photoPath));
            //iv_photo.setImageBitmap(srcBitmap);
            addPhotoToFlow(photoPath);
            //Glide.with(AddMessageActivity.this).load(photoPath).into(iv_photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void send_message(){
        final String message = et_message.getText().toString();
        String URLline = Constants.ADD_MSG_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if (jsonObject.getString("code").equals("200")) {
                                Intent intent = new Intent(AddMessageActivity.this, MediaActivity.class);
                                startActivity(intent);
                            }
                            Toast.makeText(AddMessageActivity.this,msg,Toast.LENGTH_LONG).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddMessageActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map getParams() throws AuthFailureError {
                Map paramMap = new HashMap<String,String>();
                paramMap.put("usr_id",usr_id);
                paramMap.put("content",message);
                if(photoPath!=null) {
                    paramMap.put("pic", bitmap(photoPath));
                }
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }

    private void addPhotoToFlow(String path){
        int degree = PhotoUtils.getBitmapDegree(path);
        Bitmap bitmap1 = PhotoUtils.decodeSampledBitmapFromFd(path,250,250);
        Bitmap bitmap2 = PhotoUtils.rotateBitmapByDegree(bitmap1, degree);
        iv_photo.setImageBitmap(bitmap2);
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