package com.example.adventofcode2023.day_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.util.ResourceUtils;
import static org.junit.jupiter.api.Assertions.*;

import com.example.adventofcode2023.common.TestDriven;

class CubesGameTest {

    private TestDriven<Integer> cubesGame;
    private File file;

    public void testSetup() throws FileNotFoundException {
        cubesGame = new CubesGame();
        file = ResourceUtils.getFile("classpath:day_2/test_example.txt");
    }

    @Test
    @BeforeTestMethod
    void runTestPartOne() throws IOException {
        testSetup();
        Integer result = cubesGame.testDrivenRun(file);
        assertEquals(8, result);
    }

    @Test
    void runRealPartOne() throws IOException {
        cubesGame = new CubesGame();
        file = ResourceUtils.getFile("classpath:day_2/input_1.txt");
        Integer result = cubesGame.testDrivenRun(file);
        System.out.println(result);
        // actual confirmed result
        assertEquals(2551, result);
    }

    @Test
    void runPartTwo() throws IOException {
        testSetup();
        Integer result = cubesGame.testDrivenRun(file, false);
        assertEquals(2286, result);
    }

    @Test
    void runRealPartTwo() throws IOException {
        cubesGame = new CubesGame();
        file = ResourceUtils.getFile("classpath:day_2/input_1.txt");
        Long timeStart = System.currentTimeMillis();
        Integer result = cubesGame.testDrivenRun(file, false);
        Long timeEnd = System.currentTimeMillis();
        Long timeTotal = timeEnd - timeStart;
        System.out.println(result);
        System.out.println("Operation took " + timeTotal + " milliseconds");
        // actual confirmed result
        assertEquals(62811, result);
    }

    @Test
    void findShortestAndAverageRunTimes2() throws IOException {
        long totalMillis = 0;
        long fastestTime = Long.MAX_VALUE;
        for(int i = 0; i < 100; i++) {
            cubesGame = new CubesGame();
            file = ResourceUtils.getFile("classpath:day_2/input_1.txt");
            Long timeStart = System.currentTimeMillis();
            Integer result = cubesGame.testDrivenRun(file, false);
            Long timeEnd = System.currentTimeMillis();
            Long timeTotal = timeEnd - timeStart;
            totalMillis += timeTotal;
            if (timeTotal < fastestTime) fastestTime = timeTotal;
            System.out.println(result);
            System.out.println("Operation took " + timeTotal + " milliseconds");
            // actual confirmed result
            assertEquals(62811, result);
        }
        System.out.println();
        System.out.println("Fastest time: " + fastestTime + " milliseconds");
        System.out.println("Average time: " + totalMillis/100 + "milliseconds");
    }
}