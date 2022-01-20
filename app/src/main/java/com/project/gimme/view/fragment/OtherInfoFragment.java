package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.OBJECTID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.TYPE_ATTRIBUTE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.utils.ChatMsgUtil;

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
        initTopBar();
        return inflater.inflate(R.layout.fragment_other_info, container, false);
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt(TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECTID_ATTRIBUTE);
    }

    private void initTopBar() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}