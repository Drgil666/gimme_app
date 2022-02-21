package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
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
import com.project.gimme.utils.NumberUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/11 14:58
 */
public class MessageVoAdapter extends BaseAdapter {
    private List<MessageVO> messageVOList;
    private LayoutInflater layoutInflater;

    public MessageVoAdapter(Context context, List<MessageVO> messageVOList) {
        layoutInflater = LayoutInflater.from(context);
        this.messageVOList = messageVOList;
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
            return messageVOList.get(position).getObjectId();
        }
        return GimmeApplication.TYPE_ERROR;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageVO messageVO = messageVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_message_vo, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.nick.setText(messageVO.getNick());
        viewHolder.text.setText(messageVO.getText());
        viewHolder.timestamp.setText(NumberUtil.changeToHourAndMinute(messageVO.getTimestamp()));
        if (messageVO.getNewMessageCount() != 0) {
            viewHolder.newMessageCount.setVisibility(View.VISIBLE);
            viewHolder.newMessageCount.setText(messageVO.getNewMessageCount().toString());
        } else {
            viewHolder.newMessageCount.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_message_vo_image)
        ImageView avatar;
        @BindView(R.id.listview_message_vo_nick)
        TextView nick;
        @BindView(R.id.listview_message_vo_text)
        TextView text;
        @BindView(R.id.listview_message_vo_timestamp)
        TextView timestamp;
        @BindView(R.id.listview_message_vo_new_message_count)
        TextView newMessageCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
