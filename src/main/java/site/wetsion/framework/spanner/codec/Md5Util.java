package site.wetsion.framework.spanner.codec;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {
    public static String md5Hex(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    public static String md5Hex(String text) {
        return DigestUtils.md5Hex(text);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
     * @param data
     * @return
     */
    public static byte[] md5(String data) {
        return DigestUtils.md5(data);
    }


    public static int toHash(String key) {
        int arraySize = 11113; // 数组大小一般取质数
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) { // 从字符串的左边开始计算
            int letterValue = key.charAt(i) - 96;// 将获取到的字符串转换成数字，比如a的码值是97，则97-96=1
            // 就代表a的值，同理b=2；
            hashCode = ((hashCode << 5) + letterValue) % arraySize;// 防止编码溢出，对每步结果都进行取模运算
        }
        if(hashCode>0){
            return hashCode;
        }else{
            return -hashCode;
        }
    }
}
