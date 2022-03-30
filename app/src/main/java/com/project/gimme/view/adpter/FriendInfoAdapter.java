package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.view.activity.ParamActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class FriendInfoAdapter extends BaseAdapter {
    private List<UserVoParamItem> itemList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    public FriendInfoAdapter(Context context, List<UserVoParamItem> itemList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public UserVoParamItem getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listview_friend_info_list, parent, false);
        UserVoParamItem userVoParamItem = itemList.get(position);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.param.setText(userVoParamItem.getParamName());
        viewHolder.text.setText(userVoParamItem.getParamValue());
        if (userVoParamItem.getIsArrow()) {
            viewHolder.icon.setVisibility(View.VISIBLE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(BundleUtil.PARAM_NAME_ATTRIBUTE, userVoParamItem.getParamName());
                    bundle.putString(BundleUtil.PARAM_VALUE_ATTRIBUTE, userVoParamItem.getParamValue());
                    Intent intent = new Intent(context, ParamActivity.class).putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_friend_info_list_param)
        TextView param;
        @BindView(R.id.listview_friend_info_list_value)
        TextView text;
        @BindView(R.id.listview_friend_info_list_icon)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
