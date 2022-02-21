package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.utils.PersonalMsgUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/14 16:34
 */
public class PersonalMsgListAdapter extends BaseAdapter {
    private List<PersonalMsgVO> itemList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public PersonalMsgListAdapter(Context context, List<PersonalMsgVO> personalMsgVOList) {
        layoutInflater = LayoutInflater.from(context);
        itemList = personalMsgVOList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonalMsgVO personalMsgVO = itemList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_personal_msg_list, parent, false);
        //TODO:待完成:消息对应
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.nick.setText(personalMsgVO.getOperatorNick());
        viewHolder.note.setText("留言:" + personalMsgVO.getNote());
        viewHolder.object.setText("来源:" + personalMsgVO.getObjectNick());
        viewHolder.status.setText(PersonalMsgUtil.STATUS_LIST[personalMsgVO.getStatus()].getName());
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_personal_msg_list_icon)
        ImageView imageView;
        @BindView(R.id.listview_personal_msg_list_nick)
        TextView nick;
        @BindView(R.id.listview_chat_file_owner_nick)
        TextView note;
        @BindView(R.id.listview_personal_msg_list_object)
        TextView object;
        @BindView(R.id.listview_personal_msg_list_status)
        TextView status;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
