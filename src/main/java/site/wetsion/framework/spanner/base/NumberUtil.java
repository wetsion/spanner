package site.wetsion.framework.spanner.base;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
    private static final Pattern PATTERN = Pattern.compile("[0-9]+\\.*[0-9]*");

    /**
     * 是否大于0
     */
    public static boolean greaterThanZero(Integer num) {
        return null != num && 0 < num;
    }

    /**
     * 是否大于0
     */
    public static boolean greaterThanZero(Long num) {
        return null != num && 0L < num;
    }

    /**
     * 是否大于0
     */
    public static boolean greaterThanZero(Double num) {
        return null != num && 0D < num;
    }

    /**
     * 是否大于等于0
     */
    public static boolean greaterThanOrEqualToZero(Integer num) {
        return null != num && num >= 0;
    }

    /**
     * 是否大于等于0
     */
    public static boolean greaterThanOrEqualToZero(Long num) {
        return null != num && num >= 0L;
    }

    /**
     * 为null时默认为0
     */
    public static Integer defaultZero(Integer number) {
        return ObjectUtils.defaultIfNull(number, 0);
    }

    /**
     * 为null时默认为0
     */
    public static Long defaultZero(Long number) {
        return ObjectUtils.defaultIfNull(number, 0L);
    }

    /**
     * 为null或0时给默认值
     */
    public static Long defaultIfEmpty(Long number, Long defaultNumber) {
        return Objects.isNull(number) || Objects.equals(0L, number) ? defaultNumber : number;
    }

    public static boolean isNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        Matcher matcher = PATTERN.matcher(str);
        return matcher.matches();
    }

    public static Long parseLongOrElseGet(String origin, Long defaultValue) {
        try {
            return Long.parseLong(origin);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer parseIntegerOrElseGet(String origin, Integer defaultValue) {
        try {
            return Integer.parseInt(origin);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Double parseDoubleOrElseGet(String origin, Double defaultValue) {
        try {
            return Double.parseDouble(origin);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Float parseFloatOrElseGet(String origin, Float defaultValue) {
        try {
            return Float.parseFloat(origin);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer toInteger(Long number) {
        if (null == number) {
            return null;
        }

        return Math.toIntExact(number);
    }

    public static Long toLong(Integer number) {
        if (null == number) {
            return null;
        }

        return number.longValue();
    }
}
