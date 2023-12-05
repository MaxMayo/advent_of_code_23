package com.example.adventofcode2023.day_1;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.example.adventofcode2023.common.TestDriven;

public class Calibration implements TestDriven<Integer> {

    String REGEX = "";
    Pattern PATTERN = Pattern.compile(REGEX);

    private final Map<String, Character> myMap = Map.of(
        "one", '1',
        "two", '2',
        "three", '3',
        "four", '4',
        "five", '5',
        "six", '6',
        "seven", '7',
        "eight", '8',
        "nine", '9'
    );

    @Override
    public Integer runPartOne(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .map(line -> line.replaceAll("\\D", ""))
            .map(Calibration::combineFirstAndLast)
            .map(Integer::parseInt)
            .reduce(0, Integer::sum);
    }

    @Override
    public Integer runPartTwo(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .map(this::readAllSpellingsThenReplace)
            .map(line -> line.replaceAll("\\D", ""))
            .map(Calibration::combineFirstAndLast)
            .map(Integer::parseInt)
            .reduce(0, Integer::sum);
    }

    private static String combineFirstAndLast(String input) {
        return "" + input.charAt(0) + input.charAt(input.length() - 1);
    }

    private String readAllSpellingsThenReplace(String line) {
        Map<Integer, Character> replacementsToMake = new HashMap<>();
        myMap.forEach((key, value) -> {
            int indexOfFirstOccurrence = line.indexOf(key);
            if (indexOfFirstOccurrence != -1) replacementsToMake.put(indexOfFirstOccurrence, value);
            int indexOfLastOccurrence = line.lastIndexOf(key);
            if (indexOfLastOccurrence != -1) replacementsToMake.put(indexOfLastOccurrence, value);
        });
        char[] charArray = line.toCharArray();
        replacementsToMake.forEach((index, value) -> charArray[index] = value);
        return String.valueOf(charArray);
    }
}
