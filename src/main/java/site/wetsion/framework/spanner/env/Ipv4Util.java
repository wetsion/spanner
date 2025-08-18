package site.wetsion.framework.spanner.env;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ipv4Util {
    /**
     * 获取本地ip地址
     * @return 本地ip地址
     */
    public static String getLocalIp(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
        }
        return "";
    }

    public static int ip2Int(String ip) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(ip);
        byte[] bytes = address.getAddress();
        int a, b, c, d;
        a = byte2int(bytes[0]);
        b = byte2int(bytes[1]);
        c = byte2int(bytes[2]);
        d = byte2int(bytes[3]);
        int result = (a << 24) | (b << 16) | (c << 8) | d;
        return result;
    }

    public static int byte2int(byte b) {
        int l = b & 0x07f;
        if (b < 0) {
            l |= 0x80;
        }
        return l;
    }

    public static long ip2long(String ip) throws UnknownHostException {
        int ipNum = ip2Int(ip);
        return int2long(ipNum);
    }


    public static long int2long(int i) {
        long l = i & 0x7fffffffL;
        if (i < 0) {
            l |= 0x080000000L;
        }
        return l;
    }

    /**
     * @param ip 长整形ip地址
     * @return 长整形数值转化为ip地址
     * @author wenc
     */
    public static String long2ip(long ip) {
        int[] b = new int[4];
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        String x;
        Integer p;
        p = new Integer(0);
        x = p.toString(b[0]) + "." + p.toString(b[1]) + "." + p.toString(b[2])
                + "." + p.toString(b[3]);
        return x;
    }
}
