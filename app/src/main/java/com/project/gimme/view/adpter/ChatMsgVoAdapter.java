package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.activity.InfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class ChatMsgVoAdapter extends BaseAdapter {
    private List<ChatMsgVO> chatMsgVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private Integer type;

    public ChatMsgVoAdapter(Context context, List<ChatMsgVO> chatMsgVOList, Integer type) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.chatMsgVOList = chatMsgVOList;
        this.type = type;
    }

    @Override
    public int getCount() {
        if (chatMsgVOList == null) {
            return 0;
        }
        return chatMsgVOList.size();
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public ChatMsg getItem(int position) {
        return chatMsgVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chatMsgVOList.get(position).getId();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgVO chatMsgVO = chatMsgVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_chat, parent, false);
        LinearLayout linearLayout;
        ViewHolder viewHolder = new ViewHolder(convertView);
        if (!chatMsgVO.getIsSelf()) {
            linearLayout = convertView.findViewById(R.id.right_bubble);
            linearLayout.setVisibility(View.GONE);
            viewHolder.icon = convertView.findViewById(R.id.left_image);
            viewHolder.icon.setImageResource(R.mipmap.app_icon);
            viewHolder.text = convertView.findViewById(R.id.left_content);
            viewHolder.text.setText(chatMsgVO.getText());
            viewHolder.nick = convertView.findViewById(R.id.left_name);
            viewHolder.nick.setText(chatMsgVO.getOwnerNick());
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_FRIEND.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_GROUP_MEMBER.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_CHANNEL_MEMBER.getCode());
                    }
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, chatMsgVO.getOwnerId());
                    Intent intent = new Intent(context, InfoActivity.class).putExtras(bundle);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                }
            });
        } else {
            linearLayout = convertView.findViewById(R.id.left_bubble);
            linearLayout.setVisibility(View.GONE);
            viewHolder.icon = convertView.findViewById(R.id.right_image);
            viewHolder.icon.setImageResource(R.mipmap.app_icon);
            viewHolder.text = convertView.findViewById(R.id.right_content);
            viewHolder.text.setText(chatMsgVO.getText());
            viewHolder.nick = convertView.findViewById(R.id.right_name);
            viewHolder.nick.setText(chatMsgVO.getOwnerNick());
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_SELF.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_GROUP_SELF.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_CHANNEL_SELF.getCode());
                    }
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, chatMsgVO.getOwnerId());
                    Intent intent = new Intent(context, InfoActivity.class).putExtras(bundle);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                }
            });
        }
        viewHolder.icon.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY); // 设置滤镜效果
            } else {
                ((ImageView) view).clearColorFilter(); // 清除滤镜效果
            }
            return false;//如果return true的话,onClick的事件就不会触发!
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView nick;
        TextView text;

        ViewHolder(View view) {
        }
    }
}