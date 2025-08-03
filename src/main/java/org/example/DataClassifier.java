package org.example;

import java.util.regex.Pattern;

public class DataClassifier {
    private static final Pattern INT_PATTERN = Pattern.compile("^-?[0-9]+$");
    private static final Pattern FLOAT_PATTERN = Pattern.compile("^-?[0-9]+([.,][0-9]+)?([eE][-+]?[0-9]+)?$");


    public DataType classify(String input) {
        String trimmed = input.trim();
        if (INT_PATTERN.matcher(trimmed).matches()) return DataType.INTEGER;
        if (FLOAT_PATTERN.matcher(trimmed).matches()) return DataType.FLOAT;

        return DataType.STRING;
    }
}
