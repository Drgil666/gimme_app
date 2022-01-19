package com.project.gimme.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.project.gimme.pojo.vo.UserVO;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class FriendInfoAdapter extends BaseAdapter {
    private UserVO userVO = new UserVO();
    private LayoutInflater layoutInflater;

    public FriendInfoAdapter(Context context, UserVO userVO) {
        layoutInflater = LayoutInflater.from(context);
        this.userVO = userVO;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
