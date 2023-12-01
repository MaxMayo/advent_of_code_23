package com.example.adventofcode2023.day_1;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import static org.junit.jupiter.api.Assertions.*;

import com.example.adventofcode2023.common.TestDriven;

class CalibrationTest {

    @Test
    void runTestPartOne() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_1/testExample.txt");
        TestDriven<Integer> testDriven = new Calibration();
        Integer result = testDriven.testDrivenRun(file);
        assertEquals(142, result);
    }

    @Test
    void runRealPartOne() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_1/input_1.txt");
        TestDriven<Integer> testDriven = new Calibration();
        Integer result = testDriven.testDrivenRun(file);
        System.out.println(result);
    }

    @Test
    void runTestPartTwo() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_1/test_example_2.txt");
        TestDriven<Integer> testDriven = new Calibration();
        Integer result = testDriven.testDrivenRun(file, false);
        assertEquals(281, result);
    }

    @Test
    void runRealPartTwo() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_1/input_1.txt");
        TestDriven<Integer> testDriven = new Calibration();
        Integer result = testDriven.testDrivenRun(file, false);
        System.out.println(result);
    }
}