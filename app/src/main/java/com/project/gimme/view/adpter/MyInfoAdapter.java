package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.MyInfoVO;

import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/18 16:21
 */
public class MyInfoAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<MyInfoVO> myInfoVOList;

    public MyInfoAdapter(Context context, List<MyInfoVO> myInfoVOList) {
        layoutInflater = LayoutInflater.from(context);
        this.myInfoVOList = myInfoVOList;
    }

    @Override
    public int getCount() {
        if (myInfoVOList == null) {
            return 0;
        } else {
            return myInfoVOList.size();
        }
    }

    @Override
    public MyInfoVO getItem(int position) {
        return myInfoVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myInfoVOList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyInfoVO myInfoVO = myInfoVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.gridview_my_info, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.nick = convertView.findViewById(R.id.listview_my_info_text);
        viewHolder.nick.setText(myInfoVO.getNick());
        viewHolder.description = convertView.findViewById(R.id.listview_my_info_description);
        viewHolder.description.setText(myInfoVO.getDescription());
        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView nick;
        TextView description;
    }
}
