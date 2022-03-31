package com.project.gimme.utils;

/**
 * @author Gilbert
 * @date 2022/3/27 14:38
 */
public class TextUtil {
    private static String regex = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
    private static Integer threshold = 12;

    public static String expandableText(String text) {
        int cnt = 0;
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (i + 1 < text.length() && (text.charAt(i) + String.valueOf(text.charAt(i + 1))).matches(regex)) {
                cnt += 2;
                newText.append(text.charAt(i) + String.valueOf(text.charAt(i + 1)));
                i++;
            } else if ((int) text.charAt(i) > 255) {
                cnt += 2;
                newText.append(text.charAt(i));
            } else {
                cnt++;
                newText.append(text.charAt(i));
            }
            if (cnt >= threshold && i != text.length() - 1) {
                cnt = 0;
                newText.append("\n");
            }
        }
        return newText.toString();
    }
}
