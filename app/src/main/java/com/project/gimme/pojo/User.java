package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/2 11:51
 */
@Data
@Entity(tableName = "user")
public class User {
    /**
     * 用户id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 用户昵称
     */
    @ColumnInfo(name = "nick", typeAffinity = ColumnInfo.TEXT)
    private String nick;
    /**
     * 用户头像的mongoId
     */
    @ColumnInfo(name = "avatar", typeAffinity = ColumnInfo.TEXT)
    private String avatar;
    /**
     * 用户国家id
     */
    @ColumnInfo(name = "country", typeAffinity = ColumnInfo.INTEGER)
    private Integer country;
    /**
     * 用户城市id
     */
    @ColumnInfo(name = "city", typeAffinity = ColumnInfo.INTEGER)
    private Integer city;
    /**
     * 用户省份id
     */
    @ColumnInfo(name = "province", typeAffinity = ColumnInfo.INTEGER)
    private Integer province;
    /**
     * 用户生日
     */
    @ColumnInfo(name = "birthday", typeAffinity = ColumnInfo.INTEGER)
    private Date birthday;
    /**
     * 用户邮箱
     */
    @ColumnInfo(name = "mail", typeAffinity = ColumnInfo.TEXT)
    private String mail;
    /**
     * 用户职业
     */
    @ColumnInfo(name = "occupation", typeAffinity = ColumnInfo.INTEGER)
    private Integer occupation;
    /**
     * 用户公司
     */
    @ColumnInfo(name = "company", typeAffinity = ColumnInfo.INTEGER)
    private Integer company;
    /**
     * 用户个性签名
     */
    @ColumnInfo(name = "motto", typeAffinity = ColumnInfo.TEXT)
    private String motto;
}
