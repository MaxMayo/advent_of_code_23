package com.example.adventofcode2023.day_11;

import com.example.adventofcode2023.common.TestDriven;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GalacticExpansionTest {

    File file;
    TestDriven<Long> galacticExpansion;


    @Test
    @SneakyThrows
    void testOne() {
        file = ResourceUtils.getFile("classpath:day_11/example_1.txt");
        galacticExpansion = new GalacticExpansion();

        var result = galacticExpansion.testDrivenRun(file);

        assertEquals(374, result);
    }

    @Test
    @SneakyThrows
    void testDrivenRun2() {
        file = ResourceUtils.getFile("classpath:day_11/input.txt");
        galacticExpansion = new GalacticExpansion();

        var result = galacticExpansion.testDrivenRun2(file);

        assertEquals(635_832_237_682L, result);
        System.out.println(result);
    }

}