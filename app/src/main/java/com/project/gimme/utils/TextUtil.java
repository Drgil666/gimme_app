package com.project.gimme.utils;

/**
 * @author Gilbert
 * @date 2022/3/27 14:38
 */
public class TextUtil {
    public static String expandableText(String text) {
        int cnt = 0;
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if ((int) text.charAt(i) > 255) {
                cnt += 2;
            } else {
                cnt++;
            }
            newText.append(text.charAt(i));
            if (cnt >= 10 && i != text.length() - 1) {
                cnt -= 10;
                newText.append("\n");
            }
        }
        return newText.toString();
    }
}
