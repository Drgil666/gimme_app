package com.project.gimme.view.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileController;
import com.project.gimme.controller.ChatFileInfoController;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.InfoTypeUtil;
import com.project.gimme.utils.MsgTypeUtil;
import com.project.gimme.utils.TextUtil;
import com.project.gimme.view.activity.ChannelNoticeActivity;
import com.project.gimme.view.activity.InfoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 * @date 2022/1/13 12:56
 */
public class ChatMsgVoAdapter extends BaseAdapter {
    private List<ChatMsgVO> chatMsgVOList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;
    private Integer type;
    private ViewHolder viewHolder;
    private Handler handler1 = new Handler();
    private Handler handler2 = new Handler();

    public ChatMsgVoAdapter(Context context, List<ChatMsgVO> chatMsgVOList, Integer type) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.chatMsgVOList = chatMsgVOList;
        this.type = type;
    }

    @Override
    public int getCount() {
        if (chatMsgVOList == null) {
            return 0;
        }
        return chatMsgVOList.size();
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public ChatMsg getItem(int position) {
        return chatMsgVOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chatMsgVOList.get(position).getId();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgVO chatMsgVO = chatMsgVOList.get(position);
        convertView = layoutInflater.inflate(R.layout.listview_chat, parent, false);
        LinearLayout linearLayout;
        viewHolder = new ViewHolder(convertView);
        viewHolder.leftLayout = convertView.findViewById(R.id.left_layout);
        viewHolder.rightLayout = convertView.findViewById(R.id.right_layout);
        if (!chatMsgVO.getIsSelf()) {
            linearLayout = convertView.findViewById(R.id.right_bubble);
            linearLayout.setVisibility(View.GONE);
            viewHolder.icon = convertView.findViewById(R.id.left_image);
            viewHolder.icon.setImageResource(R.mipmap.default_icon);
            viewHolder.icon.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.text = convertView.findViewById(R.id.left_content);
            viewHolder.text.setText(TextUtil.expandableText(chatMsgVO.getText()));
            viewHolder.text.setVisibility(View.VISIBLE);
            viewHolder.text.setEmojiconSize(30);
            viewHolder.nick = convertView.findViewById(R.id.left_name);
            viewHolder.nick.setText(chatMsgVO.getOwnerNick());
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_FRIEND.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_GROUP_MEMBER.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_CHANNEL_MEMBER.getCode());
                    }
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, chatMsgVO.getOwnerId());
                    //TODO：尽快接入接口!
                    Intent intent = new Intent(mContext, InfoActivity.class).putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            viewHolder.channelNoticeCount = convertView.findViewById(R.id.left_channel_count);
            viewHolder.pic = convertView.findViewById(R.id.left_pic);
        } else {
            linearLayout = convertView.findViewById(R.id.left_bubble);
            linearLayout.setVisibility(View.GONE);
            viewHolder.icon = convertView.findViewById(R.id.right_image);
            viewHolder.icon.setImageResource(R.mipmap.default_icon);
            viewHolder.icon.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.text = convertView.findViewById(R.id.right_content);
            viewHolder.text.setVisibility(View.VISIBLE);
            viewHolder.text.setText(TextUtil.expandableText(chatMsgVO.getText()));
            viewHolder.text.setEmojiconSize(30);
            viewHolder.nick = convertView.findViewById(R.id.right_name);
            viewHolder.nick.setText(chatMsgVO.getOwnerNick());
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_SELF.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode());
                    }
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, chatMsgVO.getOwnerId());
                    Intent intent = new Intent(mContext, InfoActivity.class).putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            viewHolder.channelNoticeCount = convertView.findViewById(R.id.right_channel_count);
            viewHolder.pic = convertView.findViewById(R.id.right_pic);
        }
        viewHolder.pic.setVisibility(View.GONE);
        if (chatMsgVO.getMsgType().equals(MsgTypeUtil.MsgType.TYPE_PIC.getCode())) {
            viewHolder.text.setVisibility(View.GONE);
            RoundedCorners roundedCorners = new RoundedCorners(5);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(mContext)
                    .load(GimmeApplication.REMOTE_URL + "/api/chat/file/download/" + chatMsgVO.getText())
                    .apply(new RequestOptions().override(110, 110))
                    .apply(options)
//                                .placeholder(R.drawable.loading_spinner)
                    .into(viewHolder.pic);
//            downloadChatFile(viewHolder.text.getText().toString());
            viewHolder.pic.setVisibility(View.VISIBLE);
        }
        if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            viewHolder.channelNoticeCount.setText("共" + chatMsgVO.getChannelNoticeCount() + "条回复");
            viewHolder.channelNoticeCount.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.CHANNEL_NOTICE_ID_ATTRIBUTE, chatMsgVO.getObjectId());
                    System.out.println(chatMsgVO.getText());
                    Intent intent = new Intent(mContext, ChannelNoticeActivity.class).putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            viewHolder.rightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.CHANNEL_NOTICE_ID_ATTRIBUTE, chatMsgVO.getObjectId());
                    System.out.println(chatMsgVO.getText());
                    Intent intent = new Intent(mContext, ChannelNoticeActivity.class).putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        } else {
            viewHolder.channelNoticeCount.setVisibility(View.GONE);
        }
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            viewHolder.nick.setVisibility(View.GONE);
        } else {
            viewHolder.nick.setVisibility(View.VISIBLE);
        }
        viewHolder.icon.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY); // 设置滤镜效果
            } else {
                ((ImageView) view).clearColorFilter(); // 清除滤镜效果
            }
            return false;//如果return true的话,onClick的事件就不会触发!
        });
        return convertView;
    }

    private void downloadChatFile(String id) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Looper.prepare();
                ResponseData<ChatFile> responseData = ChatFileController.getChatFile(id);
                if (responseData != null && responseData.isSuccess()) {
                    ChatFile chatFile = responseData.getData();
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            String path = mContext.getExternalFilesDir(null).getPath();
                            String filePath = path + "/" + GimmeApplication.getUserId() + "/" + chatFile.getId();
                            File file = new File(filePath, chatFile.getFilename());
                            if (!file.exists()) {
                                System.out.println("文件不存在！");
                                loadPicture(chatFile.getId(), chatFile.getFilename());
                            } else {
                                System.out.println("文件已存在！");
                            }
                        }
                    });
                }
                Looper.loop();
            }
        }).start();
    }

    private void loadPicture(Integer id, String fileName) {
        String path = mContext.getExternalFilesDir(null).getPath();
        String filePath = path + "/" + GimmeApplication.getUserId() + "/" + id.toString();
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Looper.prepare();
                ChatFileInfoController.downloadFile(filePath, id, fileName);
                //TODO:优化文件目录
                handler2.post(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(filePath, fileName);
                        //System.out.println(filePath + "/" + fileName);
//                        Bitmap bitmap = BitmapFactory.decodeFile(filePath + "/" + fileName);
//                        bitmap = Bitmap.createScaledBitmap(bitmap, 110, 110, false);
//                        viewHolder.pic.setImageBitmap(bitmap);
                        Glide.with(mContext)
                                .load(file)
                                .centerCrop()
//                                .placeholder(R.drawable.loading_spinner)
                                .into(viewHolder.pic);
                    }
                });
                Looper.loop();
            }
        }).start();
    }

    static class ViewHolder {
        ImageView icon;
        TextView nick;
        EmojiconTextView text;
        TextView channelNoticeCount;
        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        ImageView pic;

        ViewHolder(View view) {
        }
    }
}
