package com.project.gimme.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileInfoController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.MyInfoListVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.MsgTypeUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.ParamItemUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.MyInformationItemAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xutil.app.IntentUtils;

import java.io.File;
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
                            UserVoParamItem item = new UserVoParamItem("Gimme??????", user.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("??????", user.getNick(), true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("??????", user.getGender() != null ? UserUtil.GENDER_LIST[user.getGender()].getName() : null, true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("??????", NumberUtil.changeToYearAndMonthAndDay(user.getBirthday()), true, ParamItemUtil.ParamType.TYPE_DATE.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("??????", user.getOccupation() != null ? UserUtil.OCCUPATION_LIST[user.getOccupation()].getName() : null, true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("??????", user.getCompany(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("??????", user.getMail(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("????????????", user.getMotto(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
                            itemList.add(item);
                            item = new UserVoParamItem("?????????", user.getCity(), true, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
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
                        .addItem("???????????????")
                        .addItem("????????????")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            authorize();
                            switch (position) {
                                case 0: {
                                    FileUtil.saveImageToGallery(getContext(), ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                                    XToastUtils.toast("????????????!");
                                    break;
                                }
                                case 1: {
                                    Intent intent = IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.IMAGE);
                                    startActivityForResult(intent, MsgTypeUtil.MsgType.TYPE_FILE.getCode());
                                }
                            }
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MsgTypeUtil.MsgType.TYPE_FILE.getCode()) {
            if (data != null) {
                Uri uri = data.getData();
                if (FileUtil.getRealPathFromUri(getContext(), uri) != null) {
                    //???uri?????????????????????????????????file??????
                    File file = new File(FileUtil.getRealPathFromUri(getContext(), uri));
                    uploadFile(file, ChatMsgUtil.Character.TYPE_FRIEND.getName());
                }
            }
        }
    }

    private void uploadFile(File file, String type) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChatFileInfoController.uploadAvatar(file, type, 0);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.callOnClick();
                        Glide.with(getContext())
                                .load(file)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(userInfoIcon);
                        Glide.with(getContext())
                                .load(file)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(imageView);
                        XToastUtils.toast("??????????????????!");
                    }
                });
            }
        }).start();
    }

    private void authorize() {
        //??????????????????????????????
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //????????????????????????????????????????????????????????????????????????????????? true???
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //?????????????????????????????????????????????????????????????????????????????????
                // ???????????????????????????????????????????????????.??????????????????"????????????"??????????????????false
            } else {
                //????????????????????????????????????????????????????????????????????????1??????????????????????????????????????????onRequestPermissionsResult????????????????????????
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
}