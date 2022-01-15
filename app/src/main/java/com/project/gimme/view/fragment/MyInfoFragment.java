package com.project.gimme.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;

/**
 * @author DrGilbert
 */
public class MyInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("myInfoFragment");
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("myInfoDestroy!");
    }
}