package com.mirkowu.smarthome.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 偏移量，只有CBC模式才需要
     */
//    private final static String ivParameter = "0000000000000000";

    /**
     * AES要求密钥长度为128位或192位或256位，java默认限制AES密钥长度最多128位
     */
    public static String sKey = "";

    /**
     * 编码格式
     */
    public static final String ENCODING = "utf-8";


    static {
        //如果是PKCS7Padding填充方式，则必须加上下面这行
        Security.addProvider(new BouncyCastleProvider());
    }

    private static byte[] decrypted;

    /**
     * AES加密
     *
     * @param source 源字符串
     * @param key    密钥
     * @return 加密后的密文
     * @throws Exception
     */
    public static String encrypt(String source, String key, String ivParameter) throws Exception {
        byte[] sourceBytes = source.getBytes(ENCODING);
        byte[] keyBytes = key.getBytes(ENCODING);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(ENCODING));
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM), iv);
        decrypted = cipher.doFinal(sourceBytes);

        int data[] = new int[decrypted.length];
        for (int i = 0; i < decrypted.length; i++) {
            if (decrypted[i] < 0) {
                data[i] = decrypted[i] + 256;
            } else {
                data[i] = decrypted[i];
            }
        }

        return bytesToHex(data).toUpperCase();
    }

    private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将字节数组转换为16进制字符串
     *
     * @param bcd
     * @return
     */
    public static final String bytesToHex(int[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }

    /**
     * 将16进制字符串转换为字节数组
     *
     * @param s
     * @return
     */
    public static final byte[] hexToBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }

        return bytes;
    }

    /**
     * AES解密
     *
     * @param encryptStr 加密后的密文
     * @param key        密钥
     * @return 源字符串
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String key, String ivParameter) throws Exception {
        byte[] sourceBytes = hexToBytes(encryptStr);
        byte[] keyBytes = key.getBytes(ENCODING);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes(ENCODING));
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM), iv);
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, ENCODING);
    }

  /*  public static void main(String[] args) throws Exception {
        String key = "c993646556c4414fbce72710c8161410";

        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AESUtils.encrypt("1592285624689", key, "c993646556c4414f");
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");

        // 解密
        lStart = System.currentTimeMillis();
        String DeString = AESUtils.decrypt(enString, key, "c993646556c4414f");
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }*/
}
