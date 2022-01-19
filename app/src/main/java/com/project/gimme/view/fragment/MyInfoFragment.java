package com.project.gimme.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
    private GridView myInfoGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
//        System.out.println("myInfoFragment");
        myInfoGridView = view.findViewById(R.id.gridview_my_info);
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

    private void initMyInfoGridView() {
        getMyInfoList();
        myInfoGridView.setAdapter(new MyInfoAdapter(getContext(), myInfoList));
        myInfoGridView.setOnItemClickListener((adapterView, view, i, l) -> System.out.println(myInfoGridView.getAdapter().getItemId(i)));
    }
}