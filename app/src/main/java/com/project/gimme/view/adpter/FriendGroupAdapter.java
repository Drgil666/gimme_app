package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:43
 */
public class FriendGroupAdapter extends BaseAdapter {
    private List<Group> groupList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public FriendGroupAdapter(Context context, List<Group> groupList) {
        layoutInflater = LayoutInflater.from(context);
        this.groupList = groupList;
    }

    @Override
    public int getCount() {
        if (groupList == null) {
            return 0;
        }
        return groupList.size();
    }

    @Override
    public Group getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return groupList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Group group = groupList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_friend_list_group_list, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = convertView.findViewById(R.id.listview_friend_list_group_list_nick);
        viewHolder.text.setText(group.getNick());
        viewHolder.icon = convertView.findViewById(R.id.listview_friend_list_group_list_image);
        viewHolder.icon.setImageResource(R.mipmap.app_icon);
        return convertView;
    }

    private static class ViewHolder {
        TextView text;
        ImageView icon;
    }
}
