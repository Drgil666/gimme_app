package com.project.gimme.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.MyInfoVO;
import com.project.gimme.view.adpter.MyInfoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 */
public class MyInfoFragment extends Fragment {
    private List<MyInfoVO> myInfoList = new ArrayList<>();
    private ListView myInfoListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        System.out.println("myInfoFragment");
        myInfoListView = view.findViewById(R.id.listview_my_info);
        initMyInfoListView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getMyInfoList() {
        for (int i = 1; i <= 7; i++) {
            MyInfoVO myInfoVO = new MyInfoVO();
            myInfoVO.setText("待定" + i);
            myInfoVO.setDescription("描述" + i);
            myInfoVO.setType(i);
            myInfoList.add(myInfoVO);
        }
    }

    private void initMyInfoListView() {
        getMyInfoList();
        myInfoListView.setAdapter(new MyInfoAdapter(getContext(), myInfoList));
    }
}