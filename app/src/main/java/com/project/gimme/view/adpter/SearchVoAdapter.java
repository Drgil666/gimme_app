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
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.pojo.vo.SearchVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchVoAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    private List<SearchVO> itemList = new ArrayList<>();
    private Context context;

    public SearchVoAdapter(Context context, List<SearchVO> personalMsgVOList) {
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
    public SearchVO getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getObjectId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchVO searchVO = getItem(position);
        convertView = layoutInflater.inflate(R.layout.listview_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        Glide.with(context)
                .load(GimmeApplication.REMOTE_URL + "/api/chat/file/download/" + searchVO.getAvatar())
                .into(viewHolder.icon);
        viewHolder.nick.setText(searchVO.getObjectNick() + "(" + searchVO.getObjectId() + ")");
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
