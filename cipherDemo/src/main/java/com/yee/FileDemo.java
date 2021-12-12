package com.yee;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName: FileDomo
 * Description:
 * date: 2021/12/10 19:41
 * 文件的信息摘要,由于查询文件有没有被更改
 *
 * @author Yee
 * @since JDK 1.8
 */
public class FileDemo {
    public static void main(String[] args) throws Exception {
        //加密方式
        String algorithm = "sha-512";
        //文件路径
        FileInputStream fis = new FileInputStream("F:\\apache-tomcat-8.5.73-windows-x64.zip");
        String file = getDigestFile(algorithm, fis);
        System.out.println("文件的sha-512:"+file);
    }

    private static String getDigestFile(String algorithm, FileInputStream fis) throws IOException, NoSuchAlgorithmException {
        //记录读取的长度
        int len ;
        //每次读1024字节
        byte[] buffer = new byte[1024];
        //字节流存储
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((len = fis.read(buffer)) != -1){
            //要写入的数组,从0开始,到len长度
            outputStream.write(buffer,0,len);
        }
        //获得信息摘要
        MessageDigest instance = MessageDigest.getInstance(algorithm);
        // 在缓冲区的内容转换为字节数组
        byte[] digest = instance.digest(outputStream.toByteArray());
        return toHex(digest);
    }

    //信息摘要转为16进制
    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String s = Integer.toHexString(b & 0xff);
            // 保持数据的完整性，前面不够的用0补齐
            if (s.length()==1){
                s="0"+s;
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
