package com.yee;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * ClassName: SignatureDemo
 * Description:
 * date: 2021/12/11 11:00
 * 数字签名,采用现在流行的sha-512和rsa
 * @author Yee
 * @since JDK 1.8
 */
public class SignatureDemo {
    public static void main(String[] args) throws Exception {
        //原文
        String input = "叶生";
        //密钥算法
        String algorithm = "RSA";
        //签名算法
        String singn = "sha256withrsa";
        //读取本地私钥
          PrivateKey privateKey = RSAdemo.getPrivateKey("privateKey.pri",algorithm);
        //读取本地公钥
         PublicKey publicKey =  RSAdemo.getPublicKey("publicKey.pub",algorithm);
         //生成签名,使用私钥生成
        String signatured = getSignature(input,singn,privateKey);
        //校验签名
        boolean flage = verifySignature(input,singn,publicKey,signatured);
    }

    /**
     * 校验签名
     * @param input   原文
     * @param algorithm  算法
     * @param publicKey  公钥
     * @param signatured  签名
     * @return
     */
    private static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signatured) throws Exception {
        //获取签名
        Signature signature = Signature.getInstance(algorithm);
       //初始化签名
        signature.initVerify(publicKey);
        //传入原文
        signature.update(input.getBytes());
        //校验签名
        return signature.verify(Base64.decode(signatured));
    }

    /**
     *  生成签名
     * @param input  原文
     * @param algorithm  算法,签名常见的就sha256withrsa
     * @param privateKey  私钥
     * @return
     */
    private static String getSignature(String input, String algorithm, PrivateKey privateKey) throws Exception {
    //获得签名对象
        Signature signature = Signature.getInstance(algorithm);
        //初始化签名
        signature.initSign(privateKey);
        //更新原文
        signature.update(input.getBytes());
        // 开始签名
        byte[] sign = signature.sign();
        //对签名就行64编码
        return Base64.encode(sign);
    }


}
