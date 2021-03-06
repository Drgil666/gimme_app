package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.PersonalMsgController;
import com.project.gimme.pojo.PersonalMsg;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.PersonalMsgUtil;
import com.project.gimme.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 * @date 2022/1/14 16:34
 */
public class PersonalMsgListAdapter extends BaseAdapter {
    private List<PersonalMsgVO> itemList = new ArrayList<>();
    LayoutInflater layoutInflater;
    private Context mContext;

    public PersonalMsgListAdapter(Context context, List<PersonalMsgVO> personalMsgVOList) {
        layoutInflater = LayoutInflater.from(context);
        itemList = personalMsgVOList;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public PersonalMsgVO getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getId();
    }

    private void updatePersonalMsg(PersonalMsgVO personalMsgVO) {
        PersonalMsg personalMsg = personalMsgVO;
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                PersonalMsgController.updatePersonalMsg(personalMsg);
            }
        }).start();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonalMsgVO personalMsgVO = itemList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_personal_msg_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        Glide.with(mContext)
                .load(GimmeApplication.getImageUrl(personalMsgVO.getAvatar()))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(viewHolder.icon);
        if (personalMsgVO.getObjectType().equals(ChatMsgUtil.Character.TYPE_FRIEND.getName())) {
            if (personalMsgVO.getType().equals(PersonalMsgUtil.FriendPersonalMsg.TYPE_INSERT_FRIEND.getName())) {
                //??????????????????
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), 3) + " ?????????????????????");
                viewHolder.note.setText("??????:" + personalMsgVO.getNote());
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.FriendPersonalMsg.TYPE_DELETE_FRIEND.getName())) {
                //??????????????????
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), 5) + " ???????????????????????????");
                viewHolder.note.setText(null);
            }
        } else if (personalMsgVO.getObjectType().equals(ChatMsgUtil.Character.TYPE_GROUP.getName())) {
            //????????????
            if (personalMsgVO.getType().equals(PersonalMsgUtil.GroupPersonalMsg.TYPE_INSERT_GROUP.getName())) {
                viewHolder.nick.setText("?????????????????? " + TextUtil.ellipsisText(personalMsgVO.getOperatorNick(), 10));
                viewHolder.note.setText(null);
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.GroupPersonalMsg.TYPE_DELETE_GROUP.getName())) {
                viewHolder.nick.setText("???????????? " + TextUtil.ellipsisText(personalMsgVO.getOperatorNick(), 10));
                viewHolder.note.setText(null);
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.GroupPersonalMsg.TYPE_INSERT_GROUP_MEMBER.getName())) {
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), null));
                viewHolder.note.setText("???????????? " + TextUtil.ellipsisText(personalMsgVO.getOperatorNick(), 6));
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.GroupPersonalMsg.TYPE_UPDATE_GROUP_MEMBER.getName())) {
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), 5) + " ?????????????????????");
                viewHolder.note.setText("??????: " + TextUtil.ellipsisText(personalMsgVO.getObjectNick(), null));
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.GroupPersonalMsg.TYPE_DELETE_GROUP_MEMBER.getName())) {
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), 10) + " ???????????????");
                viewHolder.note.setText(null);
            }
        } else if (personalMsgVO.getObjectType().equals(ChatMsgUtil.Character.TYPE_CHANNEL.getName())) {
            if (personalMsgVO.getType().equals(PersonalMsgUtil.ChannelPersonalMsg.TYPE_INSERT_CHANNEL.getName())) {
                viewHolder.nick.setText("?????????????????? " + TextUtil.ellipsisText(personalMsgVO.getOperatorNick(), 10));
                viewHolder.note.setText(null);
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.ChannelPersonalMsg.TYPE_DELETE_CHANNEL.getName())) {
                viewHolder.nick.setText("?????????????????? " + TextUtil.ellipsisText(personalMsgVO.getOperatorNick(), 10));
                viewHolder.note.setText(null);
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.ChannelPersonalMsg.TYPE_INSERT_CHANNEL_MEMBER.getName())) {
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), null));
                viewHolder.note.setText("???????????? " + TextUtil.ellipsisText(personalMsgVO.getOperatorNick(), 6));
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.ChannelPersonalMsg.TYPE_UPDATE_CHANNEL_MEMBER.getName())) {
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), 5) + " ?????????????????????");
                viewHolder.note.setText("??????: " + TextUtil.ellipsisText(personalMsgVO.getObjectNick(), null));
            } else if (personalMsgVO.getType().equals(PersonalMsgUtil.ChannelPersonalMsg.TYPE_DELETE_CHANNEL_MEMBER.getName())) {
                viewHolder.nick.setText(TextUtil.ellipsisText(personalMsgVO.getOwnerNick(), 10) + " ???????????????");
                viewHolder.note.setText(null);
            }
        }
        if (!personalMsgVO.getStatus().equals(PersonalMsgUtil.Status.TYPE_CHOOSE.getCode())) {
            viewHolder.status.setText(PersonalMsgUtil.STATUS_LIST[personalMsgVO.getStatus()].getName());
            viewHolder.status.setVisibility(View.VISIBLE);
            viewHolder.chooseLayout.setVisibility(View.GONE);
        } else {
            viewHolder.status.setVisibility(View.GONE);
            viewHolder.chooseLayout.setVisibility(View.VISIBLE);
            viewHolder.confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_ACCEPT.getCode());
                    viewHolder.chooseLayout.setVisibility(View.GONE);
                    viewHolder.status.setVisibility(View.VISIBLE);
                    viewHolder.status.setText(PersonalMsgUtil.STATUS_LIST[personalMsgVO.getStatus()].getName());
                    updatePersonalMsg(personalMsgVO);
                }
            });
            viewHolder.refuseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_REJECT.getCode());
                    viewHolder.chooseLayout.setVisibility(View.GONE);
                    viewHolder.status.setVisibility(View.VISIBLE);
                    viewHolder.status.setText(PersonalMsgUtil.STATUS_LIST[personalMsgVO.getStatus()].getName());
                    updatePersonalMsg(personalMsgVO);
                }
            });
        }
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_personal_msg_list_icon)
        ImageView icon;
        @BindView(R.id.listview_personal_msg_list_nick)
        TextView nick;
        @BindView(R.id.listview_chat_file_owner_nick)
        TextView note;
        @BindView(R.id.listview_personal_msg_list_status)
        TextView status;
        @BindView(R.id.listview_personal_msg_list_confirm_button)
        Button confirmButton;
        @BindView(R.id.listview_personal_msg_list_refuse_button)
        Button refuseButton;
        @BindView(R.id.listview_personal_msg_list_choose_layout)
        RelativeLayout chooseLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
