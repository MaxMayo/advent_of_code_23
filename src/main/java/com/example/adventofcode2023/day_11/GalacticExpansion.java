package com.example.adventofcode2023.day_11;

import com.example.adventofcode2023.common.TestDriven;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GalacticExpansion implements TestDriven<Long> {

    @Override
    public Long runPartOne(BufferedReader bufferedReader) {
        UniverseMap universeMap = new UniverseMap(bufferedReader.lines()
                .map(line ->
                        line.chars()
                                .mapToObj(Character::toString)
                                .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new)));

        List<Pair<Point, Point>> pairs = new ArrayList<>();
        List<Point> galaxies = universeMap.getGalaxyLocations();

        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                pairs.add(Pair.of(galaxies.get(i), galaxies.get(j)));
            }
        }

        return pairs.stream()
                .map(pair -> universeMap.distanceBetweenGalaxies(pair.getLeft(), pair.getRight(), 2))
                .map(Long::valueOf)
                .reduce(0L, Long::sum);
    }

    @Override
    public Long runPartTwo(BufferedReader bufferedReader) {
        UniverseMap universeMap = new UniverseMap(bufferedReader.lines()
                .map(line ->
                        line.chars()
                                .mapToObj(Character::toString)
                                .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new)));

        System.out.println("Universe map created");
        List<Pair<Point, Point>> pairs = new ArrayList<>();
        List<Point> galaxies = universeMap.getGalaxyLocations();
        System.out.println("galaxy locations found");

        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                pairs.add(Pair.of(galaxies.get(i), galaxies.get(j)));
            }
        }

        return pairs.stream()
                .map(pair -> universeMap.distanceBetweenGalaxies(pair.getLeft(), pair.getRight(), 1_000_000))
                .map(Long::valueOf)
                .reduce(0L, Long::sum);
    }
}
