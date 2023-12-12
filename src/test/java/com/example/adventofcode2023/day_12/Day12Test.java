package com.example.adventofcode2023.day_12;

import com.example.adventofcode2023.common.AbstractDay;
import com.example.adventofcode2023.common.AbstractTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.ResourceUtils;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test extends AbstractTest<Day12> {


    
//    public static Stream<Arguments> args() {
//        return Stream.of(
//                Arguments.of("rad", "input.txt", AbstractDay::partOne)
//        );
//    }
//
//    @ParameterizedTest
////    @ArgumentsSource(null)null
//    @MethodSource("args")
//    void runAllTests(String testName, String fileName, Function<AbstractDay, Long> function, Long expectedResult) {
//        var result = super.parameterisedTest(fileName, function.apply(this.getInstance()));
//        assertEquals(expectedResult, result);
//    }
//
//    @Override

    
    @Test
    void testDrivenRun() {
    }

    @Test
    void testDrivenRun2() {
    }

    @Override
    public Day12 getInstance() {
        return new Day12();
    }
}