package com.example.adventofcode2023.day_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

import com.example.adventofcode2023.common.TestDriven;

public class Almanac implements TestDriven<Long> {

    private static long index = 0;
    private static long totalSeeds = 0;

    private static void printIndex() {
        if((index % 10_000_000) == 0) {
            System.out.println((double) index/(double) totalSeeds);
        }
        index++;
    }

    private static final String REGEX = "(?<destination>\\d+) (?<source>\\d+) (?<range>\\d+)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private Map<String, List<String>> groups = new HashMap<>();
    private List<Long> seeds = new ArrayList<>();

    private class FarmingMapper {

        public record DestSourceRange(long destination, long source, long range) {

            public static DestSourceRange fromLine(String line) {
                var matcher = PATTERN.matcher(line);
                matcher.matches();
                return new DestSourceRange(
                    Long.parseLong(matcher.group("destination")),
                    Long.parseLong(matcher.group("source")),
                    Long.parseLong(matcher.group("range"))
                );
            }

            long map(long givenSource) {
                if (hasInRange(givenSource)) {
                    return destination + givenSource - source;
                } else {
                    return givenSource;
                }
            }

            long mapNoCheck(long givenSource) {
                return destination + givenSource - source;
            }

            boolean hasInRange(long givenSource) {
                return source <= givenSource && (source + range) > givenSource;
                //that was EXPENSIVE
//                return Range.between(source, source + range - 1).contains(givenSource);
            }
        }

        private List<DestSourceRange> mappings = new ArrayList<>();

        public long getDestination(long givenSource) {
            for (DestSourceRange mapping : mappings) {
                if (mapping.hasInRange(givenSource)) {
                    return mapping.mapNoCheck(givenSource);
                }
            }
            return givenSource;
        }
    }

    @Override
    public Long runPartOne(BufferedReader bufferedReader) {
        try {
            populateSeedsList(bufferedReader.readLine());
            //discard blank line
            String line = bufferedReader.readLine();
            //discard useless text line
            bufferedReader.readLine();
            line = bufferedReader.readLine();
            var soilMapper = new FarmingMapper();
            while (!line.isBlank()) {
                soilMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            var fertiliserMapper = new FarmingMapper();
            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (!line.isBlank()) {
                fertiliserMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var waterMapper = new FarmingMapper();
            while (!line.isBlank()) {
                waterMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var lightMapper = new FarmingMapper();
            while (!line.isBlank()) {
                lightMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var temperatureMapper = new FarmingMapper();
            while (!line.isBlank()) {
                temperatureMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var humidityMapper = new FarmingMapper();
            while (!line.isBlank()) {
                humidityMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var locationMapper = new FarmingMapper();
            while (!line.isBlank()) {
                locationMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
                if(line == null) break;
            }

            long seedWithLowestLocation = 0;
            long lowestLocation = Long.MAX_VALUE;

            for (Long seed : seeds) {
                long soilNumber = soilMapper.getDestination(seed);
                long fertiliserNumber = fertiliserMapper.getDestination(soilNumber);
                long waterNumber = waterMapper.getDestination(fertiliserNumber);
                long lightNumber = lightMapper.getDestination(waterNumber);
                long temperatureNumber = temperatureMapper.getDestination(lightNumber);
                long humidityNumber = humidityMapper.getDestination(temperatureNumber);
                long location = locationMapper.getDestination(humidityNumber);
                if (location < lowestLocation) {
                    lowestLocation = location;
                    seedWithLowestLocation = seed;
                }
            }
            return lowestLocation;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void populateSeedsToSoilMap(BufferedReader bufferedReader) throws IOException {
//        //discard blank
//        bufferedReader.readLine();
//        //discard section title
//        bufferedReader.readLine();
//        String line = bufferedReader.readLine();
//        while (!line.isBlank()) {
//            Matcher matcher = PATTERN.matcher(line);
//            matcher.matches();
//
//        }
//    }

    private void populateSeedsList(String line) {
        String[] parts = line.split(":");
        String[] seedStrings = parts[1].trim().split("\s");
        Arrays.stream(seedStrings).map(Long::parseLong).forEach(seeds::add);
    }
    private LongStream seedsListStreamed(String line) {
        System.out.println("Building seed streams");
        String[] stringNums = line.split(":")[1].trim().split("\s");
        List<LongStream> longStreamArray = new ArrayList<>();
        LongStream bigLongStream = null;
        Long streamLength = 0L;
        for (int i = 0; i < stringNums.length; i+= 2) {
            long start = Long.parseLong(stringNums[i]);
            long range = Long.parseLong(stringNums[i+1]);
            streamLength += range;
            LongStream longStream = LongStream.range(start, start + range);
            if (bigLongStream == null) {
                bigLongStream = longStream;
            } else {
                bigLongStream = LongStream.concat(bigLongStream, longStream);
            }
        }
        Almanac.totalSeeds = streamLength;
        System.out.println(streamLength);
        return bigLongStream;
    }

    @Override
    public Long runPartTwo(BufferedReader bufferedReader) {
        try {
            LongStream seedStream = seedsListStreamed(bufferedReader.readLine());
            //works for SMALL data
//            populateSeedsList2(bufferedReader.readLine());
            //discard blank line
            String line = bufferedReader.readLine();
            //discard useless text line
            System.out.println("doing actual processing");
            bufferedReader.readLine();
            line = bufferedReader.readLine();
            var soilMapper = new FarmingMapper();
            while (!line.isBlank()) {
                soilMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            var fertiliserMapper = new FarmingMapper();
            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (!line.isBlank()) {
                fertiliserMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var waterMapper = new FarmingMapper();
            while (!line.isBlank()) {
                waterMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var lightMapper = new FarmingMapper();
            while (!line.isBlank()) {
                lightMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var temperatureMapper = new FarmingMapper();
            while (!line.isBlank()) {
                temperatureMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var humidityMapper = new FarmingMapper();
            while (!line.isBlank()) {
                humidityMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
            }

            //discard 2 lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            var locationMapper = new FarmingMapper();
            while (!line.isBlank()) {
                locationMapper.mappings.add(FarmingMapper.DestSourceRange.fromLine(line));
                line = bufferedReader.readLine();
                if(line == null) break;
            }

            //trim mappers to only care for ranges catered for all the way through

            //or start from lowest location value in almanac and work up


            return seedStream.map(seed -> {
                printIndex();
                return locationMapper.getDestination(
                    humidityMapper.getDestination(
                        temperatureMapper.getDestination(
                            lightMapper.getDestination(
                                waterMapper.getDestination(
                                    fertiliserMapper.getDestination(
                                        soilMapper.getDestination(seed)))))));
            }).reduce(Long.MAX_VALUE, Long::min);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
