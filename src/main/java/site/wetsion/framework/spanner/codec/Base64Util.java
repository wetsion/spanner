package site.wetsion.framework.spanner.codec;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    public static String encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String encodedText) {
        return new String(Base64.getDecoder().decode(encodedText), StandardCharsets.UTF_8);
    }

    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 将byte数组内容用base64编码成uri可以直接引用的字符串内容
     * 如应用于html中的img标签：<img src="{编码返回的数据}">
     * @param bytes 需要编码的内容
     * @param type 数据类型，如：image/jpeg
     * Data URI scheme支持的类型有:
     *     data:,<文本数据>
     *     data:text/plain,<文本数据>
     *     data:text/html,<HTML代码>
     *     data:text/html;base64,<base64编码的HTML代码>
     *     data:text/plain;charset=UTF-8;base64,<base64编码的HTML代码>
     *     data:text/css,<CSS代码>
     *     data:text/css;base64,<base64编码的CSS代码>
     *     data:text/javascript,<Javascript代码>
     *     data:text/javascript;base64,<base64编码的Javascript代码>
     *     data:image/gif;base64,<base64编码的gif图片数据>
     *     data:image/png;base64,<base64编码的png图片数据>
     *     data:image/jpeg;base64,<base64编码的jpeg图片数据>
     *     data:image/x-icon;base64,<base64编码的icon图片数据>

     * @return
     */
    public static String encodeDataUri(byte[] bytes, String type) {
        return "data:" + type + ";base64," + encode(bytes);
    }

    public static byte[] decodeBytes(String encodedText){
        return Base64.getDecoder().decode(encodedText);
    }

    public static void writeToFile(String encodedText, File file) throws IOException {
        FileUtils.writeByteArrayToFile(file, decodeBytes(encodedText));
    }

    public static File toFile(String encodedText, String fileName) throws IOException,IllegalArgumentException {
        if(StringUtils.isEmpty(encodedText)) {
            return null;
        }
        File tempFile;
        if(StringUtils.isEmpty(fileName)) {
            tempFile = File.createTempFile("base64_", "_decoded", new File(System.getProperty("java.io.tmpdir")));
        } else {
            tempFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
        }
        byte[] decode = Base64.getDecoder().decode(encodedText);
        FileUtils.writeByteArrayToFile(tempFile, decode);

        return tempFile;
    }

    public static String encodeUrl(String url) {
        return Base64.getUrlEncoder().encodeToString(url.getBytes());
    }
}
