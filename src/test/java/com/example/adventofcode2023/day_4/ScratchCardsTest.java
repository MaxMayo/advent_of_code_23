package com.example.adventofcode2023.day_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import static org.junit.jupiter.api.Assertions.*;

import com.example.adventofcode2023.common.TestDriven;

class ScratchCardsTest {

    @Test
    void testDrivenRun1() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_4/example.txt");
        TestDriven<Integer> scratchCards = new ScratchCards();
        int result = scratchCards.testDrivenRun(file);
        assertEquals(13, result);
    }

    @Test
    void runReal() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_4/input.txt");
        TestDriven<Integer> scratchCards = new ScratchCards();
        int result = scratchCards.testDrivenRun(file);
        System.out.println(result);
        assertEquals(22897, result);
    }

    @Test
    void testDrivenRun2() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_4/example.txt");
        TestDriven<Integer> scratchCards = new ScratchCards();
        int result = scratchCards.testDrivenRun(file, false);
        assertEquals(30, result);
    }

    @Test
    void runReal2() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_4/input.txt");
        TestDriven<Integer> scratchCards = new ScratchCards();
        int result = scratchCards.testDrivenRun(file, false);
        System.out.println(result);
//        assertEquals(22897, result);
    }
}