package com.hengan.news.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * String工具类
 *
 * @author kamera
 * @Date 2017-09-21
 */
public class StringUtil {

    //字符串对比
    public static boolean IsMatch(Object left0, boolean isRegexp, Object right0, boolean caseSensitive) {
        if (left0 == null)
            return false;
        if (right0 == null)
            return false;
        //一定是一个String类型
        String left = left0.toString();
        String right = right0.toString();
        left = left.trim();
        right = right.trim();
        //当正常的字符串对比的时候, 如果大小写不敏感,则转换为小写
        if (!caseSensitive && !isRegexp) {
            left = left.toLowerCase();
            right = right.toLowerCase();
        }
        if (right != null) {
            if (isRegexp) {
                return left.matches(right);
            } else {
                return left.equals(right);
            }
        }
        return true;
    }
    //对字符串进行对比
    public static boolean IsMatch(Object left0, boolean isRegexp, Object right0) {
        return IsMatch(left0, isRegexp, right0, true);
    }
    //是否是一个单词
    public static boolean IsWord(Object left) {
        return IsMatch(left, true, "^\\w+$");
    }
    //不包含空的字符(制表符, 换行, 空格 ..)
    public static boolean IsNotWithBlank(Object left) {
        return IsMatch(left, true, "^\\S+$");
    }
    //从流中读取一个String
    //不负责关闭io流
    public static String ReadStream(InputStream is) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        in.close();
        return buffer.toString();
    }

    //判断字符串是否为空
    public static boolean isBlank(Object str){
        return str==null || "".equals(str.toString().trim())||"null".equals(str.toString().trim());
    }

    //将ISO-8859-1转UTF-8
    public static String getUTF8Code(String str) throws UnsupportedEncodingException{
        if(!isBlank(str) && java.nio.charset.Charset.forName("ISO-8859-1").newEncoder().canEncode(str)){
            return new String(str.getBytes("ISO-8859-1"),"UTF-8");
        }
        return str;
    }

}
