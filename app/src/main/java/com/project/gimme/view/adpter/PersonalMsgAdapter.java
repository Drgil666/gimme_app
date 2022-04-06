package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.gimme.R;
import com.xuexiang.xui.widget.textview.badge.Badge;
import com.xuexiang.xui.widget.textview.badge.BadgeView;

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

    public PersonalMsgAdapter(Context context, List<String> list) {
        layoutInflater = LayoutInflater.from(context);
        itemList = list;
        this.mContext = context;
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
        viewHolder.icon.setImageResource(R.mipmap.right_arrow);
        Badge badge = new BadgeView(mContext)
                .bindTarget(viewHolder.count)
                .setBadgeTextSize(18, true)
                .setBadgeNumber(15);
        badge.setOnDragStateChangedListener((dragState, badge1, targetView) -> {
            switch (dragState) {
                case Badge.OnDragStateChangedListener.STATE_START:
                    break;
                case Badge.OnDragStateChangedListener.STATE_DRAGGING:
                    break;
                case Badge.OnDragStateChangedListener.STATE_DRAGGING_OUT_OF_RANGE:
                    break;
                case Badge.OnDragStateChangedListener.STATE_SUCCEED: {
                    //TODO:刷新状态
                    break;
                }
                case Badge.OnDragStateChangedListener.STATE_CANCELED:
                    break;
                default:
                    break;
            }
        });
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
