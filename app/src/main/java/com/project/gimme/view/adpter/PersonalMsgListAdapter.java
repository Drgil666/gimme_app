package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/14 16:34
 */
public class PersonalMsgListAdapter extends BaseAdapter {
    private List<PersonalMsgVO> itemList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public PersonalMsgListAdapter(Context context, List<PersonalMsgVO> personalMsgVOList) {
        layoutInflater = LayoutInflater.from(context);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public PersonalMsgVO getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonalMsgVO personalMsgVO = itemList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_personal_msg_list, parent, false);
        //TODO:待完成
        return convertView;
    }

    private static class ViewHolder {
    }
}
