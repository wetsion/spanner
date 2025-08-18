package site.wetsion.framework.spanner.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Base64;

public class AesUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String charsetName = "UTF-8";

    private static final Integer IVSize = 16;
    private static final int SecretKeySize = 16;


    /**
     * AES CBC 加密
     *
     * @param message 需要加密的字符串
     * @param key     密匙
     * @param iv      IV，需要和key长度相同
     * @return 返回加密后密文，编码为base64
     */
    public static String encryptCBC(String message, String key, String iv) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            byte[] content = new byte[0];
            content = message.getBytes(charsetName);

            SecretKeySpec keySpec = createKey(key);
            IvParameterSpec ivSpec = createIV(iv);

            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] data = cipher.doFinal(content);
            final Base64.Encoder encoder = Base64.getEncoder();
            final String result = encoder.encodeToString(data);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("AESUtil.encryptCBC error, message=" + message + ",key=" + key + ",iv=" + iv, e);
        }
    }

    /**
     * AES CBC 解密
     *
     * @param messageBase64 密文，base64编码
     * @param key           密匙，和加密时相同
     * @param iv            IV，需要和key长度相同
     * @return 解密后数据
     */
    public static String decryptCBC(String messageBase64, String key, String iv) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final Base64.Decoder decoder = Base64.getDecoder();
            byte[] messageByte = decoder.decode(messageBase64);

            SecretKeySpec keySpec = createKey(key);
            IvParameterSpec ivSpec = createIV(iv);

            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] content = cipher.doFinal(messageByte);
            String result = new String(content, charsetName);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("AESUtil.decryptCBC error, messageBase64=" + messageBase64 + ",key=" + key + ",iv=" + iv, e);
        }
    }


    /**
     * 创建密钥
     *
     * @return
     */
    private static SecretKeySpec createKey(String secretKey) {
        StringBuilder sb = new StringBuilder(SecretKeySize);
        sb.append(secretKey);
        if (secretKey.length() != 16) {
            throw new RuntimeException("secretKey length must be 16!");
        }
        try {
            byte[] data = sb.toString().getBytes(charsetName);
            return new SecretKeySpec(data, KEY_ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建16位向量: 不够则用0填充
     *
     * @return
     */
    private static IvParameterSpec createIV(String iv) {
        StringBuffer sb = new StringBuffer(IVSize);
        sb.append(iv);
        if (iv.length() != 16) {
            throw new RuntimeException("iv length must be 16!");
        }
        byte[] data = null;
        try {
            data = sb.toString().getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }


    /**
     * AES ECB 加密
     *
     * @param message 需要加密的字符串
     * @param key     密匙
     * @return 返回加密后密文，编码为base64
     */
    public static String encryptECB(String message, String key) {
        try {
            byte[] content = new byte[0];
            content = message.getBytes(charsetName);
            //
            byte[] keyByte = key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, KEY_ALGORITHM);

            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] data = cipher.doFinal(content);
            final Base64.Encoder encoder = Base64.getEncoder();
            final String result = encoder.encodeToString(data);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("AESUtil.encryptECB error, message=" + message + ",key=" + key, e);
        }
    }

    /**
     * AES ECB 解密
     *
     * @param messageBase64 密文，base64编码
     * @param key           密匙，和加密时相同
     * @return 解密后数据
     */
    public static String decryptECB(String messageBase64, String key) {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            byte[] messageByte = decoder.decode(messageBase64);

            byte[] keyByte = key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, KEY_ALGORITHM);

            Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] content = cipher.doFinal(messageByte);
            String result = new String(content, charsetName);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("AESUtil.decryptECB error, messageBase64=" + messageBase64 + ",key=" + key, e);
        }
    }
}
