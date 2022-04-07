package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.SessionUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.fragment.FriendFragment;
import com.project.gimme.view.fragment.MessageFragment;
import com.project.gimme.view.fragment.MyInfoFragment;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.util.QRCodeAnalyzeUtils;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity {
    private Integer currentFragment = SessionUtil.Character.TYPE_MESSAGE.getCode();
    private final Integer height = GimmeApplication.getHeight();
    private final Integer weight = GimmeApplication.getWeight();
    private MessageFragment messageFragment;
    private FriendFragment friendFragment;
    private MyInfoFragment myInfoFragment;
    @BindView(R.id.main_top_text)
    TextView topText;
    @BindView(R.id.main_top_right_button)
    ImageView topRightButton;
    @BindView(R.id.main_message_layout_icon)
    ImageView messageIcon;
    @BindView(R.id.main_message_layout_text)
    TextView messageText;
    @BindView(R.id.main_friend_layout_icon)
    ImageView friendIcon;
    @BindView(R.id.main_friend_layout_text)
    TextView friendText;
    @BindView(R.id.main_my_info_layout_icon)
    ImageView myInfoIcon;
    @BindView(R.id.main_my_info_layout_text)
    TextView myInfoText;
    @BindView(R.id.main_message_layout_new_message_count_background)
    RelativeLayout newMessageCountBackGround;
    @BindView(R.id.main_message_layout_new_message_count)
    TextView newMessageCount;
    private XUISimplePopup mMenuPopup;
    private final Context mContext = this;
    private final Activity activity = this;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    //TODO:动态更新的部分仍然需要修复
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        messageFragment = new MessageFragment();
        friendFragment = new FriendFragment();
        myInfoFragment = new MyInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.getFragments().clear();
        fragmentTransaction.add(R.id.main_body_fragment, messageFragment).commit();
        initTopBar(0.1);
        initTopText();
        initPopUp();
        initBottomBar(0.1);
        setTopRightButton(0.0);
    }

    private void initPopUp() {
        mMenuPopup = new XUISimplePopup(this, new AdapterItem[]{
                new AdapterItem("创建群/频道"),
                new AdapterItem("加好友/群/频道"),
                new AdapterItem("扫一扫")
        }).setHasDivider(true).
                create(new XUISimplePopup.OnPopupItemClickListener() {
                           @Override
                           public void onItemClick(XUISimpleAdapter adapter, AdapterItem item, int position) {
                               switch (position) {
                                   case 0: {
                                       Bundle bundle = new Bundle();
                                       bundle.putInt(BundleUtil.CONTACTS_LIST_TYPE_ATTRIBUTE, ContactsUtil.ContactType.TYPE_CREATE_CONTACT.getCode());
                                       Intent intent = new Intent(mContext, FriendListActivity.class).putExtras(bundle);
                                       startActivity(intent);
                                       break;
                                   }
                                   case 1: {
                                       Bundle bundle = new Bundle();
                                       bundle.putInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE, ContactsUtil.SearchType.TYPE_MESSAGE.getCode());
                                       Intent intent = new Intent(mContext, SearchActivity.class).putExtras(bundle);
                                       startActivity(intent);
                                       break;
                                   }
                                   case 2: {
                                       List<String> itemList = new ArrayList<>();
                                       itemList.add("相机扫描");
                                       itemList.add("本地图片导入");
                                       new MaterialDialog.Builder(mContext)
                                               .title("请选择扫描方式")
                                               .items(itemList)
                                               .itemsCallbackSingleChoice(
                                                       0,
                                                       (dialog, itemView, which, text) -> {
                                                           XToastUtils.toast(which + ": " + text);
                                                           switch (which) {
                                                               case 0: {
                                                                   XQRCode.startScan(activity, REQUEST_CODE);
                                                                   break;
                                                               }
                                                               case 1: {
                                                                   startActivityForResult(IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.IMAGE), REQUEST_IMAGE);
                                                                   break;
                                                               }
                                                               default: {
                                                               }
                                                           }
                                                           return true;
                                                       })
                                               .positiveText("选择")
                                               .negativeText("返回")
                                               .show();
                                   }
                                   default: {
                                       XToastUtils.toast("类型错误!");
                                       break;
                                   }
                               }
                           }
                       }
                );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理二维码扫描结果
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //处理扫描结果（在界面上显示）
            handleScanResult(data);
        }
        //选择系统图片并解析
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                getAnalyzeQRCodeResult(uri);
            }
        }
    }

    /**
     * 处理二维码扫描结果
     *
     * @param data
     */
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    XToastUtils.toast("解析结果:" + result);
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    XToastUtils.toast("解析二维码失败");
                }
            }
        }
    }

    /**
     * 进行二维码解析
     *
     * @param uri
     */
    private void getAnalyzeQRCodeResult(Uri uri) {
        XQRCode.analyzeQRCode(PathUtils.getFilePathByUri(mContext, uri), new QRCodeAnalyzeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                ToastUtils.toast("解析结果:" + result, Toast.LENGTH_LONG);
            }

            @Override
            public void onAnalyzeFailed() {
                ToastUtils.toast("解析二维码失败", Toast.LENGTH_LONG);
            }
        });
    }

    private void initTopBar(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_top_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
    }

    private void initTopText() {
        topText.setText("消息");
    }

    private void setTopText(String text) {
        topText.setText(text);
    }

    private void setTopRightButton(double size) {
        topRightButton.setOnClickListener(view -> {
                    if (!currentFragment.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
                        mMenuPopup.showDown(view);
                    } else {
                        Intent intent = new Intent(this, SettingActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void initBottomBar(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_bottom_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
        double layoutWeight = weight / 3.0;
        initMessageLayout(layoutWeight);
        initFriendLayout(layoutWeight);
        initMyInfoLayout(weight - 2 * layoutWeight);
    }

    private void initMessageLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_message_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_MESSAGE.getCode())) {
                setTopText(SessionUtil.Character.TYPE_MESSAGE.getName());
                changeFragment(SessionUtil.Character.TYPE_MESSAGE.getCode());
                currentFragment = SessionUtil.Character.TYPE_MESSAGE.getCode();
            }
        });
    }

    private void initFriendLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_friend_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
                setTopText(SessionUtil.Character.TYPE_FRIEND.getName());
                changeFragment(SessionUtil.Character.TYPE_FRIEND.getCode());
                currentFragment = SessionUtil.Character.TYPE_FRIEND.getCode();
            }
        });
    }

    private void initMyInfoLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_my_info_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
                setTopText(SessionUtil.Character.TYPE_MY_INFO.getName());
                changeFragment(SessionUtil.Character.TYPE_MY_INFO.getCode());
                currentFragment = SessionUtil.Character.TYPE_MY_INFO.getCode();
            }
        });
    }

    private void changeFragment(Integer op) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment to = getFragment(op);
        Fragment from = getFragment(currentFragment);
        if (!to.isAdded()) {//未被add
            fragmentTransaction.hide(from).add(R.id.main_body_fragment, to).commit();
        } else {//已经被add
            fragmentTransaction.hide(from).show(to).commit();
        }
        changeUIColor(op);
    }

    private void changeUIColor(Integer op) {
        Glide.with(this).load(R.mipmap.message).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(messageIcon);
        Glide.with(this).load(R.mipmap.contacts).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(friendIcon);
        Glide.with(this).load(R.mipmap.my_info).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(myInfoIcon);
        messageText.setTextColor(R.color.black);
        friendText.setTextColor(R.color.black);
        myInfoText.setTextColor(R.color.black);
        if (op.equals(SessionUtil.Character.TYPE_MESSAGE.getCode())) {
            Glide.with(this).load(R.mipmap.message_select).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(messageIcon);
            messageText.setTextColor(R.color.gimme_color);
            Glide.with(this).load(R.mipmap.add_plus).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(topRightButton);
        } else if (op.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
            Glide.with(this).load(R.mipmap.contacts_select).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(friendIcon);
            friendText.setTextColor(R.color.gimme_color);
            Glide.with(this).load(R.mipmap.add_plus).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(topRightButton);
        } else if (op.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
            Glide.with(this).load(R.mipmap.my_info_select).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(myInfoIcon);
            myInfoText.setTextColor(R.color.gimme_color);
            Glide.with(this).load(R.mipmap.setting).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(topRightButton);
        }
        //TODO:Glide要缓存常量图片
    }

    private Fragment getFragment(Integer op) {
        if (op.equals(SessionUtil.Character.TYPE_MESSAGE.getCode())) {
            return messageFragment;
        } else if (op.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
            return friendFragment;
        } else if (op.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
            return myInfoFragment;
        } else {
            return null;
        }
    }
}