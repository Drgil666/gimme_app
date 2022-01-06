package com.project.gimme.utils;


import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zxl
 * @date 2021/4/12 18:19
 */
public class RegexUtil {

    public static final String USER_EMAIL = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
    public static final String USER_ID_CARD = "[1-9]\\d{16}[a-zA-Z0-9]{1}";
    public static final String USER_MOBILE = "(\\+\\d+)?1[3456789]\\d{9}$";
    public static final String USER_PHONE = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
    public static final String USER_BIRTHDAY = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
    public static final String DIGIT = "^[-\\+]?[\\d]+$";
    public static final String DECIMALS = "\\-?[1-9]\\d+(\\.\\d+)?";
    public static final String CHAR = "[a-z|A-Z]+";
    public static final String BLANK_SPACE = "\\s+";
    public static final String CHINESE = "^[\u4E00-\u9FA5]+$";
    public static final String URL = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
    public static final String POSTCODE = "[1-9]\\d{5}";
    public static final String IP_ADDRESS = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        return Pattern.matches(USER_EMAIL, email);
    }

    /**
     * 验证earchValue
     *
     * @param str 被判断的字符串
     * @return 验证失败返回true，验证成功返回false
     */
    public static boolean checkSearchValue(String str) {
        str = StringUtils.isEmpty(str) ? "" : str;
        return !str.contains("%");
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码18位，第一位不能为0，最后一位可能是数字或字母，中间16位为数字 \d同[0-9]
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        return Pattern.matches(USER_ID_CARD, idCard);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *               <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        return Pattern.matches(USER_MOBILE, mobile);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        return Pattern.matches(USER_PHONE, phone);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        return Pattern.matches(DIGIT, digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        return Pattern.matches(DECIMALS, decimals);
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        return Pattern.matches(BLANK_SPACE, blankSpace);
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        return Pattern.matches(CHINESE, chinese);
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        return Pattern.matches(USER_BIRTHDAY, birthday);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkUrl(String url) {
        return Pattern.matches(URL, url);
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        return Pattern.matches(POSTCODE, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        return Pattern.matches(IP_ADDRESS, ipAddress);
    }

    /**
     * 判断字符串是否符合正则表达式
     *
     * @param str   被判断的字符串
     * @param regex 正则表达式
     * @return 比较结果
     */
    public static boolean find(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }


    /**
     * 判断是否是字母
     *
     * @param value 被判断的字符串
     * @return 是否是字母
     */
    public static boolean checkChar(String value) {
        Pattern pattern = Pattern.compile(CHAR);
        return pattern.matcher(value).matches();
    }
}
