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
import com.project.gimme.pojo.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:43
 */
public class FriendGroupAdapter extends BaseAdapter {
    private List<Group> groupList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;
    public FriendGroupAdapter(Context context, List<Group> groupList) {
        layoutInflater = LayoutInflater.from(context);
        this.groupList = groupList;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (groupList == null) {
            return 0;
        }
        return groupList.size();
    }

    @Override
    public Group getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return groupList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Group group = groupList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_friend_list_group_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.text.setText(group.getNick());
        Glide.with(mContext)
                .load(GimmeApplication.getImageUrl(group.getAvatar()))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(viewHolder.icon);
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_friend_list_group_list_nick)
        TextView text;
        @BindView(R.id.listview_friend_list_group_list_image)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
