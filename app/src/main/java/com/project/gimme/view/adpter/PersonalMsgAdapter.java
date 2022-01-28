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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/12 15:46
 */
public class PersonalMsgAdapter extends BaseAdapter {
    private List<String> itemList;
    private LayoutInflater layoutInflater;

    public PersonalMsgAdapter(Context context, List<String> list) {
        layoutInflater = LayoutInflater.from(context);
        itemList = list;
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public String getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String text = itemList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_personal_msg, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.text.setText(text);
        viewHolder.icon.setImageResource(R.mipmap.app_icon);
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    private static class ViewHolder {
        @BindView(R.id.listview_personal_message_text)
        TextView text;
        @BindView(R.id.listview_personal_message_icon)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
