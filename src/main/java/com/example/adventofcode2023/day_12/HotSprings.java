package com.example.adventofcode2023.day_12;

import com.example.adventofcode2023.common.TestDriven;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

public class HotSprings implements TestDriven<Long> {

    public static Long numCombosFromLine(String line) {
        long result = 0L;
        var splitLine = line.split("\\s");
        List<Integer> lengths = Arrays.stream(splitLine[1].split(",")).map(Integer::parseInt).toList();
        String s = splitLine[0];



        return result;
    }
    @Override
    public Long runPartOne(BufferedReader bufferedReader) {
        return null;
    }

    @Override
    public Long runPartTwo(BufferedReader bufferedReader) {
        return null;
    }
}
