package com.example.adventofcode2023.day_3;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.example.adventofcode2023.common.TestDriven;

public class GearRatios implements TestDriven<Integer> {

    private record SymbolLocation(int row, int column){};
    private record PartNumberLocation(int row, int startIndex, int endIndex, int value){}
    @Override
    public Integer runPartOne(BufferedReader bufferedReader) {
        List<String> lines = bufferedReader.lines().toList();
        //can now close buffered reader

        List<SymbolLocation> symbolLocations = findSymbolLocations(lines);

        List<PartNumberLocation> partNumbers = findAllPartNumbers(lines);

        return findValidPartNumbers(partNumbers, symbolLocations).stream().reduce(Integer::sum).orElse(null);

    }

    private List<Integer> findValidPartNumbers(List<PartNumberLocation> partNumberLocations, List<SymbolLocation> symbolLocations) {
        List<Integer> validPartNumbers = new ArrayList<>();
        //for partNumber, find if it has an adjacent symbol
        for (PartNumberLocation partNumberLocation : partNumberLocations) {
            List<SymbolLocation> potentialSymbolLocations = new ArrayList<>();
            //create potential symbol locations
            int columnRangeStart = partNumberLocation.startIndex - 1;
            int columnRangeEnd = partNumberLocation.endIndex + 1;
            //do row above and below number
            for (int y = partNumberLocation.row - 1; y < partNumberLocation.row + 2; y += 2) {
                for (int x = columnRangeStart; x <= columnRangeEnd; x++) {
                    potentialSymbolLocations.add(new SymbolLocation(y, x));
                }
            }
            //do the two end spaces
            potentialSymbolLocations.add(new SymbolLocation(partNumberLocation.row, partNumberLocation.startIndex - 1));
            potentialSymbolLocations.add(new SymbolLocation(partNumberLocation.row, partNumberLocation.endIndex + 1));
            // see if any potential ones match any real ones
            if (potentialSymbolLocations.stream().anyMatch(symbolLocations::contains)) {
                validPartNumbers.add(partNumberLocation.value);
            }
        }
        return validPartNumbers;
    }

    // copy pasted from symbol locations. TODO: tidy
    private List<PartNumberLocation> findAllPartNumbers(List<String> lines) {
        List<PartNumberLocation> locations = new ArrayList<>();
        int numLines = lines.size();
        for (int row = 0; row < numLines; row++) {
            String line = lines.get(row);
            int lineLength = line.length();
            int currentNumber = 0;
            int startIndex = -1;
            int endIndex = -1;
            for (int column = 0; column < lineLength; column++) {
                char character = line.charAt(column);
                if (currentNumber != 0 && !Character.isDigit(character)) {
                    locations.add(new PartNumberLocation(row, startIndex, endIndex, currentNumber));
                    startIndex = -1;
                    endIndex = -2;
                    currentNumber = 0;
                } else if (Character.isDigit(character)) {
                    if(startIndex == -1) startIndex = column;
                    endIndex = column;
                    currentNumber *= 10;
                    currentNumber += Character.getNumericValue(character);
                    if(column == lineLength - 1) {
                        locations.add(new PartNumberLocation(row, startIndex, endIndex, currentNumber));
                    }
                }
            }
        }
        return locations;
    }

    private List<SymbolLocation> findSymbolLocations(List<String> lines) {
        List<SymbolLocation> locations = new ArrayList<>();
        int numLines = lines.size();
        for (int row = 0; row < numLines; row++) {
            String line = lines.get(row);
            int lineLength = line.length();
            for (int column = 0; column < lineLength; column++) {
                char character = line.charAt(column);
                if (isCharacterASymbol(character)) {
                    locations.add(new SymbolLocation(row, column));
                }
            }
        }
        return locations;
    }

    private boolean isCharacterASymbol(char character) {
        return !Character.isLetterOrDigit(character) && character != '.';
    }

    @Override
    public Integer runPartTwo(BufferedReader bufferedReader) {
        return null;
    }
}
