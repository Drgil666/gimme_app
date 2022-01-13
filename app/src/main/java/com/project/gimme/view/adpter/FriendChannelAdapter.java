package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class FriendChannelAdapter extends BaseAdapter {
    private List<Channel> channelList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public FriendChannelAdapter(Context context, List<Channel> channelList) {
        layoutInflater = LayoutInflater.from(context);
        this.channelList = channelList;
    }

    @Override
    public int getCount() {
        if (channelList == null) {
            return 0;
        }
        return channelList.size();
    }

    @Override
    public Channel getItem(int position) {
        return channelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return channelList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Channel channel = channelList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_friend_list_channel_list, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text = convertView.findViewById(R.id.listview_friend_list_channel_list_nick);
        viewHolder.text.setText(channel.getNick());
        viewHolder.icon = convertView.findViewById(R.id.listview_friend_list_channel_list_image);
        viewHolder.icon.setImageResource(R.mipmap.app_icon);
        return convertView;
    }

    private static class ViewHolder {
        TextView text;
        ImageView icon;
    }
}
