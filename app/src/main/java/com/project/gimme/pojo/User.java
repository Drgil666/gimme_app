package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/2 11:51
 */
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

    @Generated(hash = 192849480)
    public User(Integer id, String nick, String avatar, Integer country,
                Integer city, Integer province, Date birthday, String mail,
                Integer occupation, Integer company, String motto) {
        this.id = id;
        this.nick = nick;
        this.avatar = avatar;
        this.country = country;
        this.city = city;
        this.province = province;
        this.birthday = birthday;
        this.mail = mail;
        this.occupation = occupation;
        this.company = company;
        this.motto = motto;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getCountry() {
        return this.country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getProvince() {
        return this.province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getOccupation() {
        return this.occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public Integer getCompany() {
        return this.company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public String getMotto() {
        return this.motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }
}
