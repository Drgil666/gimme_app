package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.project.gimme.pojo.vo.SearchVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SearchVoAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    private List<SearchVO> itemList = new ArrayList<>();
    private Context context;

    public SearchVoAdapter(Context context, List<SearchVO> personalMsgVOList) {
        layoutInflater = LayoutInflater.from(context);
        itemList = personalMsgVOList;
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public SearchVO getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getObjectId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO:待完成
        return null;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
