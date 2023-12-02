package com.example.adventofcode2023.day_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        Integer result = cubesGame.testDrivenRun(file, false);
        System.out.println(result);
    }
}