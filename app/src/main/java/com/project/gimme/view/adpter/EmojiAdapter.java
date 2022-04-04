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
import java.util.Arrays;
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
        getEmojiList();
        layoutInflater = LayoutInflater.from(context);
    }

    private void getEmojiList() {
        /// region 表情包列表
        emojiList = Arrays.asList("\uD83D\uDE00", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE03", "\uD83D\uDE04", "\uD83D\uDE05", "\uD83D\uDE06", "\uD83D\uDE07", "\uD83D\uDE08", "\uD83D\uDE09", "\uD83D\uDE0A", "\uD83D\uDE0B", "\uD83D\uDE0C", "\uD83D\uDE0D", "\uD83D\uDE0E", "\uD83D\uDE0F", "\uD83D\uDE10", "\uD83D\uDE11", "\uD83D\uDE12", "\uD83D\uDE13", "\uD83D\uDE14", "\uD83D\uDE15", "\uD83D\uDE16", "\uD83D\uDE17", "\uD83D\uDE18", "\uD83D\uDE19", "\uD83D\uDE1A", "\uD83D\uDE1B", "\uD83D\uDE1C", "\uD83D\uDE1D", "\uD83D\uDE1E", "\uD83D\uDE1F", "\uD83D\uDE20", "\uD83D\uDE21", "\uD83D\uDE22", "\uD83D\uDE23", "\uD83D\uDE24", "\uD83D\uDE25", "\uD83D\uDE26", "\uD83D\uDE27", "\uD83D\uDE28", "\uD83D\uDE29", "\uD83D\uDE2A", "\uD83D\uDE2B", "\uD83D\uDE2C", "\uD83D\uDE2D", "\uD83D\uDE2E", "\uD83D\uDE2F", "\uD83D\uDE30", "\uD83D\uDE31", "\uD83D\uDE32", "\uD83D\uDE33", "\uD83D\uDE34", "\uD83D\uDE35", "\uD83D\uDE36", "\uD83D\uDE37", "\uD83D\uDE38", "\uD83D\uDE39", "\uD83D\uDE3A", "\uD83D\uDE3B", "\uD83D\uDE3C", "\uD83D\uDE3D", "\uD83D\uDE3E", "\uD83D\uDE3F", "\uD83D\uDE40", "\uD83D\uDE41", "\uD83D\uDE42", "\uD83D\uDE43", "\uD83D\uDE44", "\uD83D\uDE45", "\uD83D\uDE46", "\uD83D\uDE47", "\uD83D\uDE48", "\uD83D\uDE49", "\uD83D\uDE4A", "\uD83D\uDE4B", "\uD83D\uDE4C", "\uD83D\uDE4D", "\uD83D\uDE4E", "\uD83D\uDE4F", "\uD83C\uDF00", "\uD83C\uDF01", "\uD83C\uDF02", "\uD83C\uDF03", "\uD83C\uDF04", "\uD83C\uDF05", "\uD83C\uDF06", "\uD83C\uDF07", "\uD83C\uDF08", "\uD83C\uDF09", "\uD83C\uDF0A", "\uD83C\uDF0B", "\uD83C\uDF0C", "\uD83C\uDF0D", "\uD83C\uDF0E", "\uD83C\uDF0F", "\uD83C\uDF10", "\uD83C\uDF11", "\uD83C\uDF12", "\uD83C\uDF13", "\uD83C\uDF14", "\uD83C\uDF15", "\uD83C\uDF16", "\uD83C\uDF17", "\uD83C\uDF18", "\uD83C\uDF19", "\uD83C\uDF1A", "\uD83C\uDF1B", "\uD83C\uDF1C", "\uD83C\uDF1D", "\uD83C\uDF1E", "\uD83C\uDF1F", "\uD83C\uDF20", "\uD83C\uDF21", "\uD83C\uDF24", "\uD83C\uDF25", "\uD83C\uDF26", "\uD83C\uDF27", "\uD83C\uDF28", "\uD83C\uDF29", "\uD83C\uDF2A", "\uD83C\uDF2B", "\uD83C\uDF2C", "\uD83C\uDF2D", "\uD83C\uDF2E", "\uD83C\uDF2F", "\uD83C\uDF30", "\uD83C\uDF31", "\uD83C\uDF32", "\uD83C\uDF33", "\uD83C\uDF34", "\uD83C\uDF35", "\uD83C\uDF36", "\uD83C\uDF37", "\uD83C\uDF38", "\uD83C\uDF39", "\uD83C\uDF3A", "\uD83C\uDF3B", "\uD83C\uDF3C", "\uD83C\uDF3D", "\uD83C\uDF3E", "\uD83C\uDF3F", "\uD83C\uDF40", "\uD83C\uDF41", "\uD83C\uDF42", "\uD83C\uDF43", "\uD83C\uDF44", "\uD83C\uDF45", "\uD83C\uDF46", "\uD83C\uDF47", "\uD83C\uDF48", "\uD83C\uDF49", "\uD83C\uDF4A", "\uD83C\uDF4B", "\uD83C\uDF4C", "\uD83C\uDF4D", "\uD83C\uDF4E", "\uD83C\uDF4F", "\uD83C\uDF50", "\uD83C\uDF51", "\uD83C\uDF52", "\uD83C\uDF53", "\uD83C\uDF54", "\uD83C\uDF55", "\uD83C\uDF56", "\uD83C\uDF57", "\uD83C\uDF58", "\uD83C\uDF59", "\uD83C\uDF5A", "\uD83C\uDF5B", "\uD83C\uDF5C", "\uD83C\uDF5D", "\uD83C\uDF5E", "\uD83C\uDF5F", "\uD83C\uDF60", "\uD83C\uDF61", "\uD83C\uDF62", "\uD83C\uDF63", "\uD83C\uDF64", "\uD83C\uDF65", "\uD83C\uDF66", "\uD83C\uDF67", "\uD83C\uDF68", "\uD83C\uDF69", "\uD83C\uDF6A", "\uD83C\uDF6B", "\uD83C\uDF6C", "\uD83C\uDF6D", "\uD83C\uDF6E", "\uD83C\uDF6F", "\uD83C\uDF70", "\uD83C\uDF71", "\uD83C\uDF72", "\uD83C\uDF73", "\uD83C\uDF74", "\uD83C\uDF75", "\uD83C\uDF76", "\uD83C\uDF77", "\uD83C\uDF78", "\uD83C\uDF79", "\uD83C\uDF7A", "\uD83C\uDF7B", "\uD83C\uDF7C", "\uD83C\uDF7D", "\uD83C\uDF7E", "\uD83C\uDF7F", "\uD83C\uDF80", "\uD83C\uDF81", "\uD83C\uDF82", "\uD83C\uDF83", "\uD83C\uDF84", "\uD83C\uDF85", "\uD83C\uDF86", "\uD83C\uDF87", "\uD83C\uDF88", "\uD83C\uDF89", "\uD83C\uDF8A", "\uD83C\uDF8B", "\uD83C\uDF8C", "\uD83C\uDF8D", "\uD83C\uDF8E", "\uD83C\uDF8F", "\uD83C\uDF90", "\uD83C\uDF91", "\uD83C\uDF92", "\uD83C\uDF93", "\uD83C\uDF96", "\uD83C\uDF97", "\uD83C\uDF99", "\uD83C\uDF9A", "\uD83C\uDF9B", "\uD83C\uDF9E", "\uD83C\uDF9F", "\uD83C\uDFA0", "\uD83C\uDFA1", "\uD83C\uDFA2", "\uD83C\uDFA3", "\uD83C\uDFA4", "\uD83C\uDFA5", "\uD83C\uDFA6", "\uD83C\uDFA7", "\uD83C\uDFA8", "\uD83C\uDFA9", "\uD83C\uDFAA", "\uD83C\uDFAB", "\uD83C\uDFAC", "\uD83C\uDFAD", "\uD83C\uDFAE", "\uD83C\uDFAF", "\uD83C\uDFB0", "\uD83C\uDFB1", "\uD83C\uDFB2", "\uD83C\uDFB3", "\uD83C\uDFB4", "\uD83C\uDFB5", "\uD83C\uDFB6", "\uD83C\uDFB7", "\uD83C\uDFB8", "\uD83C\uDFB9", "\uD83C\uDFBA", "\uD83C\uDFBB", "\uD83C\uDFBC", "\uD83C\uDFBD", "\uD83C\uDFBE", "\uD83C\uDFBF", "\uD83C\uDFC0", "\uD83C\uDFC1", "\uD83C\uDFC2", "\uD83C\uDFC3", "\uD83C\uDFC4", "\uD83C\uDFC5", "\uD83C\uDFC6", "\uD83C\uDFC7", "\uD83C\uDFC8", "\uD83C\uDFC9", "\uD83C\uDFCA", "\uD83C\uDFCB", "\uD83C\uDFCC", "\uD83C\uDFCD", "\uD83C\uDFCE", "\uD83C\uDFCF", "\uD83C\uDFD0", "\uD83C\uDFD1", "\uD83C\uDFD2", "\uD83C\uDFD3", "\uD83C\uDFD4", "\uD83C\uDFD5", "\uD83C\uDFD6", "\uD83C\uDFD7", "\uD83C\uDFD8", "\uD83C\uDFD9", "\uD83C\uDFDA", "\uD83C\uDFDB", "\uD83C\uDFDC", "\uD83C\uDFDD", "\uD83C\uDFDE", "\uD83C\uDFDF", "\uD83C\uDFE0", "\uD83C\uDFE1", "\uD83C\uDFE2", "\uD83C\uDFE3", "\uD83C\uDFE4", "\uD83C\uDFE5", "\uD83C\uDFE6", "\uD83C\uDFE7", "\uD83C\uDFE8", "\uD83C\uDFE9", "\uD83C\uDFEA", "\uD83C\uDFEB", "\uD83C\uDFEC", "\uD83C\uDFED", "\uD83C\uDFEE", "\uD83C\uDFEF", "\uD83C\uDFF0", "\uD83C\uDFF3", "\uD83C\uDFF4", "\uD83C\uDFF5", "\uD83C\uDFF7", "\uD83C\uDFF8", "\uD83C\uDFF9", "\uD83C\uDFFA");
        /// endregion
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
        @BindView(R.id.gridview_emoji_icon)
        TextView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
