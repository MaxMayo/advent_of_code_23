package com.example.adventofcode2023.day_5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import static org.junit.jupiter.api.Assertions.*;

import com.example.adventofcode2023.common.TestDriven;

class AlmanacTest {

    private File file;
    private TestDriven<Long> almanac;

    void testSetup() throws FileNotFoundException {
        file = ResourceUtils.getFile("classpath:day_5/example.txt");
        almanac = new Almanac();
    }

    @Test
    void testDrivenRun() throws IOException {
        testSetup();
        long result = almanac.testDrivenRun(file);
        assertEquals(35, result);
    }

    @Test
    void realRun() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_5/input.txt");
        almanac = new Almanac();
        var result = almanac.testDrivenRun(file);
        System.out.println(result);
    }

    @Test
    void testDrivenRun2() throws IOException {
        testSetup();
        long result = almanac.testDrivenRun(file, false);
        System.out.println(result);
        assertEquals(46, result);
    }

    @Test
    void realRun2() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_5/input.txt");
        almanac = new Almanac();
        var result = almanac.testDrivenRun(file, false);
        System.out.println(result);
    }
}