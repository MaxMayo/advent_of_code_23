package com.example.adventofcode2023.day_9;

import com.example.adventofcode2023.common.TestDriven;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NextInSequence implements TestDriven<Long> {

    @Override
    public Long runPartOne(BufferedReader bufferedReader) {
        return bufferedReader.lines()
                .map(line -> line.split("\\s"))
                .map(
                        stringArray -> Arrays.stream(stringArray)
                                .map(Long::parseLong).toList()
                )
                .map(NextInSequence::findSequences)
                .map(listOfSequences -> listOfSequences
                        .reversed()
                        .stream()
                        .map(List::getLast)
                        .reduce(0L, Long::sum)
                )
                .reduce(0L, Long::sum);
    }

    private static List<List<Long>> findSequences(List<Long> firstRow) {
        int maxDepth = firstRow.size();
        List<List<Long>> sequences = new ArrayList<>(maxDepth);
        List<Long> currentSequence = firstRow;
        for (int depth = 1; depth <= maxDepth; depth += 1) {
            int currentSequenceLength = currentSequence.size();
            List<Long> sequenceDifferences = new ArrayList<>(currentSequence.size() - 1);
            boolean allAre0 = true;
            for (int i = 0; i < currentSequenceLength - 1; i++) {
                long difference = currentSequence.get(i + 1) - currentSequence.get(i);
                sequenceDifferences.add(difference);
                if (difference != 0) allAre0 = false;
            }
            sequences.add(sequenceDifferences);
            if (allAre0) break;
            currentSequence = sequenceDifferences;
        }
        sequences.add(firstRow);
        return sequences;
    }


    @Override
    public Long runPartTwo(BufferedReader bufferedReader) {
        return null;
    }
}
