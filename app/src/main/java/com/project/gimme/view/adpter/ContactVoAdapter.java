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
import com.project.gimme.pojo.vo.ContactVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Gilbert
 * @date 2022/3/31 12:52
 */
public class ContactVoAdapter extends BaseAdapter {
    private List<ContactVO> contactVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public ContactVoAdapter(Context context, List<ContactVO> contactVOList) {
        this.mContext = context;
        this.contactVOList = contactVOList;
        layoutInflater = LayoutInflater.from(context);
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
        viewHolder.nick.setText(contactVO.getNick());
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_friend_list_contact_vo_list_image)
        ImageView icon;
        @BindView(R.id.listview_friend_list_contact_vo_list_nick)
        TextView nick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
