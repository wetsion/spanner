package site.wetsion.framework.spanner.base;

public class StringUtil {

    /**
     * 判断字符串格式是否为一个email地址
     */
    public static boolean isEmail(String str){
        if (str == null || "".equals(str)){
            return false;
        }
        return str.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
    }

    /**
     * 数值转字符串，null兜底
     */
    public static String toStringDefaultNull(Integer number) {
        if (null == number) {
            return null;
        }

        return number.toString();
    }

    /**
     * 数值转字符串，null兜底
     */
    public static String toStringDefaultNull(Long number) {
        if (null == number) {
            return null;
        }

        return number.toString();
    }

    /**
     * 移除字符串中除了UCS-2编码字符以外的所有字符
     */
    public static String removeNonUCS2Chars(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= '\u0000' && c <= '\uD7FF' || c >= '\uE000' && c <= '\uFFFF') {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * Converts snake_case string to lowerCamelCase.
     *
     * @param snakeCaseStr The string in snake_case format.
     * @return A string in lowerCamelCase format.
     */
    public static String snakeCaseStrToLowerCamelCase(String snakeCaseStr) {
        if (snakeCaseStr == null || snakeCaseStr.isEmpty()) {
            return snakeCaseStr;
        }

        StringBuilder lowerCamelCaseStr = new StringBuilder();
        boolean convertNext = false;
        for (char ch : snakeCaseStr.toCharArray()) {
            if (ch == '_') {
                convertNext = true;
            } else {
                if (convertNext) {
                    lowerCamelCaseStr.append(Character.toUpperCase(ch));
                    convertNext = false;
                } else {
                    lowerCamelCaseStr.append(Character.toLowerCase(ch));
                }
            }
        }
        return lowerCamelCaseStr.toString();
    }
}
