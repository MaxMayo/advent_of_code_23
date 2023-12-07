package com.example.adventofcode2023.day_7;

import com.example.adventofcode2023.common.TestDriven;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CamelCardsTest {

    File testFile = ResourceUtils.getFile("classpath:day_7/example.txt");
    File realFile = ResourceUtils.getFile("classpath:day_7/input.txt");
    TestDriven<Long> testDriven() {
        return new CamelCards();
    }

    CamelCardsTest() throws FileNotFoundException {
    }

    @Test
    void testDrivenRun1() throws IOException {
        var result = testDriven().testDrivenRun(testFile);
        assertEquals(6440, result);
    }

    @Test
    void runReal1() throws IOException {
        var result = testDriven().testDrivenRun(realFile);
        System.out.println(result);
        assertEquals(248559379, result);
    }

    @Test
    void testDrivenRun2() throws IOException {
        var result = testDriven().testDrivenRun2(testFile);
        assertEquals(5905, result);
    }

    @Test
    void runReal2() throws IOException {
        var result = testDriven().testDrivenRun2(realFile);
        System.out.println(result);
        assertEquals(0, result);
    }
}