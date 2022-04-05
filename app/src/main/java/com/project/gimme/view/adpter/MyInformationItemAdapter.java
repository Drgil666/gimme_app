package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.gimme.R;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.ParamItemUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class MyInformationItemAdapter extends BaseAdapter {
    private List<UserVoParamItem> itemList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;
    private User user;
    private TimePickerView datePicker;

    public MyInformationItemAdapter(Context context, User user) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.user = user;
        initItemList();
    }

    private void initItemList() {
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
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public UserVoParamItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserVoParamItem item = itemList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_friend_info_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.param.setText(item.getParamName());
        viewHolder.text.setText(item.getParamValue());
        if (item.getIsArrow()) {
            Glide.with(mContext).load(R.mipmap.right_arrow).into(viewHolder.icon);
            viewHolder.icon.setVisibility(View.VISIBLE);
            if (item.getParamType().equals(ParamItemUtil.ParamType.TYPE_TEXT.getCode())) {
                viewHolder.text.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        System.out.println(viewHolder.text.getText().toString());
                    }
                });
            } else if (item.getParamType().equals(ParamItemUtil.ParamType.TYPE_DATE.getCode())) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (datePicker == null) {
                            datePicker = new TimePickerBuilder(mContext, (date, v) -> {
                                viewHolder.text.setText(NumberUtil.changeToYearAndMonthAndDay(date));
                                user.setBirthday(date);
                                updateUser();
                            }).setTitleText("日期选择").build();
                        }
                        datePicker.show();
                    }
                });
            } else if (item.getParamType().equals(ParamItemUtil.ParamType.TYPE_GENDER.getCode())) {
                viewHolder.text.setInputType(EditorInfo.TYPE_NULL);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (v, options1, options2, options3) -> {
                            viewHolder.text.setText(UserUtil.GENDER_LIST[options1].getName());
                            user.setGender(options1);
                            updateUser();
                            return false;
                        }).setTitleText("性别选择").setSelectOptions(user.getGender()).build();
                        List<String> picker = new ArrayList<>();
                        for (UserUtil.Gender gender : UserUtil.GENDER_LIST) {
                            picker.add(gender.getName());
                        }
                        pvOptions.setPicker(picker);
                        pvOptions.show();
                    }
                });
            } else if (item.getParamType().equals(ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode())) {
                viewHolder.text.setInputType(EditorInfo.TYPE_NULL);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (v, options1, options2, options3) -> {
                            viewHolder.text.setText(UserUtil.OCCUPATION_LIST[options1].getName());
                            user.setOccupation(options1);
                            updateUser();
                            return false;
                        }).setTitleText("性别选择").setSelectOptions(user.getOccupation()).build();
                        List<String> picker = new ArrayList<>();
                        for (UserUtil.Occupation occupation : UserUtil.OCCUPATION_LIST) {
                            picker.add(occupation.getName());
                        }
                        pvOptions.setPicker(picker);
                        pvOptions.show();
                    }
                });
            } else if (item.getParamType().equals(ParamItemUtil.ParamType.TYPE_LOCAL.getCode())) {
                viewHolder.text.setInputType(EditorInfo.TYPE_NULL);
            } else {
                XToastUtils.toast("类型错误!");
            }
        } else {
            viewHolder.icon.setVisibility(View.INVISIBLE);
            viewHolder.text.setInputType(EditorInfo.TYPE_NULL);
        }
        return convertView;
    }

    private void updateUser() {

    }

    @SuppressLint("NonConstantResourceId")
    static class ViewHolder {
        @BindView(R.id.listview_friend_info_list_param)
        TextView param;
        @BindView(R.id.listview_friend_info_list_value)
        EditText text;
        @BindView(R.id.listview_friend_info_list_icon)
        ImageView icon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
