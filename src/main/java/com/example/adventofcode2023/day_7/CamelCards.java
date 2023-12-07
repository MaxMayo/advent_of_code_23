package com.example.adventofcode2023.day_7;

import com.example.adventofcode2023.common.TestDriven;
import lombok.Getter;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class CamelCards implements TestDriven<Long> {

    private static final List<String> CARDS_ORDERED_BY_STRENGTH = List.of("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");

    @Getter
    private class Hand implements Comparable<Hand> {
        private final String originalString;
        private List<String> cards = new ArrayList<>(5);
        private List<HandType> handStrengths = List.of(HandType.FIVE, HandType.FOUR, HandType.FULL, HandType.THREE, HandType.TWO_PAIR, HandType.ONE_PAIR, HandType.HIGH_CARD);

        private final HandType handType;

        private Hand(String cardsPart) {
            originalString = cardsPart;
            for(char c : cardsPart.toCharArray()) {
                cards.add(String.valueOf(c));
            }
            long numDifferentCards = cards.stream().distinct().count();
            List<String> differentCards;
            handType = switch ((int) numDifferentCards) {
                case 1:
                    yield HandType.FIVE;
                case 2:
                    differentCards = cards.stream().distinct().toList();
                    long cardOneCount = cards.stream().filter(cardInList -> cardInList.equals(differentCards.get(0))).count();
                    long cardTwoCount = cards.stream().filter(cardInList -> cardInList.equals(differentCards.get(1))).count();
                    if ((cardOneCount == 4 || cardTwoCount == 4) && (cardOneCount == 1) || (cardTwoCount == 1)) {
                        yield HandType.FOUR;
                    } else if (cardOneCount == 3 || cardTwoCount == 3) {
                        yield HandType.FULL;
                    } else {
                        throw new RuntimeException("bad");
                    }
                case 3:
                    differentCards = cards.stream().distinct().toList();
                    long numMostCards = differentCards.stream().map(card -> cards.stream()
                            .filter(candidateCard -> candidateCard.equals(card))
                            .count()).reduce(0L, Long::max);
                    if (numMostCards == 3) {
                        yield HandType.THREE;
                    } else if (numMostCards == 2) {
                        yield HandType.TWO_PAIR;
                    }
                case 4:
                    yield HandType.ONE_PAIR;
                case 5:
                    yield HandType.HIGH_CARD;

                default:
                    throw new IllegalStateException("Unexpected value: " + (int) numDifferentCards);
            };
        }

        private long handScoreDifference(Hand other) {
            long thisRank = handStrengths.indexOf(this.handType);
            long otherRank = handStrengths.indexOf(other.handType);
            return Math.clamp(otherRank - thisRank, -1, 1);
        }

        @Override
        public int compareTo(Hand other) {
            long handTypeDifference = handScoreDifference(other);
            if (handTypeDifference == 0) {
                return compareSimilarHand(other);
            } else return (int) handTypeDifference;
        }

        public int compareSimilarHand(Hand other) {
            for (int i = 0; i < 5; i++) {
                int cardDifference = compareCards(this.cards.get(i), other.cards.get(i));
                if (cardDifference != 0) {
                    return cardDifference;
                }
            }
            throw new RuntimeException("hands were completely equal " + this.originalString + " " + other.originalString);
        }

        //positive if held is "higher" than other
        private int compareCards(String held, String other) {
            if (held.equals("7") && other.equals("T")) {
                System.out.println("hi");
            }
            int indexOfHeld = CARDS_ORDERED_BY_STRENGTH.indexOf(held);
            int indexOfOther = CARDS_ORDERED_BY_STRENGTH.indexOf(other);
            return indexOfOther - indexOfHeld;
        }
    }

    private enum HandType {
        FIVE, FOUR, FULL, THREE, TWO_PAIR, ONE_PAIR, HIGH_CARD
    }

    private static long totalHands = 0;

    @Override
    public Long runPartOne(BufferedReader bufferedReader) {
        Map<String, Long> game = bufferedReader.lines()
                .peek(line -> totalHands++)
                .map(line -> {
                    String[] segments = line.split("\s");
                    return new AbstractMap.SimpleEntry<>(segments[0], Long.parseLong(segments[1]));
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
        PriorityQueue<Hand> priorityQueue = new PriorityQueue<>();
        List<Hand> allHands = game.keySet().stream().map(Hand::new).sorted().toList();
        priorityQueue.addAll(allHands);
        long totalWinnings = 0;
        for (long i = 0; i < totalHands; i++) {
            long rankScore = i + 1;
            Hand currentHand = priorityQueue.poll();
            long bid = game.get(currentHand.getOriginalString());
            totalWinnings += bid * rankScore;
        }

        return totalWinnings;
    }

    @Override
    public Long runPartTwo(BufferedReader bufferedReader) {
        return null;
    }
}
