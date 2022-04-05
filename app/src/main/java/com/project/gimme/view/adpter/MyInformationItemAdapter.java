package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.ProvinceInfo;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.ParamItemUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xutil.net.JsonUtil;
import com.xuexiang.xutil.resource.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

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
    private Handler handler = new Handler();
    private List<ProvinceInfo> provinceInfoList = new ArrayList<>();
    private List<ProvinceInfo> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private List<List<List<String>>> options3Items = new ArrayList<>();

    public MyInformationItemAdapter(Context context, User user, List<UserVoParamItem> itemList) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.user = user;
        this.itemList = itemList;
        provinceInfoList = JsonUtil.fromJson(ResourceUtils.readStringFromAssert("province.json"), new TypeToken<List<ProvinceInfo>>() {
        }.getType());
        loadData();
    }

    private void loadData() {
        options1Items = provinceInfoList;
        options2Items = new ArrayList<>();
        options3Items = new ArrayList<>();
        //遍历省份（第一级）
        for (ProvinceInfo provinceInfo : provinceInfoList) {
            //该省的城市列表（第二级）
            List<String> cityList = new ArrayList<>();
            //该省的所有地区列表（第三级）
            List<List<String>> areaList = new ArrayList<>();
            for (ProvinceInfo.City city : provinceInfo.getCityList()) {
                //添加城市
                String cityName = city.getName();
                cityList.add(cityName);
                //该城市的所有地区列表
                List<String> cityAreaList = new ArrayList<>();
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (city.getArea() == null || city.getArea().size() == 0) {
                    cityAreaList.add("");
                } else {
                    cityAreaList.addAll(city.getArea());
                }
                //添加该省所有地区数据
                areaList.add(cityAreaList);
            }
            options2Items.add(cityList);
            options3Items.add(areaList);
        }
    }

    /**
     * @return 获取默认城市的索引
     */
    private int[] getDefaultCity() {
        int[] res = new int[3];
        ProvinceInfo provinceInfo;
        List<ProvinceInfo.City> cities;
        ProvinceInfo.City city;
        List<String> ares;
        for (int i = 0; i < options1Items.size(); i++) {
            provinceInfo = options1Items.get(i);
            if ("浙江省".equals(provinceInfo.getName())) {
                res[0] = i;
                cities = provinceInfo.getCityList();
                for (int j = 0; j < cities.size(); j++) {
                    city = cities.get(j);
                    if ("杭州市".equals(city.getName())) {
                        res[1] = j;
                        ares = city.getArea();
                        for (int k = 0; k < ares.size(); k++) {
                            if ("江干区".equals(ares.get(k))) {
                                res[2] = k;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return res;
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
                viewHolder.text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            String text = viewHolder.text.getText().toString();
                            System.out.println(text);
                            if ("公司".equals(item.getParamName())) {
                                user.setCompany(text);
                            } else if ("邮箱".equals(item.getParamName())) {
                                user.setMail(text);
                            } else if ("个性签名".equals(item.getParamName())) {
                                user.setMotto(text);
                            }
                            updateUser();
                        }
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
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int[] defaultSelectOptions = getDefaultCity();
                        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (v, options1, options2, options3) -> {
                            //返回的分别是三个级别的选中位置
                            String tx = options1Items.get(options1).getPickerViewText() + "-" +
                                    options2Items.get(options1).get(options2) + "-" +
                                    options3Items.get(options1).get(options2).get(options3);
                            user.setCity(tx);
                            viewHolder.text.setText(tx);
                            updateUser();
                            //XToastUtils.toast(tx);
                            return false;
                        })
                                .setTitleText("城市选择")
                                .setDividerColor(Color.BLACK)
                                //切换选项时，还原到第一项
                                .isRestoreItem(true)
                                //设置选中项文字颜色
                                .setTextColorCenter(Color.BLACK)
                                .setContentTextSize(20)
                                .isDialog(false)
                                .setSelectOptions(defaultSelectOptions[0], defaultSelectOptions[1], defaultSelectOptions[2])
                                .build();
                        pvOptions.setPicker(options1Items, options2Items, options3Items);
                        pvOptions.show();
                    }
                });

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
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                UserController.updateUser(user);
            }
        }).start();
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
