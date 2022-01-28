package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:17
 */
public class FriendUserAdapter extends BaseAdapter {
    private List<User> userList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public FriendUserAdapter(Context context, List<User> userList) {
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
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = userList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_friend_list_friend_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.text.setText(user.getNick());
        viewHolder.icon.setImageResource(R.mipmap.app_icon);
        return convertView;
    }

    private static class ViewHolder {
        @BindView(R.id.listview_friend_list_friend_list_nick)
        TextView text;
        @BindView(R.id.listview_friend_list_friend_list_image)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
