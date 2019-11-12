package com.shuiyinhuo.component.mixdev.utils.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.CharConversionException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhiheng Su
 * @Data: 2017/2/16.
 * @Description:
 */
public abstract class MyBaseAdapter<T> extends android.widget.BaseAdapter {
    protected ArrayList<T> data;
    private Object temp;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    protected boolean isUseCatch=true;

    public MyBaseAdapter(Context context) {
        mContext = context;
        iniDatas();
    }

    public void reFrishDatas(ArrayList<T> newDatas){
        this.data=newDatas;
        this.notifyDataSetChanged();
    }

    public void iniDatas(){
        mLayoutInflater=LayoutInflater.from(mContext);
    };

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (isUseCatch) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(getLayoutId(), parent, false);
                convertView = setView(convertView);
            }

        }else {
            convertView = mLayoutInflater.inflate(getLayoutId(), parent, false);
            convertView = setView(convertView);
        }
        temp=  convertView.getTag();

        loadData(position,temp);
        return convertView;
    }

    public abstract View setView(View convertView);
    public abstract void loadData(int position,Object viewHolder);
    public abstract int getLayoutId();

}
