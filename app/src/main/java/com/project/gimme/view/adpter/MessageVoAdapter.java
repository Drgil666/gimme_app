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
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/11 14:58
 */
public class MessageVoAdapter extends BaseAdapter {
    private List<MessageVO> messageVOList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public MessageVoAdapter(Context context, List<MessageVO> messageVOList) {
        this.messageVOList = messageVOList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (messageVOList == null) {
            return 0;
        }
        return messageVOList.size();
    }

    @Override
    public MessageVO getItem(int position) {
        if (position >= 0 && position < messageVOList.size()) {
            return messageVOList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (position >= 0 && position < messageVOList.size()) {
            return messageVOList.get(position).getId();
        }
        return GimmeApplication.TYPE_ERROR;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageVO messageVO = messageVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_message_vo, parent, false);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.avatar = convertView.findViewById(R.id.listview_message_vo_image);
        viewHolder.nick = convertView.findViewById(R.id.listview_message_vo_nick);
        viewHolder.text = convertView.findViewById(R.id.listview_message_vo_text);
        viewHolder.timestamp = convertView.findViewById(R.id.listview_message_vo_timestamp);

//        viewHolder.avatar
        viewHolder.nick.setText(messageVO.getNick());
        viewHolder.text.setText(messageVO.getText());
        viewHolder.timestamp.setText(TimeUtil.changeToHourAndMinute(messageVO.getTimestamp()));
        return convertView;
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView nick;
        TextView text;
        TextView timestamp;
    }
}
