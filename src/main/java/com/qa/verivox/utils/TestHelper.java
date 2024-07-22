package com.qa.verivox.utils;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHelper {

    public static boolean isDateFormatIsCorrect(String regex, String date) {

        boolean isMatchingFormat = false;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            isMatchingFormat = true;
        }
        return isMatchingFormat;
    }

}
