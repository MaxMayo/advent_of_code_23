package com.example.adventofcode2023.day_6;

import lombok.SneakyThrows;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.adventofcode2023.common.TestDriven;

public class Boats implements TestDriven<Long> {


    @SneakyThrows
    @Override
    public Long runPartOne(BufferedReader bufferedReader) {
        List<Long> timeList = Arrays.stream(bufferedReader.readLine().split(":")[1].trim().split("\s")).filter(string -> !string.isBlank()).map(Long::parseLong).toList();
        List<Long> distanceRecordList = Arrays.stream(bufferedReader.readLine().split(":")[1].trim().split("\s")).filter(string -> !string.isBlank()).map(Long::parseLong).toList();

        List<Long> waysToWin = new ArrayList<>();
        for(long i = 0; i < timeList.size(); i++) {
            waysToWin.add(numWaysToWin(timeList.get((int) i), distanceRecordList.get((int) i)));
        }

        return waysToWin.stream().reduce(1L, (held, current) -> held * current);
    }

    public long numWaysToWin(long totalTime, long distanceRecord) {
        long numWaysToBreakRecord = 0;
        for (long chargeTime = 0; chargeTime < totalTime; chargeTime++) {
            // unnecessary
            long velocity = chargeTime;
            long timeRemaining = totalTime - chargeTime;
            long distance = velocity * timeRemaining;
            if (distance > distanceRecord) numWaysToBreakRecord++;
        }
        return numWaysToBreakRecord;
    }

    @Override
    public Long runPartTwo(BufferedReader bufferedReader) {
        try {
            var timeRecord = Long.parseLong(bufferedReader.readLine().split(":")[1].trim().replaceAll("\s", ""));
            var distanceRecord = Long.parseLong(bufferedReader.readLine().split(":")[1].trim().replaceAll("\s", ""));
            return numWaysToWin(timeRecord, distanceRecord);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
