package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DrGilbert
 * @date 2022/1/19 15:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {
    /**
     * 好友备注
     */
    private String note;
    /**
     * 国家名
     */
    private String countryNick;
    /**
     * 省份名
     */
    private String provinceNick;
    /**
     * 城市名
     */
    private String cityNick;
    /**
     * 职业名
     */
    private String occupationNick;
}
