package com.lss.duolejie_seller.utils;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import java.security.Key;
public class AESUtil {
    /** 密钥算法 */
    private static final String KEY_ALGORITHM = "AES";
    /** 秘钥长度 */
    private static final int KEY_SIZE = 128;
    /** 秘钥 */
    private static final String KEY_STR = "9mtTU3mgo0Mm2Aox";
    
    /** 加密/解密算法/工作模式/填充方法 */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";


    /**
     * 获取密钥
     * @return
     * @throws Exception
     */
    public static Key getKey() throws Exception{
        //实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES 要求密钥长度为128位、192位或256位
        kg.init(KEY_SIZE);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey;
    }

    /**
     * 转化密钥
     * @param key 密钥
     * @return Key 密钥
     * @throws Exception
     */
    public static Key codeToKey(String key) throws Exception{
        byte[] keyBytes = key.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    private static String decrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k = new SecretKeySpec(key,KEY_ALGORITHM);
        /**
         * 实例化
         * 使用PKCS7Padding填充方式，按如下方式实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置解密模式
        cipher.init(Cipher.DECRYPT_MODE,k);
        //执行操作
        return new String(cipher.doFinal(data),"UTF-8");
    }

    /**
     * 解密
     * @param data 待解密数据
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception{
        return null;
    }

    /**
     * 加密
     * @param data 待加密数据
     * @return bytes[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k = new SecretKeySpec(key,KEY_ALGORITHM);
        /**
         * 实例化
         * 使用PKCS7Padding填充方式，按如下方式实现
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE,k);
        //执行操作
        return cipher.doFinal(data);
    }

    public static String encrypt(String data) throws Exception{
        byte[] dataBytes = data.getBytes("UTF-8");
        byte[] keyBytes = KEY_STR.getBytes("UTF-8");
        return android.util.Base64.encodeToString(encrypt(dataBytes, keyBytes),
        		android.util.Base64.URL_SAFE|android.util.Base64.NO_WRAP|android.util.Base64.NO_PADDING);
//        return new String(android.util.Base64.encode(encrypt(dataBytes, keyBytes), android.util.Base64.URL_SAFE),"utf-8");
    }/*Base64.encodeToString(param.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP|Base64.NO_PADDING)*/
//    new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP),"utf-8")
    /**
     * 初始化密钥
     * @return
     * @throws Exception
     */
/*    public static String getKeyStr() throws Exception{
        return org.apache.commons.codec.binary.Base64.encodeBase64String(getKey().getEncoded());
    }
    */
    public static void main(String args) throws Exception{
//    	System.out.println("新的秘钥:" + getKeyStr());
    	Long time =System.currentTimeMillis();
    	System.out.println(time);
     	String miwen="1|$|10|$|11671|$|"+time.toString();
//     	String miwen=time.toString()+"|$|";
//    	String miwen="10002|$|152,153,154,155,156,157,158,159,160,161,162|$|1100|$|1|$|1|$|"+time.toString();
    	String mw = AESUtil.encrypt(miwen);
        System.out.println("密文:" + mw);
        String jm = AESUtil.decrypt(mw);
        System.out.println("明文:" + jm);
        System.out.println("明文长度:" + jm.length());
       
//        GregorianCalendar now = new GregorianCalendar();
//        SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
//        DateFormat df = DateFormat.getDateInstance();
//        System.out.println(fmtrq.format(now.getTime()));
//        now.add(GregorianCalendar.MONTH,+24);
//        System.out.println(fmtrq.format(now.getTime()));
//        String str=fmtrq.format(now.getTime());
//        System.out.println(str.substring(5, 7));
        
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//        String str1 = "2011-01";
//        String str2 = "2010-11";
//        Calendar bef = Calendar.getInstance();
//        Calendar aft = Calendar.getInstance();
//        bef.setTime(sdf.parse(str1));
//        aft.setTime(sdf.parse(str2)); 
//        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
//        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
//        System.out.println(Math.abs(month + result));
    }
}