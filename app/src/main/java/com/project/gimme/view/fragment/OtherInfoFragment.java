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
public class OtherInfoFragment extends Fragment {
    private Integer type;
    private Integer objectId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getType();
//        System.out.println("type:" + type + "object_id:" + objectId);
        return inflater.inflate(R.layout.fragment_other_info, container, false);
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt("type");
        objectId = bundle.getInt("object_id");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}