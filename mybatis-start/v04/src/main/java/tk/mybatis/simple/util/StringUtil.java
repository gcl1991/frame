package tk.mybatis.simple.util;

public class StringUtil {
    public static boolean isNotEmpty(String str) {
        return str != null && !str.equals("");
    }
}
