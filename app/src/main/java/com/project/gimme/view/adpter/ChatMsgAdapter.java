package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.utils.NumberUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class ChatMsgAdapter extends BaseAdapter {
    private List<ChatMsgVO> chatMsgVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public ChatMsgAdapter(Context context, List<ChatMsgVO> chatMsgVOList) {
        this.mContext = context;
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
    public boolean isEnabled(int position) {
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
        convertView = layoutInflater.inflate(R.layout.listview_chat_msg, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        Glide.with(mContext)
                .load(GimmeApplication.getImageUrl(chatMsgVO.getAvatar()))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(viewHolder.icon);
        viewHolder.nick.setText(chatMsgVO.getOwnerNick());
        viewHolder.timestamp.setText(NumberUtil.changeToHourAndMinute(chatMsgVO.getTimeStamp()));
        viewHolder.text.setText(chatMsgVO.getText());
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_chat_msg_image)
        ImageView icon;
        @BindView(R.id.listview_chat_msg_nick)
        TextView nick;
        @BindView(R.id.listview_chat_msg_text)
        TextView text;
        @BindView(R.id.listview_chat_msg_timestamp)
        TextView timestamp;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
