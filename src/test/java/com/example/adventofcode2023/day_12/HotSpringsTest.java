package com.example.adventofcode2023.day_12;

import com.example.adventofcode2023.common.TestDriven;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HotSpringsTest {

    File file;
    TestDriven<Long> testDriven;

    @BeforeEach
    void setUp() {
        testDriven = new HotSprings();
    }

    static Stream<Arguments> individualLines() {
        return Stream.of(
                Arguments.of("???.### 1,1,3", 1),
                Arguments.of(".??..??...?##. 1,1,3", 4),
                Arguments.of("?#?#?#?#?#?#?#? 1,3,1,6", 1),
                Arguments.of("????.#...#... 4,1,1", 1),
                Arguments.of("????.######..#####. 1,6,5", 4),
                Arguments.of("?###???????? 3,2,1", 10)
        );
    }

    @ParameterizedTest
    @MethodSource("individualLines")
    void incrementalDevelopment(String line, long expectedNumCombos) {
        var result = HotSprings.numCombosFromLine(line);
        assertEquals(expectedNumCombos, result);
    }

    @Test
    void testPartOne() throws IOException {
        File file = new File("classpath:day_12/example_1.txt");
        var result = testDriven.testDrivenRun(file);
        assertEquals(21, result);
    }

    @Test
    void testDrivenRun() {
    }

    @Test
    void testDrivenRun2() {
    }
}