package com.example.adventofcode2023.day_9;

import com.example.adventofcode2023.common.TestDriven;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NextInSequenceTest {

    File file;
    TestDriven<Long> nextInSequence;

    @BeforeEach
    void setup() throws FileNotFoundException {
        file = ResourceUtils.getFile("classpath:day_9/example.txt");
        nextInSequence = new NextInSequence();
    }
    @Test
    @SneakyThrows
    void testDrivenRun() throws IOException {
        var result = nextInSequence.testDrivenRun(file);
        assertEquals(114, result);
    }

    @Test
    @SneakyThrows
    void realRun() {
        file = ResourceUtils.getFile("classpath:day_9/input.txt");
        var result = nextInSequence.testDrivenRun(file);
        System.out.println(result);
        assertNotEquals(927219961, result);
        assertEquals(1938800261, result);
    }

    @Test
    @SneakyThrows
    void testDrivenRun2() {
        var result = nextInSequence.testDrivenRun2(file);
        assertEquals(2, result);
    }

    @Test
    @SneakyThrows
    void realRun2() {
        file = ResourceUtils.getFile("classpath:day_9/input.txt");
        var result = nextInSequence.testDrivenRun2(file);
        System.out.println(result);
        assertEquals(1112, result);
    }
}