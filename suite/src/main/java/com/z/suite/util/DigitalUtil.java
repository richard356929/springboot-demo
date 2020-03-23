package com.z.suite.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class DigitalUtil {

    /**
     * 获取ID：机器码（1位）＋时间戳（13位）＋随机数（4位）
     * 
     * @return
     */
    public static long getID(int uniqueId) {
        StringBuffer sb = new StringBuffer();
        // 机器码小于0时，做0处理
        if (uniqueId <= 0) {
            uniqueId = 1;
        }
        // 机器码大于1位数时，取个位数
        if (uniqueId > 9) {
            uniqueId = uniqueId % 9 + 1;
        }
        // 时间戳（13位）
        long timestamp = System.currentTimeMillis();
        // 随机数（4位）
        String randomNum = getRandomDigital(4);

        sb.append(uniqueId).append(timestamp).append(randomNum);
        return Long.parseLong(sb.toString());
    }

    /**
     * 获取指定位数的随机数字，不足位数，前补零
     * 
     * @param digit
     *        位数
     * @return
     */
    public static String getRandomDigital(int digit) {
        int num = (int) (Math.random() * Math.pow(10, digit));
        int differential = digit - String.valueOf(num).length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < differential; i++) {
            sb.append("0");
        }
        return sb.append(num).toString();
    }

    /**
     * 获取最大数为maxValue的随机数
     *
     * @param maxValue
     * @return
     */
    public static int getMaxRandom(int maxValue) {
        return (int) (Math.random() * maxValue);
    }

    /**
     * 获取最大数为maxValue的随机数
     *
     * @param maxValue
     * @return
     */
    public static float getMaxRandom(float maxValue) {
        return (float) (Math.random() * maxValue);
    }

    /**
     * 四舍五入保留数字
     *
     * @param num
     * @param digit
     * @return
     */
    public static double roundNum(double num, int digit) {
        BigDecimal b = new BigDecimal(num);
        double numValue = b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return numValue;
    }

    /**
     * 四舍五入保留数字
     *
     * @param num
     * @param digit
     * @return
     */
    public static float roundNum(float num, int digit) {
        BigDecimal b = new BigDecimal(num);
        float numValue = b.setScale(digit, BigDecimal.ROUND_HALF_UP).floatValue();
        return numValue;
    }

    /**
     * 获取百分比
     *
     * @param data:数据源
     * @param IntegerDigits:小数点前保留位数,默认为2
     * @param fractionDigits:小数点后保留位数,默认为2
     * @return 如果返回的数据小于0.01%,则返回对应的0.01%;反之,则返回正确的百分比.
     */
    public static String getPercentForFloat(double data, int IntegerDigits, int fractionDigits) {
        if (IntegerDigits <= 0)
            IntegerDigits = 2;
        if (fractionDigits <= 0)
            fractionDigits = 2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(IntegerDigits);
        nf.setMinimumFractionDigits(fractionDigits);
        if (data < 0.0001)
            return "0.01%";
        return nf.format(data);
    }

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
