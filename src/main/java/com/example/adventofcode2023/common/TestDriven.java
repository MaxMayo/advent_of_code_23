package com.example.adventofcode2023.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public interface TestDriven<R> {

    default R testDrivenRun(File file) throws IOException {
        return testDrivenRun(file, true);
    }

    default R testDrivenRun2(File file) throws IOException {
        return testDrivenRun(file, false);
    }

    default R testDrivenRun(File file, boolean isPartOne) throws IOException {
        try (
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            return isPartOne ? runPartOne(bufferedReader) : runPartTwo(bufferedReader);
        }
    }

    R runPartOne(BufferedReader bufferedReader);

    R runPartTwo(BufferedReader bufferedReader);



}
