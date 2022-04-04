package com.project.gimme.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.MyInfoListVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.MyInfoUtil;
import com.project.gimme.view.activity.MyInformationActivity;
import com.project.gimme.view.adpter.MyInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class MyInfoFragment extends Fragment {
    private List<MyInfoListVO> myInfoList = new ArrayList<>();
    @BindView(R.id.user_info_icon)
    ImageView userInfoIcon;
    @BindView(R.id.gridview_my_info)
    GridView myInfoGridView;
    @BindView(R.id.user_info_nick)
    TextView userInfoNick;
    @BindView(R.id.user_info_company)
    TextView userInfoCompany;
    @BindView(R.id.user_info_motto)
    TextView userInfoMotto;
    private Unbinder unbinder;
    private User user;
    Handler handler = new Handler();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getUser();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        getUser();
        initUserInfoLayout();
        initMyInfoGridView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getUser() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<User> userResponseData = UserController.getUser("");
                if (userResponseData != null && userResponseData.isSuccess()) {
                    user = userResponseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            userInfoNick.setText(user.getNick());
                            userInfoCompany.setText(user.getCompany());
                            userInfoMotto.setText(user.getMotto());
                            Glide.with(getContext())
                                    .load(GimmeApplication.REMOTE_URL + "/api/chat/file/download/" + user.getAvatar())
                                    .error(R.mipmap.default_icon)
                                    .into(userInfoIcon);
                        }
                    });
                }
            }
        }).start();
    }

    private void getMyInfoList() {
        myInfoList = new ArrayList<>();
        for (MyInfoUtil.MyInfoType myInfoType : MyInfoUtil.MY_INFO_TYPE_LIST) {
            MyInfoListVO myInfoListVO = new MyInfoListVO();
            myInfoListVO.setNick(myInfoType.getName());
            myInfoListVO.setDescription(myInfoType.getDescription());
            myInfoListVO.setType(myInfoType.getCode() + "");
            myInfoList.add(myInfoListVO);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUserInfoLayout() {
        userInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyInformationActivity.class);
                startActivity(intent);
            }
        });
        userInfoIcon.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY); // 设置滤镜效果
            } else {
                ((ImageView) view).clearColorFilter(); // 清除滤镜效果
            }
            return false;//如果return true的话,onClick的事件就不会触发!
        });
    }

    private void initMyInfoGridView() {
        getMyInfoList();
        myInfoGridView.setAdapter(new MyInfoAdapter(getContext(), myInfoList));
        myInfoGridView.setOnItemClickListener((adapterView, view, i, l) -> System.out.println(myInfoGridView.getAdapter().getItemId(i)));
    }
}