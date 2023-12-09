package com.example.adventofcode2023.day_7;

import com.example.adventofcode2023.common.TestDriven;
import lombok.Getter;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class CamelCards implements TestDriven<Long> {

    private static final List<String> CARDS_ORDERED_BY_STRENGTH = List.of("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");
    private static final List<String> CARDS_ORDERED_BY_STRENGTH_JACKED = List.of("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J");
    // bad
    private static boolean IS_PART_ONE;

    @Getter
    private class Hand implements Comparable<Hand> {
        private final String originalString;
        private String jackedString;
        private List<String> originalCards = new ArrayList<>(5);
        private List<String> cards = new ArrayList<>(5);
        private List<HandType> handStrengths = List.of(HandType.FIVE, HandType.FOUR, HandType.FULL, HandType.THREE, HandType.TWO_PAIR, HandType.ONE_PAIR, HandType.HIGH_CARD);

        private HandType handType;

        public Hand(String cardsPart) {
            this(cardsPart, true);
        }

        public Hand(String cardsPart, boolean isPartOne) {
            IS_PART_ONE = isPartOne;
            originalString = cardsPart;
            jackedString = originalString;
            for (char c : cardsPart.toCharArray()) {
                cards.add(String.valueOf(c));
                originalCards.add(String.valueOf(c));
            }
            long numDifferentCards = cards.stream().distinct().count();
            handType = calculateHandType((int) numDifferentCards);
        }

        private HandType calculateHandType(int numDifferentCards) {
            List<String> differentCards;
            return switch (numDifferentCards) {
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
            return otherRank - thisRank;
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
            // if not already returned, cards must have been equal ignoring jacked tie-breaker rules.
            for (int i = 0; i < 5; i++) {
                String heldCard = this.cards.get(i);
                String otherCard = other.cards.get(i);
                if (heldCard.equals(otherCard)) { // both are same, compare them by original card values
                    heldCard = this.originalCards.get(i);
                    otherCard = other.originalCards.get(i);
                }
                int cardDifference = compareCards(heldCard, otherCard, CARDS_ORDERED_BY_STRENGTH_JACKED);
                if (cardDifference != 0) {
                    return cardDifference;
                }
            }
            throw new RuntimeException("hands were completely equal " + this.originalString + " " + other.originalString);
        }

        //positive if held is "higher" than other
        private int compareCards(String held, String other) {
            return compareCards(held, other, CARDS_ORDERED_BY_STRENGTH);
        }

        private int compareCards(String held, String other, List<String> cardsList) {
            int indexOfHeld = cardsList.indexOf(held);
            int indexOfOther = cardsList.indexOf(other);
            return indexOfOther - indexOfHeld;
        }

        private void promoteHand() {
            //look at whole hand, get all letters
            List<String> distinctCards = getCards().stream().distinct().filter(card -> !card.equals("J")).toList();
            int numDistinctCards = distinctCards.size();
            //create new hand values for each possible combo of J replacements
            //jack locations
            List<Integer> indexesOfJackLocations = new ArrayList<>(5);
            if (originalString.contains("J")) {
                for (int i = 0; i < 5; i++) {
                    if (cards.get(i).equals("J")) indexesOfJackLocations.add(i);
                }
            }

            int numJacks = indexesOfJackLocations.size();

            //5 same
            //promote or do nothing
            if (numDistinctCards == 0) { // JJJJJ
                substituteJs("A");
                return;
            } else if (numDistinctCards == 1) { //JAAAA, JJAAA, JJJAA, JJJJA
                // promote to full house
                substituteJs(distinctCards.getFirst());
            } else if (numDistinctCards == 2) {//JAAAB, JAABB, JABBB | JJAAB, JJABB | JJJAB
                String firstCharacter = distinctCards.getFirst();
                String secondCharacter = distinctCards.getLast();
                if (numJacks == 1) { //JAAAB, JAABB, JABBB
                    if ((originalString.replaceAll(firstCharacter, "").length() == 2) //JAAAB -> JB
                            || (originalString.replaceAll(secondCharacter, "").length() == 2) //JABBB -> JA
                    ) { // You've never seen a prettier ternary. makes AAAAB or BABBB
                        // promoted to four of a kind
                        substituteJs(originalString.replaceAll(firstCharacter, "").length() == 2 ? firstCharacter : secondCharacter);
                    } else { // JAABB
                        //substitute which (A or B) is more powerful, promoted to full house
                        substituteJs(compareCards(firstCharacter, secondCharacter) > 0 ? firstCharacter : secondCharacter);
                    }
                } else if (numJacks == 2) { // JJAAB, JJABB
                    //which one is there more of
                    substituteJs(originalString.replaceAll(firstCharacter, "").length() == 1 ? firstCharacter : secondCharacter);
                }
            } else if (numDistinctCards == 3) { // JAABC, JABBC, JABCC | JJABC
                if (numJacks == 1) { // be thankful I'm not nesting ternaries
                    // I'm going to nest the ternaries.
                    // I found a better way
                    // makes a three of a kind
                    substituteJs(getStrongestCard(distinctCards.get(0), distinctCards.get(1), distinctCards.get(2)));
                } else {
                    // also makes a three of a kind
                    substituteJs(getStrongestCard(distinctCards.get(0), distinctCards.get(1), distinctCards.get(2)));
                }
            } else if (numDistinctCards == 4) { // JABCD
                // lots of copy-paste, makes a two of a kind
                substituteJs(getStrongestCard(distinctCards.get(0), distinctCards.get(1), distinctCards.get(2), distinctCards.get(3)));
            } else if (numDistinctCards == 5) { //no jacks to replace

            }
            cards = new ArrayList<>(5);
            for (char c : jackedString.toCharArray()) {
                cards.add(String.valueOf(c));
            }
            long numDifferentCards = cards.stream().distinct().count();
            handType = calculateHandType((int) numDifferentCards);
        }

        private String getStrongestCard(String... cards) {
            String strongestCard = cards[0];
            for (int i = 1; i < cards.length; i++) {
                if (compareCards(cards[i], strongestCard) > 0) {
                    strongestCard = cards[i];
                }
            }
            return strongestCard;
        }

        private void substituteJs(String substitution) {
            jackedString = jackedString.replaceAll("J", substitution);
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
        Map<String, Long> game = bufferedReader.lines()
                .peek(line -> totalHands++)
                .map(line -> {
                    String[] segments = line.split("\s");
                    return new AbstractMap.SimpleEntry<>(segments[0], Long.parseLong(segments[1]));
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
        PriorityQueue<Hand> priorityQueue = new PriorityQueue<>();
        List<Hand> allHands = game.keySet().stream().map(Hand::new).sorted().toList();
        allHands.forEach(Hand::promoteHand);
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
}
