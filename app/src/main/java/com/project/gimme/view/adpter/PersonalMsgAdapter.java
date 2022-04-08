package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.gimme.R;
import com.xuexiang.xui.widget.textview.badge.Badge;
import com.xuexiang.xui.widget.textview.badge.BadgeView;

import java.util.ArrayList;
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
    private Context mContext;
    private List<Long> cntList = new ArrayList<>();

    public PersonalMsgAdapter(Context context, List<String> list, List<Long> cntList) {
        layoutInflater = LayoutInflater.from(context);
        itemList = list;
        this.mContext = context;
        this.cntList = cntList;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String text = itemList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_personal_msg, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.text.setText(text);
        Glide.with(mContext)
                .load(R.mipmap.right_arrow)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(viewHolder.icon);
        if (cntList.get(position) != null && cntList.get(position) != 0) {
            Badge badge = new BadgeView(mContext)
                    .bindTarget(viewHolder.count)
                    .setBadgeTextSize(18, true)
                    .setBadgeNumber(Math.toIntExact(cntList.get(position)));
            badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {

                }
            });
        }

        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_personal_message_text)
        TextView text;
        @BindView(R.id.listview_personal_message_icon)
        ImageView icon;
        @BindView(R.id.listview_personal_message_count)
        RelativeLayout count;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
