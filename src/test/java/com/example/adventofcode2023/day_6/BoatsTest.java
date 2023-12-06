package com.example.adventofcode2023.day_6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import static org.junit.jupiter.api.Assertions.*;

import com.example.adventofcode2023.common.TestDriven;

class BoatsTest {

    File file;
    TestDriven<Long> boats;

    private void testSetup() throws FileNotFoundException {
        file = ResourceUtils.getFile("classpath:day_6/example.txt");
        boats = new Boats();
    }

    @Test
    void testDrivenRun() throws IOException {
        testSetup();
        var result = boats.testDrivenRun(file);
        assertEquals(288, result);
    }

    @Test
    void runReal() throws IOException {
        file = ResourceUtils.getFile("classpath:day_6/input.txt");
        boats = new Boats();
        var result = boats.testDrivenRun(file);
        System.out.println(result);
    }

    @Test
    void testDrivenRun2() throws IOException {
        testSetup();
        var result = boats.testDrivenRun(file, false);
        assertEquals(71503, result);
    }

    @Test
    void runReal2() throws IOException {
        file = ResourceUtils.getFile("classpath:day_6/input.txt");
        boats = new Boats();
        var result = boats.testDrivenRun(file, false);
        System.out.println(result);
    }
}