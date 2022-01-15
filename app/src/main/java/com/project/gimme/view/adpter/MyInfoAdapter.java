package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/15 16:24
 */
public class MyInfoAdapter extends BaseAdapter {
    private List<String> itemList;
    LayoutInflater layoutInflater;

    public MyInfoAdapter(Context context, List<String> itemList) {
        layoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public String getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private static class ViewHolder {

    }
}
