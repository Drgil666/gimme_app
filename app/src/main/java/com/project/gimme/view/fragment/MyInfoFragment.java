package com.project.gimme.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.view.adpter.MyInfoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 */
public class MyInfoFragment extends Fragment {
    private List<String> myInfoList = new ArrayList<>();
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
        for (int i = 1; i <= 6; i++) {
            myInfoList.add("待定" + i);
        }
    }

    private void initMyInfoListView() {
        getMyInfoList();
        myInfoListView.setAdapter(new MyInfoAdapter(getContext(), myInfoList));
    }
}