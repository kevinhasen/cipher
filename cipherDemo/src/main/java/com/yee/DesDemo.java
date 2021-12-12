package com.yee;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * ClassName: DesAesDemo
 * Description:
 * date: 2021/12/10 14:30
 * des和aes是一样的,把算法和转换换成aes即可,其他不用改
 * 由于输出有负数,所以需要使用base64转码
 * @author Yee
 * @since JDK 1.8
 */
public class DesDemo {
    public static void main(String[] args) throws Exception{

        //加密原文
        String input = "叶生";
        //key,如果使用的是des,必须是8位
        String key = "13579246";
        //要使用的算法
        String algorithm = "DES";
        //要转换成什么加密格式
        String transformation = "DES";
        //调用加密方法
        String encrypt = encryptDES(input, key, algorithm, transformation);
        System.out.println("加密之后:"+encrypt);
        //调用解密方法
        String decrypt = decryptDES(encrypt, key, algorithm, transformation);
        System.out.println("解密之后:"+decrypt);
    }

    /**
     * 抽取加密方法
     * @param input  原文
     * @param key    密钥,DES密钥长度必须是8个字符
     * @param algorithm  密钥算法
     * @param transformation   Cipher对象的算法
     * @return   返回密文
     * @throws Exception
     */
    private static String encryptDES(String input, String key, String algorithm, String transformation) throws Exception {
        //加密对象,需要传入一个加密格式
        Cipher cipher = Cipher.getInstance(transformation);
        //指定密钥规则,需要传入key的字节数组和加密的算法
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        /**
         * 初始化密码,根据选择的模式判断是加密还是解密
         * 第二个参数是密钥
         */
        cipher.init(Cipher.ENCRYPT_MODE,keySpec);
        //进行加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        //由于加密后字节码有负数无法显示
        //需要使用base64转码可显示加密内容
        String encode = Base64.encode(bytes);
        return encode;
    }

    /**
     * 抽取解密方法
     * @param input    密文
     * @param key     密钥
     * @param algorithm   密钥算法
     * @param transformation   cipher算法
     * @return    返回原文
     * @throws Exception
     */
    private static String decryptDES(String input, String key, String algorithm, String transformation) throws Exception{
        //获得解密对象
        Cipher cipher = Cipher.getInstance(transformation);
        //密钥规则
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        //密码初始化
        cipher.init(Cipher.DECRYPT_MODE,keySpec);
        //解密
        byte[] bytes = cipher.doFinal(Base64.decode(input));
        return new String(bytes);
    }
}
