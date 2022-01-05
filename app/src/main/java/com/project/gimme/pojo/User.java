package com.project.gimme.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/2 11:51
 */
@Data
public class User {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 用户头像的mongoId
     */
    private String avatar;
    /**
     * 用户国家id
     */
    private Integer country;
    /**
     * 用户城市id
     */
    private Integer city;
    /**
     * 用户省份id
     */
    private Integer province;
    /**
     * 用户生日
     */
    private Date birthday;
    /**
     * 用户邮箱
     */
    private String mail;
    /**
     * 用户职业
     */
    private Integer occupation;
    /**
     * 用户公司
     */
    private Integer company;
    /**
     * 用户个性签名
     */
    private String motto;

}
