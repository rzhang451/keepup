package com.example.keepup_v1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.example.keepup_v1.funcs.MessageData;

import java.util.List;

/**
 * BaseAdapter 的万能适配器，需要继承该类
 * <T> 为传入的的Adapter中的Data数据
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected String mUrl;
    protected MediaAdapter.OnItemClickListener mListener;
    protected MediaAdapter.OnItemClickLikeListener mLikeListener;
    protected MediaAdapter.OnItemEnterLisener mEnterListener;

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;



    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;

    }

    public CommonAdapter(Context context, List<T> datas, int layoutId,String url, MediaAdapter.OnItemClickListener listener, MediaAdapter.OnItemEnterLisener enterListener, MediaAdapter.OnItemClickLikeListener likeListener) {
        this.mContext = context;
        this.mListener = (MediaAdapter.OnItemClickListener) listener;
        this.mDatas = datas;
        this.layoutId = layoutId;
        this.mUrl = url;
        this.mEnterListener = enterListener;
        this.mLikeListener = likeListener;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, getItem(position), position);

        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t, int position);

}
