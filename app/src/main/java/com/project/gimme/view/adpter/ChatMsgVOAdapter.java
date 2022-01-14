package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChatMsgVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class ChatMsgVOAdapter extends BaseAdapter {
    private List<ChatMsgVO> chatMsgVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ChatMsgVOAdapter(Context context, List<ChatMsgVO> chatMsgVOList) {
        layoutInflater = LayoutInflater.from(context);
        this.chatMsgVOList = chatMsgVOList;
    }

    @Override
    public int getCount() {
        if (chatMsgVOList == null) {
            return 0;
        }
        return chatMsgVOList.size();
    }

    @Override
    public ChatMsg getItem(int position) {
        return chatMsgVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chatMsgVOList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgVO chatMsgVO = chatMsgVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_chat, parent, false);
        LinearLayout linearLayout;
        ViewHolder viewHolder = new ViewHolder();
        if (!chatMsgVO.getIsSelf()) {
            linearLayout = convertView.findViewById(R.id.right_bubble);
            linearLayout.setVisibility(View.GONE);
            viewHolder.icon = convertView.findViewById(R.id.left_image);
            viewHolder.icon.setImageResource(R.mipmap.app_icon);
            viewHolder.text = convertView.findViewById(R.id.left_content);
            viewHolder.text.setText(chatMsgVO.getText());
            viewHolder.nick = convertView.findViewById(R.id.left_name);
            viewHolder.nick.setText("这是一个昵称" + chatMsgVO.getId());
        } else {
            linearLayout = convertView.findViewById(R.id.left_bubble);
            linearLayout.setVisibility(View.GONE);
            viewHolder.icon = convertView.findViewById(R.id.right_image);
            viewHolder.icon.setImageResource(R.mipmap.app_icon);
            viewHolder.text = convertView.findViewById(R.id.right_content);
            viewHolder.text.setText(chatMsgVO.getText());
            viewHolder.nick = convertView.findViewById(R.id.right_name);
            viewHolder.nick.setText("这是一个昵称" + chatMsgVO.getId());
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView nick;
        TextView text;
    }
}
