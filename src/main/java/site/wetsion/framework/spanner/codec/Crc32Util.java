package site.wetsion.framework.spanner.codec;

import java.util.zip.CRC32;

public class Crc32Util {
    public static long getValue(String s){
        return getValue(s.getBytes());
    }

    public static long getValue(int s){
        CRC32 crc32 = new CRC32();
        crc32.update(s);
        return crc32.getValue();
    }

    public static long getValue(byte[] s){
        CRC32 crc32 = new CRC32();
        crc32.update(s);
        return crc32.getValue();
    }
}
