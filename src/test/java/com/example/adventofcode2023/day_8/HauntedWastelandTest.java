package com.example.adventofcode2023.day_8;

import com.example.adventofcode2023.common.TestDriven;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HauntedWastelandTest {

    private TestDriven<Integer> hauntedWasteland;

    @BeforeEach
    void setup() {
        hauntedWasteland = new HauntedWasteland();
    }

    @Test
    void testDrivenRunOne() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_8/example_1.txt");
        var result = hauntedWasteland.testDrivenRun(file);
        System.out.println(result);
        assertEquals(2, result);
    }

    @Test
    void testDrivenRunOne2() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_8/example_2.txt");
        var result = hauntedWasteland.testDrivenRun(file);
        System.out.println(result);
        assertEquals(6, result);
    }

    @Test
    void testDrivenRunReal() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_8/input.txt");
        var result = hauntedWasteland.testDrivenRun(file);
        System.out.println(result);
        assertEquals(14429, result);
    }

    @Test
    void testDrivenRun2() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_8/example_3.txt");
        var result = hauntedWasteland.testDrivenRun(file, false);
        System.out.println(result);
        assertEquals(6, result);
    }

    @Test
    void realRun2() throws IOException {
        File file = ResourceUtils.getFile("classpath:day_8/input.txt");
        var result = hauntedWasteland.testDrivenRun(file, false);
        System.out.println(result);
    }
}