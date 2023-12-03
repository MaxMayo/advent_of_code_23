package com.example.adventofcode2023.day_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import static org.junit.jupiter.api.Assertions.*;

import com.example.adventofcode2023.common.TestDriven;

class GearRatiosTest {

    private File file;
    private TestDriven<Integer> gearRatios;

    void testDrivenSetup() throws FileNotFoundException {
        file = ResourceUtils.getFile("classpath:day_3/test_example.txt");
        gearRatios = new GearRatios();
    }

    @Test
    void testDrivenRunPart1() throws IOException {
        testDrivenSetup();
        int result = gearRatios.testDrivenRun(file);
        assertEquals(4361, result);
    }

    @Test
    void runRealPart1() throws IOException {
        file = ResourceUtils.getFile("classpath:day_3/input_1.txt");
        gearRatios = new GearRatios();
        int result = gearRatios.testDrivenRun(file);
        System.out.println(result);
        //higher
        assertNotEquals(523948, result);
        //higher
        assertNotEquals(524064, result);
        //correct
        assertEquals(525119, result);
    }

    @Test
    void testDrivenRunPart2() throws IOException {
        testDrivenSetup();
        int result = gearRatios.testDrivenRun(file, false);
        assertEquals(467835, result);
    }

    @Test
    void runRealPart2() throws IOException {
        file = ResourceUtils.getFile("classpath:day_3/input_1.txt");
        gearRatios = new GearRatios();
        int result = gearRatios.testDrivenRun(file, false);
        System.out.println(result);
        assertEquals(76504829, result);

    }
}