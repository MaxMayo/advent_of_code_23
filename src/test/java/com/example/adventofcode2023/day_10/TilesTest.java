package com.example.adventofcode2023.day_10;

import com.example.adventofcode2023.common.TestDriven;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TilesTest {

    File file;
    TestDriven<Integer> tiles;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        tiles = new Tiles();
    }

    @Test
    @SneakyThrows
    void testDrivenRun1Small() {
        file = ResourceUtils.getFile("classpath:day_10/example1.txt");
        var result = tiles.testDrivenRun(file);
        assertEquals(4, result);
    }

    @Test
    @SneakyThrows
    void testDrivenRun1Larger() {
        file = ResourceUtils.getFile("classpath:day_10/example2.txt");
        var result = tiles.testDrivenRun(file);
        assertEquals(8, result);
    }

    @Test
    @SneakyThrows
    void runReal1() {
        file = ResourceUtils.getFile("classpath:day_10/input.txt");
        var result = tiles.testDrivenRun(file);
        System.out.println(result);
    }

    @Test
    @SneakyThrows
    void testDrivenRun2() {
    }
}