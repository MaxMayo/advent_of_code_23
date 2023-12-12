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

    private static int numGalaxies = 0;

    public UniverseMap(List<List<String>> map) {
        this.map = map;
//        applyNumberToGalaxies();
        expandUniverse();
    }

     private void applyNumberToGalaxies() {
        this.map.forEach(list -> list.replaceAll(string -> string.equals("#") ? String.valueOf(numGalaxies++) : string));
    }

    public List<Point> getGalaxyLocations() {
        List<Point> locations = new ArrayList<>();
        mapIterator((row, column, character) -> {
            if (!character.equals(".")) locations.add(new Point(column, row));
        });
        return locations;
    }

    private void mapIterator(TriConsumer<Integer, Integer, String> codeBlock) {
        for (int column = 0; column < map.getFirst().size(); column++) {
            for (int row = 0; row < map.size(); row++) {
                codeBlock.accept(row, column, map.get(row).get(column));
            }
        }
    }

    private void expandUniverse() {
        //rows
        List<Integer> whereToInsertNewRows = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            if(map.get(i).stream().allMatch("."::equals)) {
                whereToInsertNewRows.add(i);
            }
        }
        List<Integer> whereToInsertNewColumns = new ArrayList<>();
        for (int column = 0; column < map.getFirst().size(); column++) {
            boolean columnIsEmpty = true;
            for (int row = 0; row < map.size(); row++) {
                if(!map.get(row).get(column).equals(".")) columnIsEmpty = false;
            }
            if (columnIsEmpty) whereToInsertNewColumns.add(column);
        }

        //insert new columns first
        whereToInsertNewColumns.reversed().forEach(columnIndex -> {
            map.forEach(innerList -> innerList.add(columnIndex, "."));
        });
        int rowLength = this.map.getFirst().size();

        whereToInsertNewRows.reversed().forEach(rowIndex -> {
            map.add(rowIndex, new ArrayList<>(Collections.nCopies(rowLength, ".")));
        });
    }

}