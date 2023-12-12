package com.example.adventofcode2023.day_11;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.TriConsumer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class UniverseMap {
    private List<List<String>> map;
    private List<Integer> columnsWhereExpanded = new ArrayList<>();
    private List<Integer> rowsWhereExpanded = new ArrayList<>();

    private static int numGalaxies = 0;

    public UniverseMap(List<List<String>> map) {
        this.map = map;
        expandUniverse();
    }


    public List<Point> getGalaxyLocations() {
        List<Point> locations = new ArrayList<>();
        mapIterator((row, column, character) -> {
            if (!character.equals(".")) locations.add(new Point(column, row));
        });
        return locations;
    }

    private void mapIterator(TriConsumer<Integer, Integer, String> codeBlock) {
        for (int row = 0; row < map.size(); row++) {
            for (int column = 0; column < map.getFirst().size(); column++) {
                codeBlock.accept(row, column, map.get(row).get(column));
            }
        }
    }

    private void expandUniverse() {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).stream().allMatch("."::equals)) {
                rowsWhereExpanded.add(i);
            }
        }
        for (int column = 0; column < map.getFirst().size(); column++) {
            boolean columnIsEmpty = true;
            for (int row = 0; row < map.size(); row++) {
                if (!map.get(row).get(column).equals(".")) columnIsEmpty = false;
            }
            if (columnIsEmpty) columnsWhereExpanded.add(column);
        }
    }

    public Integer distanceBetweenGalaxies(Point a, Point b, int expansionCoefficient) {
        int leftMost = Math.min(a.x, b.x);
        int rightMost = Math.max(a.x, b.x);
        int highest = Math.min(a.y, b.y);
        int lowest = Math.max(a.y, b.y);
        int columnExpansionsCrossed = (int) columnsWhereExpanded.stream()
                .filter(columnIndex -> columnIndex < rightMost && columnIndex > leftMost)
                .count();
        int rowExpansionsCrossed = (int) rowsWhereExpanded.stream()
                .filter(rowIndex -> rowIndex > highest && rowIndex < lowest)
                .count();

        int distWhereExpansionsIgnored = Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
        int distMinusExpansions = distWhereExpansionsIgnored - columnExpansionsCrossed - rowExpansionsCrossed;
        return distMinusExpansions + ((columnExpansionsCrossed + rowExpansionsCrossed) * expansionCoefficient);
    }


}