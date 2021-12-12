package com.yee;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * ClassName: RSAdemo
 * Description:
 * date: 2021/12/10 22:24
 * 非对称加密算法
 * 公钥和私钥
 * @author Yee
 * @since JDK 1.8
 */
public class RSAdemo {
    public static void main(String[] args) throws Exception {

        //要加密的原文
        String input = "叶生";
        //加密算法
        String algorithm = "RSA";

        //保存密钥到本地,这里没加前缀,默认是根目录,没有该文件会自动创建
        generateKeyToFile(algorithm,"publicKey.pub","privateKey.pri");
        //读取本地私钥
      //  PrivateKey privateKey = getPrivateKey("privateKey.pri",algorithm);
        //读取本地公钥
       // PublicKey publicKey = getPublicKey("publicKey.pub",algorithm);
    }

    /**
     *  读取公钥
     * @param pulickPath  公钥路径
     * @param algorithm  算法
     * @return
     */
    public static PublicKey getPublicKey(String pulickPath, String algorithm) throws Exception {
        //文件转为字符串
        String publicKeyStr = FileUtils.readFileToString(new File(pulickPath), Charset.defaultCharset());
        //读取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //base64解码,这个类表示私钥,是一个规则,专用于公钥转码
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(publicKeyStr));
        //生成公钥,传入规则
        return keyFactory.generatePublic(spec);
    }


    /**
     *  读取私钥
     * @param priPath   密钥路径
     * @param algorithm  算法
     * @return  返回私钥
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String priPath,String algorithm) throws Exception {
        //将文件内容转为字符串,按照默认方式,怎么存的,怎么取
        String privateKeyStr = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        //获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //64解码,这个类表示私钥,是一个规则,专用于私钥转码
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyStr));
        //生成私钥,传入一个规则
        return keyFactory.generatePrivate(spec);
    }

    /**
     *
     * @param algorithm  加密算法
     * @param pubPath   公钥存储路径
     * @param priPath   私钥存储路径
     * @throws Exception
     */
    public static  void generateKeyToFile(String algorithm,String pubPath,String priPath) throws Exception {
        //密钥生成器
        KeyPairGenerator instance = KeyPairGenerator.getInstance(algorithm);
        //生成密钥对
        KeyPair keyPair = instance.generateKeyPair();
        //生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        //生成公钥
        PublicKey publicKey = keyPair.getPublic();
        //获得私钥字节
        byte[] privateKeyEncoded = privateKey.getEncoded();
        //获得公钥字节
        byte[] publicKeyEncoded = publicKey.getEncoded();
        //密钥进行转码
        String privateKeyStr = Base64.encode(privateKeyEncoded);
        String publickKeyStr = Base64.encode(publicKeyEncoded);
        /**
         *  保存公钥和私钥,使用工具类.pom已经导入
         * 1,保存的路径
         * 2,保存的数据
         * 3,编码的格式
         */
        FileUtils.writeStringToFile(new File(pubPath),publickKeyStr, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(priPath),privateKeyStr, Charset.forName("UTF-8"));
    }

    /**
     *  加密
     * @param algorithm  算法
     * @param key   密钥
     * @param input   原文
     * @return   返回密文
     * @throws Exception
     */
    public static String encrypRSA(String algorithm, Key key, String input) throws Exception{
        //私钥加密,创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        //选择加密模式,和key
        cipher.init(Cipher.ENCRYPT_MODE,key);
        //私钥进行加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        //64编码
       return Base64.encode(bytes);
    }

    /**
     *  解密
     * @param algorithm  算法
     * @param key     密钥
     * @param encrypted    密文
     * @return    返回原文
     * @throws Exception
     */
    public static String decryptRSA(String algorithm,Key key,String encrypted) throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        // 私钥进行解密
        cipher.init(Cipher.DECRYPT_MODE,key);
        // 由于密文进行了Base64编码, 在这里需要进行解码
//        byte[] decode = Base64.decode(encrypted);
        //密文解密
        byte[] bytes1 = cipher.doFinal(encrypted.getBytes());
        return new String(bytes1);
    }
}
