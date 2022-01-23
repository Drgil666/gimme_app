package com.project.gimme.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.MyInfoListVO;
import com.project.gimme.view.adpter.MyInfoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 */
public class MyInfoFragment extends Fragment {
    private List<MyInfoListVO> myInfoList = new ArrayList<>();
    private ImageView userInfoIcon;
    private GridView myInfoGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        userInfoIcon = view.findViewById(R.id.user_info_icon);
        myInfoGridView = view.findViewById(R.id.gridview_my_info);
        initUserInfoLayout();
        initMyInfoGridView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getMyInfoList() {
        for (int i = 1; i <= 6; i++) {
            MyInfoListVO myInfoListVO = new MyInfoListVO();
            myInfoListVO.setNick("待定" + i);
            myInfoListVO.setDescription("描述" + i);
            myInfoListVO.setType(i);
            myInfoList.add(myInfoListVO);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUserInfoLayout() {
        userInfoIcon.setImageResource(R.mipmap.app_icon);
        userInfoIcon.setOnClickListener(view -> System.out.println("click!"));
        userInfoIcon.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                ((ImageView) view).clearColorFilter(); // 清除滤镜效果
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY); // 设置滤镜效果
                //如果return true的话,onClick的事件就不会触发!
            }
            return false;
        });
    }

    private void initMyInfoGridView() {
        getMyInfoList();
        myInfoGridView.setAdapter(new MyInfoAdapter(getContext(), myInfoList));
        myInfoGridView.setOnItemClickListener((adapterView, view, i, l) -> System.out.println(myInfoGridView.getAdapter().getItemId(i)));
    }
}