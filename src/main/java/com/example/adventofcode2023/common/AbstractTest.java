package com.example.adventofcode2023.common;

import java.io.File;
import java.util.function.Function;

public abstract class AbstractTest<T extends AbstractDay> {

    private T instance;

    abstract public T getInstance();

    public Long parameterisedTest(String fileName, Function<AbstractDay, Long> lambdaThatRunsPartOneOrTwo) {
        String directory = "11";
        String resourcePath = String.format("classpath:day_%s/%s.txt", directory, fileName);
        File file = new File(resourcePath);
        T instance = getInstance();

        Long result = lambdaThatRunsPartOneOrTwo.apply(instance);

        System.out.println(result);
        return result;
    }

}
