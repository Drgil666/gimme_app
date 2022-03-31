package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.Group;

import lombok.Data;

/**
 * @author Gilbert
 * @date 2022/3/31 12:45
 */
@Data
public class ContactVO {
    /**
     * 会话头像
     */
    private String img;
    /**
     * 会话名
     */
    private String nick;
    /**
     * 会话id
     */
    private Integer objectId;

    public static ContactVO convertUserVO(UserVO userVO) {
        ContactVO contactVO = new ContactVO();
        contactVO.img = userVO.getAvatar();
        if (userVO.getNote() != null) {
            contactVO.nick = userVO.getNote();
        } else {
            contactVO.nick = userVO.getNick();
        }
        contactVO.objectId = userVO.getId();
        return contactVO;
    }

    public static ContactVO convertGroup(Group group) {
        ContactVO contactVO = new ContactVO();
        contactVO.img = group.getAvatar();
        contactVO.objectId = group.getId();
        contactVO.nick = group.getNick();
        return contactVO;

    }

    public static ContactVO convertChannel(Channel channel) {
        ContactVO contactVO = new ContactVO();
        contactVO.img = channel.getAvatar();
        contactVO.objectId = channel.getId();
        contactVO.nick = channel.getNick();
        return contactVO;
    }
}
