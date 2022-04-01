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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Data;

/**
 * @author Gilbert
 * @date 2022/3/31 17:17
 */
public class ExtraOptionAdapter extends BaseAdapter {
    private List<ExtraOptionItem> itemList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public ExtraOptionAdapter(Context context) {
        mContext = context;
        getItemList();
        layoutInflater = LayoutInflater.from(context);
    }

    @Data
    public static class ExtraOptionItem {
        private Integer imgId;
        private String text;

        ExtraOptionItem(Integer imgId, String text) {
            this.imgId = imgId;
            this.text = text;
        }
    }

    private void getItemList() {
        itemList = Arrays.asList(
                new ExtraOptionItem(R.mipmap.pic, "照片"),
                new ExtraOptionItem(R.mipmap.file, "文件"),
                new ExtraOptionItem(R.mipmap.todo, "待办"));
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public ExtraOptionItem getItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            return itemList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExtraOptionItem item = itemList.get(position);
        convertView = layoutInflater.inflate(R.layout.gridview_extra_option, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.icon.setImageResource(item.getImgId());
        viewHolder.text.setText(item.getText());
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.gridview_extra_option_icon)
        ImageView icon;
        @BindView(R.id.gridview_extra_option_text)
        TextView text;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
