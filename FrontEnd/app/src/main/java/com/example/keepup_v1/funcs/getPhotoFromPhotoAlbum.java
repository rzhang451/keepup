package com.example.keepup_v1.funcs;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;

public class getPhotoFromPhotoAlbum {
    public static String getRealPathFromUri(Context context, Uri uri){
        int sdkVersion = Build.VERSION.SDK_INT;
        if(sdkVersion>=19){
            return getRealPathFromUriAbove19(context,uri);
        }else{
            return getRealPathFromUrilBelow19(context,uri);
        }
    }

    private static String getRealPathFromUriAbove19(Context context, Uri uri){
        return getDataColumn(context,uri,null,null);
    }

    @SuppressLint("NewApi")
    private static String getRealPathFromUrilBelow19(Context context, Uri uri){
        String filePath = null;
        if(DocumentsContract.isDocumentUri(context,uri)){
            String documentId = DocumentsContract.getDocumentId(uri);
            if(isMediaDocument(uri)){
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=";
                String[] selectArgs = {id};
                filePath = getDataColumn(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection,selectArgs);
            }else if(idDownloadDocument(uri)){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(documentId));
                filePath = getDataColumn(context,contentUri,null,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            filePath = getDataColumn(context,uri,null,null);
        }else if("file".equals(uri.getScheme())){
            filePath=uri.getPath();
        }
        return filePath;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs){
        String path = null;
        String[] projection = new String[] {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try{
            cursor = context.getContentResolver().query(uri,projection,selection,selectionArgs,null);
            if(cursor != null && cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        }catch (Exception e){
            if(cursor != null){
                cursor.close();
            }
        }
        return path;
    }

    private static boolean isMediaDocument(Uri uri){
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean idDownloadDocument(Uri uri){
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
