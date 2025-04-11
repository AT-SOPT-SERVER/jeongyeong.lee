package org.sopt.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    private static final Pattern graphemePattern = Pattern.compile("\\X");
    private static final Matcher graphemeMatcher = graphemePattern.matcher("");

    public static int getLengthOfEmojiContainableText(String text) {
        if (text == null) {
            return 0;
        }
        graphemeMatcher.reset(text);
        int count = 0;
        while (graphemeMatcher.find()) {
            count++;
        }
        return count;
    }
}
