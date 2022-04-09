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
import com.project.gimme.pojo.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherMemberListAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    private List<UserVO> userVOList = new ArrayList<>();
    private Context context;

    public OtherMemberListAdapter(Context context, List<UserVO> personalMsgVOList) {
        layoutInflater = LayoutInflater.from(context);
        userVOList = personalMsgVOList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (userVOList == null) {
            return 0;
        }
        return userVOList.size();
    }

    @Override
    public UserVO getItem(int position) {
        return userVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userVOList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserVO userVO = getItem(position);
        convertView = layoutInflater.inflate(R.layout.listview_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        Glide.with(context)
                .load(GimmeApplication.getImageUrl(userVO.getAvatar()))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(viewHolder.icon);
        viewHolder.nick.setText(userVO.getOtherNick());
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_search_icon)
        ImageView icon;
        @BindView(R.id.listview_search_nick)
        TextView nick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
