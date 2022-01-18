package com.project.gimme.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
            MyInfoVO myInfoVO = new MyInfoVO();
            myInfoVO.setNick("待定" + i);
            myInfoVO.setDescription("描述" + i);
            myInfoVO.setType(i);
            myInfoList.add(myInfoVO);
        }
    }

    private void initMyInfoGridView() {
        getMyInfoList();
        myInfoGridView.setAdapter(new MyInfoAdapter(getContext(), myInfoList));
        myInfoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(myInfoGridView.getAdapter().getItemId(i));
            }
        });
    }
}