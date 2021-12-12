package com.yee;

import java.security.MessageDigest;

/**
 * ClassName: DigestDemo
 * Description:
 * date: 2021/12/10 18:58
 * 信息摘要,不需要使用base64,使用16进制替换负数
 *
 * @author Yee
 * @since JDK 1.8
 */
public class DigestDemo {
    public static void main(String[] args) throws Exception {
        //原文
        String input = "abc";
        //加密算法
        String algorithm = "MD5";
        //获得信息摘要
        MessageDigest instance = MessageDigest.getInstance(algorithm);
        //获得字节数组
        byte[] digest = instance.digest(input.getBytes());
        //转成base64和md5不合适,使用16进制,去掉高位
        //留最低8位,可以解决负数问题
        //拼接md5
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            String str = Integer.toHexString(b & 0xff);
            //不足两位补0,否则生成的md5长度缺失
            if (str.length() == 1){
                str = "0"+str;
            }
            sb.append(str);
        }
        System.out.println(sb.toString());
    }
}
