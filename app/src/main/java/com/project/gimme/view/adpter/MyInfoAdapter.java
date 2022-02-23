package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.MyInfoListVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/18 16:21
 */
public class MyInfoAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<MyInfoListVO> myInfoListVOList;

    public MyInfoAdapter(Context context, List<MyInfoListVO> myInfoListVOList) {
        layoutInflater = LayoutInflater.from(context);
        this.myInfoListVOList = myInfoListVOList;
    }

    @Override
    public int getCount() {
        if (myInfoListVOList == null) {
            return 0;
        } else {
            return myInfoListVOList.size();
        }
    }

    @Override
    public MyInfoListVO getItem(int position) {
        return myInfoListVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
        //TODO:待调整
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyInfoListVO myInfoListVO = myInfoListVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.gridview_my_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.icon.setImageResource(R.mipmap.app_icon);
        viewHolder.nick.setText(myInfoListVO.getNick());
        viewHolder.description.setText(myInfoListVO.getDescription());
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_my_info_icon)
        ImageView icon;
        @BindView(R.id.listview_my_info_text)
        TextView nick;
        @BindView(R.id.listview_my_info_description)
        TextView description;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
