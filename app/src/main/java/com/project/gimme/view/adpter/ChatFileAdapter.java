package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.pojo.vo.ChatFileVO;
import com.project.gimme.utils.NumberUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/11 14:58
 */
public class ChatFileAdapter extends BaseAdapter {
    private List<ChatFileVO> chatFileVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ChatFileAdapter(Context context, List<ChatFileVO> chatFileVOList) {
        this.chatFileVOList = chatFileVOList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (chatFileVOList == null) {
            return 0;
        }
        return chatFileVOList.size();
    }

    @Override
    public ChatFileVO getItem(int position) {
        if (position >= 0 && position < chatFileVOList.size()) {
            return chatFileVOList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (position >= 0 && position < chatFileVOList.size()) {
            return chatFileVOList.get(position).getId();
        }
        return GimmeApplication.TYPE_ERROR;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatFileVO chatFileVO = chatFileVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_chat_file, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.nick = convertView.findViewById(R.id.listview_chat_file_nick);
        viewHolder.nick.setText(chatFileVO.getFilename());
        viewHolder.ownerNick = convertView.findViewById(R.id.listview_chat_file_owner);
        viewHolder.ownerNick.setText("来自" + chatFileVO.getOwnerNick());
        viewHolder.timestamp = convertView.findViewById(R.id.listview_chat_file_owner_timestamp);
        viewHolder.timestamp.setText(NumberUtil.changeToYearAndMonthAndDay(chatFileVO.getTimestamp()));
        viewHolder.size = convertView.findViewById(R.id.listview_chat_file_size);
        viewHolder.size.setText(NumberUtil.changeToFileSize(chatFileVO.getSize()));
        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView nick;
        TextView ownerNick;
        TextView timestamp;
        TextView size;
    }
}
