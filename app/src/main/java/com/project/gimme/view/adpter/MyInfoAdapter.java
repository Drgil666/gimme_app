package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.MyInfoVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/15 16:24
 */
public class MyInfoAdapter extends BaseAdapter {
    private List<MyInfoVO> itemList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public MyInfoAdapter(Context context, List<MyInfoVO> itemList) {
        layoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    //清除数据源
    public void clear() {
        itemList.clear();
    }

    //刷新数据源
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public MyInfoVO getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 位于哪一列
     *
     * @param pos 位置
     * @return 行号（1开始）
     */
    public long getColumn(int pos) {
        return pos % 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listview_my_info, parent, false);
        long column = getColumn(position);
        //TODO:有bug，待修正
        if (column == 1) {
            RelativeLayout relativeLayout = convertView.findViewById(R.id.listview_my_info_right_area);
            relativeLayout.setVisibility(View.GONE);
            relativeLayout = convertView.findViewById(R.id.listview_my_info_left_area);
            relativeLayout.setVisibility(View.GONE);
            return convertView;
        }
        if (position == itemList.size() - 1) {
            //最后一个的左边时，让右边消失
            RelativeLayout relativeLayout = convertView.findViewById(R.id.listview_my_info_right_area);
            relativeLayout.setVisibility(View.GONE);
        }
        ViewHolder viewHolder = new ViewHolder();
        MyInfoVO myInfoVO = itemList.get(position);
        viewHolder.text = convertView.findViewById(R.id.listview_my_info_left_area_text);
        viewHolder.text.setText(myInfoVO.getText());
        viewHolder.description = convertView.findViewById(R.id.listview_my_info_left_area_description);
        viewHolder.description.setText(myInfoVO.getDescription());
        if (position + 1 < itemList.size()) {
            myInfoVO = itemList.get(position + 1);
            viewHolder.text = convertView.findViewById(R.id.listview_my_info_right_area_text);
            viewHolder.text.setText(myInfoVO.getText());
            viewHolder.description = convertView.findViewById(R.id.listview_my_info_right_area_description);
            viewHolder.description.setText(myInfoVO.getDescription());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView text;
        TextView description;
    }
}
