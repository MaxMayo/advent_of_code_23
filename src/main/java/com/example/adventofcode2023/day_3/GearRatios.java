package com.example.adventofcode2023.day_3;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.adventofcode2023.common.TestDriven;

public class GearRatios implements TestDriven<Integer> {

    private record SymbolLocation(int row, int column) {
    }

    private record PartNumberLocation(int row, int startIndex, int endIndex, int value) {
    }

    @Override
    public Integer runPartOne(BufferedReader bufferedReader) {
        List<String> lines = bufferedReader.lines().toList();
        //can now close buffered reader
        List<SymbolLocation> symbolLocations = findSymbolLocations(lines, false);
        List<PartNumberLocation> partNumbers = findAllPartNumbers(lines);
        return findValidPartNumbers(partNumbers, symbolLocations).stream().map(PartNumberLocation::value).reduce(Integer::sum).orElse(null);
    }

    private List<PartNumberLocation> findValidPartNumbers(List<PartNumberLocation> partNumberLocations, List<SymbolLocation> symbolLocations) {
        List<PartNumberLocation> validPartNumbers = new ArrayList<>();
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
                validPartNumbers.add(partNumberLocation);
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
                    if (startIndex == -1) startIndex = column;
                    endIndex = column;
                    currentNumber *= 10;
                    currentNumber += Character.getNumericValue(character);
                    if (column == lineLength - 1) {
                        locations.add(new PartNumberLocation(row, startIndex, endIndex, currentNumber));
                    }
                }
            }
        }
        return locations;
    }


    private List<SymbolLocation> findSymbolLocations(List<String> lines, boolean onlyAsterisks) {
        List<SymbolLocation> locations = new ArrayList<>();
        int numLines = lines.size();
        for (int row = 0; row < numLines; row++) {
            String line = lines.get(row);
            int lineLength = line.length();
            for (int column = 0; column < lineLength; column++) {
                char character = line.charAt(column);
                if (isCharacterASymbol(character)) {
                    if ((!onlyAsterisks) || (onlyAsterisks && character == '*')) {
                        locations.add(new SymbolLocation(row, column));
                    }
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
        List<String> lines = bufferedReader.lines().toList();
        //can now close buffered reader
        List<SymbolLocation> asteriskLocations = findSymbolLocations(lines, true);
        List<PartNumberLocation> partNumbers = findAllPartNumbers(lines);
        List<PartNumberLocation> validPartNumberLocations = findValidPartNumbers(partNumbers, asteriskLocations);
        //find stars with exactly 2 adjacent part numbers
        return findSumOfGearRatios(validPartNumberLocations, asteriskLocations);
    }

    private Integer findSumOfGearRatios(List<PartNumberLocation> partNumberLocations, List<SymbolLocation> symbolLocations) {
        Map<SymbolLocation, List<Integer>> asterisksAndAdjacentPartsMap = new HashMap<>();
        for (SymbolLocation asterisk : symbolLocations) {
            asterisksAndAdjacentPartsMap.put(asterisk, new ArrayList<>(3));
        }
        updateGearsMapWithNumParts(partNumberLocations, symbolLocations, asterisksAndAdjacentPartsMap);
        return asterisksAndAdjacentPartsMap.values()
            .stream()
            .filter(list -> list.size() == 2)
            .map(list -> list.get(0) * list.get(1))
            .reduce(0, Integer::sum);


    }

    //more bad copy/paste to replace with actual thought
    private void updateGearsMapWithNumParts(List<PartNumberLocation> partNumberLocations,
                                            List<SymbolLocation> asterisks,
                                            Map<SymbolLocation, List<Integer>> asterisksAndAAdjacentPartsMap) {
        //for partNumber, find if it has an adjacent asterisk
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
            potentialSymbolLocations.stream().filter(asterisks::contains).forEach(asterisk -> {
                    var partsList = asterisksAndAAdjacentPartsMap.get(asterisk);
                    partsList.add(partNumberLocation.value);
                }
            );
        }
    }
}
