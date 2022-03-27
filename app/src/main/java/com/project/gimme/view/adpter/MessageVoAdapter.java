package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.pojo.vo.RefreshVO;
import com.project.gimme.utils.NumberUtil;
import com.squareup.picasso.Picasso;
import com.xuexiang.xui.widget.textview.badge.Badge;
import com.xuexiang.xui.widget.textview.badge.BadgeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 * @date 2022/1/11 14:58
 */
public class MessageVoAdapter extends BaseAdapter {
    private List<MessageVO> messageVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public MessageVoAdapter(Context context, List<MessageVO> messageVOList) {
        layoutInflater = LayoutInflater.from(context);
        this.messageVOList = messageVOList;
        this.mContext = context;
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
        Picasso.with(convertView.getContext()).load(R.mipmap.default_icon).into(viewHolder.avatar);
        viewHolder.nick.setText(messageVO.getNick());
        viewHolder.text.setText(messageVO.getText());
        viewHolder.timestamp.setText(NumberUtil.changeToHourAndMinute(messageVO.getTimestamp()));
        if (messageVO.getNewMessageCount() != 0) {
            Badge badge = new BadgeView(mContext)
                    .bindTarget(viewHolder.newMessageCountBackGround)
                    .setBadgeNumber(messageVO.getNewMessageCount());
            badge.setOnDragStateChangedListener((dragState, badge1, targetView) -> {
                switch (dragState) {
                    case Badge.OnDragStateChangedListener.STATE_START:
                        break;
                    case Badge.OnDragStateChangedListener.STATE_DRAGGING:
                        break;
                    case Badge.OnDragStateChangedListener.STATE_DRAGGING_OUT_OF_RANGE:
                        break;
                    case Badge.OnDragStateChangedListener.STATE_SUCCEED: {
                        refresh(messageVO.getType(), messageVO.getObjectId());
                        //刷新状态
                    }
                    break;
                    case Badge.OnDragStateChangedListener.STATE_CANCELED:
                        break;
                    default:
                        break;
                }
            });
        } else {
            viewHolder.newMessageCountBackGround.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private void refresh(String type, Integer objectId) {
        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setChatType(type);
        refreshVO.setObjectId(objectId);
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChatMsgController.refresh(refreshVO);
            }
        }).start();
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
        //@BindView(R.id.listview_message_vo_new_message_count)
        //TextView newMessageCount;
        @BindView(R.id.listview_message_vo_new_message_count_background)
        RelativeLayout newMessageCountBackGround;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
