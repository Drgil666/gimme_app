package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/2 11:51
 */
@Data
@Entity(nameInDb = "user")
public class User {
    /**
     * 用户id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 用户昵称
     */
    @Property(nameInDb = "nick")
    private String nick;
    /**
     * 用户头像的mongoId
     */
    @Property(nameInDb = "avatar")
    private String avatar;
    /**
     * 用户国家id
     */
    @Property(nameInDb = "country")
    private Integer country;
    /**
     * 用户城市id
     */
    @Property(nameInDb = "city")
    private Integer city;
    /**
     * 用户省份id
     */
    @Property(nameInDb = "province")
    private Integer province;
    /**
     * 用户生日
     */
    @Property(nameInDb = "birthday")
    private Date birthday;
    /**
     * 用户邮箱
     */
    @Property(nameInDb = "mail")
    private String mail;
    /**
     * 用户职业
     */
    @Property(nameInDb = "occupation")
    private Integer occupation;
    /**
     * 用户公司
     */
    @Property(nameInDb = "company")
    private Integer company;
    /**
     * 用户个性签名
     */
    @Property(nameInDb = "motto")
    private String motto;
}
