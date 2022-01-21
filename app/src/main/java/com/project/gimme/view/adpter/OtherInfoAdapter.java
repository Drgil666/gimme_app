package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class OtherInfoAdapter extends BaseAdapter {
    private List<UserVO> userVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public OtherInfoAdapter(Context context, List<UserVO> userVOList) {
        layoutInflater = LayoutInflater.from(context);
        this.userVOList = userVOList;
    }

    @Override
    public int getCount() {
        if (userVOList == null) {
            return 0;
        }
        return userVOList.size();
    }

    @Override
    public UserVO getItem(int position) {
        return userVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userVOList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserVO userVO = userVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.gridview_other_info, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.nick = convertView.findViewById(R.id.gridview_other_info_nick);
        if (userVO.getNote() != null) {
            viewHolder.nick.setText(userVO.getNote());
        } else {
            viewHolder.nick.setText(userVO.getNick());
        }
        if (userVO.getId() == -1) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("click!");
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView nick;
    }
}
