package com.example.adventofcode2023.day_2;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.adventofcode2023.common.TestDriven;

public class CubesGame implements TestDriven<Integer> {

    private static final int RED_LIMIT = 12;
    private static final int GREEN_LIMIT = 13;
    private static final int BLUE_LIMIT = 14;

    private record GameInstance(int id, List<BagPull> bagPulls) {
        public boolean breaksNoLimits() {
            return bagPulls.stream().noneMatch(BagPull::breaksLimits);
        }
    }

    private record BagPull(int red, int green, int blue) {
        public boolean breaksLimits() {
            return red > RED_LIMIT || green > GREEN_LIMIT || blue > BLUE_LIMIT;
        }
    }

    @Override
    public Integer runPartOne(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .map(CubesGame::mapLineToGameInstance)
            .filter(GameInstance::breaksNoLimits)
            .map(GameInstance::id)
            .reduce(0, Integer::sum);
    }

    private static GameInstance mapLineToGameInstance(String line) {
        //Get id
        String[] idAndRemainder = line.split(":");
        int id = Integer.parseInt(idAndRemainder[0].split("\\s")[1]);

        //Split remainder into cubes found from one pull
        List<String> bagPullsString = Arrays.asList(idAndRemainder[1].split(";"));
        List<BagPull> bagPulls = new ArrayList<>();
        //Split group of cubes by colour
        bagPullsString.forEach(cubesFound -> {
                List<String> numsAndColoursStrings = Arrays.asList(cubesFound.split(","));
                Map<String, Integer> results = new HashMap<>();
                numsAndColoursStrings.forEach(colourAndNum -> {
                    String regex = "((\\d+) (blue|red|green))";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(colourAndNum.trim());
                    if (matcher.matches()) {
                        results.put(matcher.group(3), Integer.parseInt(matcher.group(2)));
                    }
                });
                BagPull bagPull = new BagPull(
                    results.getOrDefault("red", 0),
                    results.getOrDefault("green", 0),
                    results.getOrDefault("blue", 0)
                );
                bagPulls.add(bagPull);
            }
        );
        return new GameInstance(id, bagPulls);
    }

    @Override
    public Integer runPartTwo(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .map(CubesGame::mapLineToGameInstance)
            .map(CubesGame::mapGameToMinBagPulls)
            .map(bagPull -> bagPull.red * bagPull.green * bagPull.blue)
            .reduce(0, Integer::sum);
    }

    // Using BagPull to hold the minimum values, not its original intended use
    private static BagPull mapGameToMinBagPulls(GameInstance gameInstance) {
        int minRed = 0;
        int minGreen = 0;
        int minBlue = 0;
        List<BagPull> bagPulls = gameInstance.bagPulls();
        for (BagPull bagPull : bagPulls) {
            if (bagPull.red() > minRed) minRed = bagPull.red();
            if (bagPull.green() > minGreen) minGreen = bagPull.green();
            if (bagPull.blue() > minBlue) minBlue = bagPull.blue();
        }
        return new BagPull(minRed, minGreen, minBlue);
    }
}
