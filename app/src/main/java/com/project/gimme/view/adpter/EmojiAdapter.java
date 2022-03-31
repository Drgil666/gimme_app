package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.gimme.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Gilbert
 * @date 2022/3/31 17:17
 */
public class EmojiAdapter extends BaseAdapter {
    private List<String> emojiList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;

    public EmojiAdapter(Context context) {
        mContext = context;
        emojiList.add("\uD83D\uDE00");
        emojiList.add("\uD83D\uDE09");
        emojiList.add("\uD83D\uDE01");
        emojiList.add("\uD83D\uDE06");
        emojiList.add("\uD83D\uDE05");
        emojiList.add("\uD83D\uDE02");
        emojiList.add("\uD83D\uDE0A");
        //TODO:表情需要导入
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (emojiList == null) {
            return 0;
        }
        return emojiList.size();
    }

    @Override
    public String getItem(int position) {
        if (position >= 0 && position < emojiList.size()) {
            return emojiList.get(position);
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
        String emoji = emojiList.get(position);
        convertView = layoutInflater.inflate(R.layout.gridview_emoji, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.icon.setText(emoji);
        return convertView;
    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.grid_emoji_icon)
        TextView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
