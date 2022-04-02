package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.pojo.vo.ContactVO;
import com.project.gimme.pojo.vo.RefreshVO;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.activity.FriendListActivity;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author Gilbert
 * @date 2022/3/31 12:52
 */
public class ContactVoAdapter extends BaseAdapter {
    private List<ContactVO> contactVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;
    private Integer contactType;
    private Integer type;
    private Integer objectId;
    private Integer chatMsgId;
    private Handler handler = new Handler();

    public ContactVoAdapter(Context context, List<ContactVO> contactVOList, Integer contactType, Integer chatMsgId) {
        this.mContext = context;
        this.contactVOList = contactVOList;
        layoutInflater = LayoutInflater.from(context);
        this.contactType = contactType;
        this.chatMsgId = chatMsgId;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (contactVOList == null) {
            return 0;
        }
        return contactVOList.size();
    }

    @Override
    public ContactVO getItem(int position) {
        if (position >= 0 && position < contactVOList.size()) {
            return contactVOList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (position >= 0 && position < contactVOList.size()) {
            return contactVOList.get(position).getObjectId();
        }
        return GimmeApplication.TYPE_ERROR;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactVO contactVO = contactVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_friend_list_contact_vo_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        Glide.with(mContext).load(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(viewHolder.icon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactType.equals(ContactsUtil.ContactType.TYPE_CREATE_CONTACT.getCode())) {
                    viewHolder.checkBox.setChecked(!viewHolder.checkBox.isChecked());
                } else {
//                    transmitMessage();
                }
            }
        });
        viewHolder.nick.setText(contactVO.getNick());
        if (contactType.equals(ContactsUtil.ContactType.TYPE_CREATE_CONTACT.getCode())) {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                    if (isChecked) {
                        FriendListActivity.addItem(contactVO.getObjectId());
                    } else {
                        FriendListActivity.deleteItem(contactVO.getObjectId());
                    }
                }
            });
        } else {
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void transmitMessage(Integer chatMsgId, Integer type, Integer objectId) {
        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setChatType(ChatMsgUtil.CHARACTER_LIST[type].getName());
        refreshVO.setObjectId(objectId);
        refreshVO.setChatMsgId(chatMsgId);
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChatMsgController.transmitChatMsg(refreshVO);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) mContext).finish();
                        XToastUtils.toast("转发成功！");
                    }
                });
            }
        }).start();
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_friend_list_contact_vo_list_image)
        ImageView icon;
        @BindView(R.id.listview_friend_list_contact_vo_list_nick)
        TextView nick;
        @BindView(R.id.listview_friend_list_contact_vo_list_checkbox)
        SmoothCheckBox checkBox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
