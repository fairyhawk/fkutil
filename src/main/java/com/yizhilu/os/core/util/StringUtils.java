package com.yizhilu.os.core.util;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName com.supergenius.sns.util.StringUtils
 * @description 字符串工具类
 * @author : qinggang.liu 305050016@qq.com
 * @Create Date : 2013-12-13 下午2:23:56
 */
public class StringUtils {

    /**
     * 手机号中奖4位加星号
     * 
     * @author liuqinggang
     * @param mobile
     * @return String
     * 
     */
    public static String starMobile(String mobile) {
        if (mobile.length() == 11) {
            String starmobile = String.valueOf(mobile.charAt(0))
                    + String.valueOf(mobile.charAt(1)) + String.valueOf(mobile.charAt(2))
                    + "****" + String.valueOf(mobile.charAt(7))
                    + String.valueOf(mobile.charAt(8)) + String.valueOf(mobile.charAt(9))
                    + String.valueOf(mobile.charAt(10));
            return starmobile;
        }
        return mobile;
    }

    /**
     * 生成指定长度的随机字符串
     * 
     * @author liuqinggang
     * @param strLength
     * @return
     */
    public static String getRandomString(int strLength) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < strLength; i++) {
            int charInt;
            char c;
            if (random.nextBoolean()) {
                charInt = 48 + random.nextInt(10);
                c = (char) charInt;
                buffer.append(c);
                continue;
            }
            charInt = 65;
            if (random.nextBoolean())
                charInt = 65 + random.nextInt(26);
            else
                charInt = 97 + random.nextInt(26);
            if (charInt == 79)
                charInt = 111;
            c = (char) charInt;
            buffer.append(c);
        }

        return buffer.toString();
    }

    /**
     * MD5加密方法
     * 
     * @author liuqinggang
     * @param str
     *            String
     * @return String
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        byte newByte1[] = str.getBytes();
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            byte newByte2[] = messagedigest.digest(newByte1);
            String cryptograph = "";
            for (int i = 0; i < newByte2.length; i++) {
                String temp = Integer.toHexString(newByte2[i] & 0x000000ff);
                if (temp.length() < 2)
                    temp = "0" + temp;
                cryptograph += temp;
            }
            return cryptograph;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证Email地址是否有效
     * 
     * @author liuqinggang
     * @param sEmail
     * @return boolean
     */
    public static boolean validEmail(String sEmail) {
        String pattern = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return sEmail.matches(pattern);
    }

    /**
     * 验证字符是否大长
     * 
     * @author liuqinggang
     * @param str
     * @return
     */
    public static boolean validMaxLen(String str, int length) {
        if (str == null || str.equals("")) {
            return false;
        }
        if (str.length() > length) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证字符是否长度太小
     * 
     * @author liuqinggang
     * @param str
     * @return boolean
     */
    public static boolean validMinLen(String str, int length) {
        if (str == null || str.equals("")) {
            return false;
        }
        if (str.length() < length) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证两个字符串是否相等且不能为空
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null || str1.equals("") || str2 == null || str2.equals("")) {
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * 将字符型转为Int
     * 
     * @param str
     * @return
     */
    public static int toInt(String str) {
        int value = 0;
        if (str == null || str.equals("")) {
            return 0;
        }
        try {
            value = Integer.parseInt(str);
        } catch (Exception ex) {
            ex.printStackTrace();
            value = 0;
        }
        return value;
    }

    /**
     * 把数组转换成String
     * 
     * @param array
     * @return
     */
    public static String arrayToString(Object[] array, String split) {
        if (array == null) {
            return "";
        }
        StringBuffer str = new StringBuffer("");
        for (int i = 0; i < array.length; i++) {
            if (i != array.length - 1) {
                str.append(array[i].toString()).append(split);
            } else {
                str.append(array[i].toString());
            }
        }
        return str.toString();
    }

    /**
     * 得到WEB-INF的绝对路�?
     * 
     * @return
     */
    public static String getWebInfPath() {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("")
                .toString();
        if (filePath.toLowerCase().indexOf("file:") > -1) {
            filePath = filePath.substring(6, filePath.length());
        }
        if (filePath.toLowerCase().indexOf("classes") > -1) {
            filePath = filePath.replaceAll("/classes", "");
        }
        if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
            filePath = "/" + filePath;
        }
        if (!filePath.endsWith("/"))
            filePath += "/";
        return filePath;
    }

    /**
     * 得到根目录绝对路�?(不包含WEB-INF)
     * 
     * @return
     */
    public static String getRootPath() {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("")
                .toString();
        if (filePath.toLowerCase().indexOf("file:") > -1) {
            filePath = filePath.substring(6, filePath.length());
        }
        if (filePath.toLowerCase().indexOf("classes") > -1) {
            filePath = filePath.replaceAll("/classes", "");
        }
        if (filePath.toLowerCase().indexOf("web-inf") > -1) {
            filePath = filePath.substring(0, filePath.length() - 9);
        }
        if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
            filePath = "/" + filePath;
        }

        if (filePath.endsWith("/"))
            filePath = filePath.substring(0, filePath.length() - 1);

        return filePath;
    }

    /**
     * 格式化页�?
     * 
     * @param page
     * @return
     */
    public static int formatPage(String page) {
        int iPage = 1;
        if (page == null || page.equals("")) {
            return iPage;
        }
        try {
            iPage = Integer.parseInt(page);
        } catch (Exception ex) {
            ex.printStackTrace();
            iPage = 1;
        }
        return iPage;
    }

    /**
     * 将计量单位字节转换为相应单位
     * 
     * @param size
     * @return
     */
    public static String getFileSize(String fileSize) {
        String temp = "";
        DecimalFormat df = new DecimalFormat("0.00");
        double dbFileSize = Double.parseDouble(fileSize);
        if (dbFileSize >= 1024) {
            if (dbFileSize >= 1048576) {
                if (dbFileSize >= 1073741824) {
                    temp = df.format(dbFileSize / 1024 / 1024 / 1024) + " GB";
                } else {
                    temp = df.format(dbFileSize / 1024 / 1024) + " MB";
                }
            } else {
                temp = df.format(dbFileSize / 1024) + " KB";
            }
        } else {
            temp = df.format(dbFileSize / 1024) + " KB";
        }
        return temp;
    }

    /**
     * 得到32位随机字
     * 
     * @return
     */
    public static String getEntry() {
        Random random = new Random(100);
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(new String("yyyyMMddHHmmssS"));
        return md5(formatter.format(now) + random.nextDouble());
    }

    /**
     * 将中文汉字转成UTF8编码
     * 
     * @param str
     * @return
     */
    public static String toUTF8(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        try {
            return new String(str.getBytes("ISO8859-1"), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String to(String str, String charset) {
        if (str == null || str.equals("")) {
            return "";
        }
        try {
            return new String(str.getBytes("ISO8859-1"), charset);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 得到10个数字的大写，0-9
     * 
     * @param num
     * @return
     */
    public static String getChineseNum(int num) {
        String[] chineseNum = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌",
                "玖" };
        return chineseNum[num];
    }

    public static String replaceEnter(String str) {
        if (str == null)
            return null;
        return str.replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 去除HTML 元素
     * 
     * @param element
     * @return
     */
    public static String getTxtWithoutHTMLElement(String element) {
        if (null == element) {
            return element;
        }
        Pattern pattern = Pattern.compile("<[^<|^>]*>");
        Matcher matcher = pattern.matcher(element);
        StringBuffer txt = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            if (group.matches("<[\\s]*>")) {
                matcher.appendReplacement(txt, group);
            } else {
                matcher.appendReplacement(txt, "");
            }
        }
        matcher.appendTail(txt);
        String temp = txt.toString().replaceAll("[\r|\n]", "");
        // 多个连续空格替换为一个空格
        temp = temp.replaceAll("\\s+", " ");
        return temp;
    }

    /**
     * clear trim to String
     * 
     * @return
     */
    public static String toTrim(String strtrim) {
        if (null != strtrim && !strtrim.equals("")) {
            return strtrim.trim();
        }
        return "";
    }

    /**
     * UUID
     */
    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 按传入字数截断并加结尾符号
     * 
     * @param sourceStr
     * @param length
     * @param charactor
     * @return
     */
    public static String cutffStr(String sourceStr, int length, String charactor) {
        String resultStr = sourceStr;
        if (sourceStr == null || "".equals(sourceStr)) {

            return "";
        }
        if (sourceStr.length() > length) {
            resultStr = sourceStr.substring(0, length);
            resultStr += charactor;

        }

        return resultStr;

    }

    public static boolean isNumber(String str) {
        if (str == null || str.trim().equals("")) {
            return false;
        }
        boolean flag = false;
        try {
            Long.parseLong(str);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static String getLength(Object goodsName, int length) {
        if (goodsName == null) {
            return null;
        } else {
            String temp = String.valueOf(goodsName);
            if (temp.length() <= length) {
                return temp;
            } else {
                temp = temp.substring(0, length) + "...";
                return temp;
            }
        }
    }

    /**
     * 替换email中@符号前的一半字符为*号
     * 
     * @param email
     * @return
     */
    public static String handleEmail(String email) {
        if (email == null) {
            return "";
        } else {
            String[] aryEmail = email.split("@");
            if (aryEmail != null && aryEmail.length == 2) {
                if (aryEmail[0] != null) {
                    String firstPart = aryEmail[0].substring(aryEmail[0].length() / 2,
                            aryEmail[0].length());
                    if (firstPart != null && !"".equals(firstPart)) {
                        char repeatChar[] = new char[firstPart.length()];
                        for (int i = 0; i < firstPart.length(); i++) {
                            repeatChar[i] = '*';
                        }
                        email = email.replaceFirst(firstPart + "@",
                                new String(repeatChar) + "@");
                    }
                }
            }
        }
        return email;
    }

    private static final String regex_mobile = "^1\\d{10}$";

    /**
     * 
     * @author liuqinggang
     * @param tocheckNo
     * @return 手机号码校验
     * 
     */
    public static boolean isMobileNo(String tocheckNo) {
        return Pattern.matches(regex_mobile, tocheckNo);
    }

    private static final String regex_digital = "^[1-9]\\d{0,}";

    /**
     * 
     * @author liuqinggang
     * @param source
     * @param ingoreDigital
     *            忽略数字校验
     * @return
     * 
     */
    public static boolean neNullAndDigital(String source, boolean ingoreDigital,
            Integer length) {
        boolean isvalid = false;
        if (source != null && !"".equals(source.trim())) {
            isvalid = true;
        }
        if (!ingoreDigital && isvalid) {
            isvalid = Pattern.matches(regex_digital, source);
        }
        if (isvalid && length != null) {
            isvalid = source.trim().length() <= length;
        }
        return isvalid;
    }

}