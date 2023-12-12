package com.example.adventofcode2023.common;

import java.io.*;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbstractDay {

    Stream<String> lines;

    public Long run(File file, Function<Stream<String>, Long> methodToRun) {
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                Stream<String> lines = bufferedReader.lines();
        ) {
            return methodToRun.apply(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Function<AbstractDay, Long> runMe(AbstractDay abstractDay) {
        return (day) -> day.partOne(lines);
    }

    public Long runPartOne(AbstractDay day) {
//        day.partOne()
        return partOne(lines);
    }

    public abstract Long partOne(Stream<String> lines);

    public Long runPartTwo(Stream<String> lines) {
        return partTwo(lines);
    };
    public abstract Long partTwo(Stream<String> lines);

}
