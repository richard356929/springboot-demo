package com.z.suite.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {

    /**
     * 获取UUID编码（16位）
     *
     * @param modelCode
     * @return
     */
    public static String uuidCode(String modelCode) {
        // 年份，99取余
        int currentYear = Integer.valueOf(DateFormatUtils.format(new Date(), "yyyy")) % 99;
        String currentYearStr = StringUtil.leftPad(String.valueOf(currentYear), 2, '0');

        // UUID，取HashCode
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {// 有可能是负数
            hashCode = -hashCode;
        }
        String hashCodeStr = StringUtil.leftPad(String.valueOf(hashCode), 12, '0');

        // UUID编码
        return StringUtil.concat("", modelCode, currentYearStr, hashCodeStr, DigitalUtil.getRandomDigital(1));
    }

    /**
     * 判断字符串是否为空
     * 
     * @param value
     * @return
     */
    public static boolean isNull(String value) {
        return value == null || value.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     * 
     * @param value
     * @return
     */
    public static boolean isNotNull(String value) {
        return value != null && value.length() > 0;
    }

    /**
     * 替换参数
     *
     * @param src
     * @param params
     * @return
     */
    public static String replaceParams(String src, Map<String, Object> params) {
        if (isNotNull(src) && params != null && params.size() > 0) {
            for (Entry<String, Object> param : params.entrySet()) {
                src = src.replaceAll("@\\{" + param.getKey() + "\\}", String.valueOf(param.getValue()));
            }
        }
        return src;
    }

    /**
     * 连接字符串
     *
     * @param center
     * @param objects
     * @return
     */
    public static String concat(String center, Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            sb.append(objects[i]);
            if (i == (objects.length - 1)) {
                continue;
            }
            if (!isEmpty(center)) {
                sb.append(center);
            }
        }
        return sb.toString();
    }

    /**
     * 获取字符串编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    /**
     * 手机号中间四位保密
     *
     * @param phone
     * @return
     */
    public static String blurPhone(long phone) {
        if (isMobilePhone(phone)) {
            String phoneStr = String.valueOf(phone);
            return phoneStr.substring(0, 3) + "****" + phoneStr.substring(7);
        }
        return "";
    }

    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean isEmail = false;

        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        isEmail = m.find();

        return isEmail;
    }

    /**
     * @param phone
     * @return
     */
    public static boolean isMobilePhone(long phoneNum) {
        return isMobilePhone(String.valueOf(phoneNum));
    }

    /**
     * @param phoneNum
     * @return
     */
    public static boolean isMobilePhone(String phoneNum) {
        boolean isMobilePhone = false;
        String regExp = "(^[1][3,4,5,7,8][0-9]{9}$)|(^9\\d{8}$)";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNum);
        isMobilePhone = m.find();
        return isMobilePhone;
    }

    /**
     * 获取空串,主要应用于URLpath参数设置.
     *
     * @param str
     * @return
     */
    public static String getNullStrByString(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

}
