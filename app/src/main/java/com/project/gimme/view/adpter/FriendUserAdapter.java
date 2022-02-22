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
import com.project.gimme.pojo.vo.UserVO;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:17
 */
public class FriendUserAdapter extends BaseAdapter {
    private List<UserVO> userList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public FriendUserAdapter(Context context, List<UserVO> userList) {
        layoutInflater = LayoutInflater.from(context);
        this.userList = userList;
    }

    @Override
    public int getCount() {
        if (userList == null) {
            return 0;
        }
        return userList.size();
    }

    @Override
    public UserVO getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserVO userVO = userList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_friend_list_friend_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        if (StringUtils.isEmpty(userVO.getNote())) {
            viewHolder.text.setText(userVO.getNick());
        } else {
            viewHolder.text.setText(userVO.getNote());
        }
        viewHolder.icon.setImageResource(R.mipmap.app_icon);
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_friend_list_friend_list_nick)
        TextView text;
        @BindView(R.id.listview_friend_list_friend_list_image)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
