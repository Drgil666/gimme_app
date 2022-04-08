package com.project.gimme.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.MyInfoListVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.ParamItemUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.MyInformationItemAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

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
    @BindView(R.id.user_info_listview)
    ListView listView;
    @BindView(R.id.user_info_nick)
    TextView userInfoNick;
    @BindView(R.id.user_info_company)
    TextView userInfoCompany;
    @BindView(R.id.user_info_motto)
    TextView userInfoMotto;
    @BindView(R.id.user_info_imageview)
    ImageView imageView;
    private List<UserVoParamItem> itemList = new ArrayList<>();
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
                                    .load(GimmeApplication.getImageUrl(user.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                    .into(userInfoIcon);
                            Glide.with(getContext())
                                    .load(GimmeApplication.getImageUrl(user.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                    .into(imageView);
                            itemList = new ArrayList<>();
                            UserVoParamItem item = new UserVoParamItem("Gimme账号", user.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[user.getGender()].getName(), true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("生日", NumberUtil.changeToYearAndMonthAndDay(user.getBirthday()), true, ParamItemUtil.ParamType.TYPE_DATE.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("职业", UserUtil.OCCUPATION_LIST[user.getOccupation()].getName(), true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("公司", user.getCompany(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("邮箱", user.getMail(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("个性签名", user.getMotto(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("所在地", user.getCity(), true, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
                            itemList.add(item);
                            listView.setAdapter(new MyInformationItemAdapter(getContext(), user, itemList));
                        }
                    });
                }
            }
        }).start();
    }

    private void initUserInfoLayout() {
        userInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha0to1));
                imageView.setVisibility(View.VISIBLE);
                RelativeLayout topLayout = (RelativeLayout) ((Activity) getContext()).findViewById(R.id.main_top_bar);
                topLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha0to1));
                topLayout.setVisibility(View.GONE);
                RelativeLayout bottomLayout = (RelativeLayout) ((Activity) getContext()).findViewById(R.id.main_bottom_bar);
                bottomLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha0to1));
                bottomLayout.setVisibility(View.GONE);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
                RelativeLayout topLayout = (RelativeLayout) ((Activity) getContext()).findViewById(R.id.main_top_bar);
                topLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha1to0));
                topLayout.setVisibility(View.VISIBLE);
                RelativeLayout bottomLayout = (RelativeLayout) ((Activity) getContext()).findViewById(R.id.main_bottom_bar);
                bottomLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha1to0));
                bottomLayout.setVisibility(View.VISIBLE);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new BottomSheet.BottomListSheetBuilder(getContext())
                        .addItem("保存到相册")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            FileUtil.saveImageToGallery(getContext(), ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            XToastUtils.toast("保存成功!");
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }
}