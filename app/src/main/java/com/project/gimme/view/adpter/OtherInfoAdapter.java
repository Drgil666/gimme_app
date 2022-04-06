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

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class OtherInfoAdapter extends BaseAdapter {
    private List<UserVO> userVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public OtherInfoAdapter(Context context, List<UserVO> userVOList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.userVOList = userVOList;
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
        UserVO userVO = userVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.gridview_other_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        if (userVO.getNote() != null) {
            viewHolder.nick.setText(userVO.getNote());
        } else {
            viewHolder.nick.setText(userVO.getNick());
        }
        if (userVO.getId() == -1) {
            Glide.with(context)
                    .load(R.mipmap.add_plus)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                    .into(viewHolder.icon);
        } else {
            Glide.with(context)
                    .load(GimmeApplication.getImageUrl(userVO.getAvatar()))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                    .into(viewHolder.icon);
        }
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.gridview_other_info_icon)
        ImageView icon;
        @BindView(R.id.gridview_other_info_nick)
        TextView nick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
