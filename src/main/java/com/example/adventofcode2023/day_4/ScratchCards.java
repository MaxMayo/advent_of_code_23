package com.example.adventofcode2023.day_4;

import lombok.Getter;
import lombok.Setter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.example.adventofcode2023.common.TestDriven;

public class ScratchCards implements TestDriven<Integer> {
    
    private String REGEX = "(?<cardGroup>Card\\s+\\d+):(?<winningNums>(\\s+\\d+)+)\\s\\|(?<foundNums>(\\s+\\d+)+)";
    private Pattern PATTERN = Pattern.compile(REGEX);

    @Override
    public Integer runPartOne(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .map(PATTERN::matcher)
            .map(matcher -> {
                matcher.matches();
                String winningNums = matcher.group("winningNums");
                String foundNums = matcher.group("foundNums");
                List<Integer> winningNumList = Arrays.stream(winningNums.split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .map(Integer::parseInt)
                    .toList();
                List<Integer> foundNumList = Arrays.stream(foundNums.split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .map(Integer::parseInt)
                    .toList();
                int score = 0;
                for (Integer winningNum : winningNumList) {
                    if (foundNumList.contains(winningNum)) {
                        if (score == 0) {
                            score = 1;
                        } else {
                            score *= 2;
                        }
                    }
                }
                return score;
            }).reduce(0, Integer::sum);
    }

    @Override
    public Integer runPartTwo(BufferedReader bufferedReader) {
        List<Card> cards = bufferedReader.lines()
            .map(PATTERN::matcher)
            .map(matcher -> {
                matcher.matches();
                String winningNums = matcher.group("winningNums");
                String foundNums = matcher.group("foundNums");
                List<Integer> winningNumList = Arrays.stream(winningNums.split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .map(Integer::parseInt)
                    .toList();
                List<Integer> foundNumList = Arrays.stream(foundNums.split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .map(Integer::parseInt)
                    .toList();
                int score = 0;
                for (Integer winningNum : winningNumList) {
                    if (foundNumList.contains(winningNum)) {
                        score += 1;
                    }
                }
                return new Card(winningNumList, foundNumList, score);
            }).collect(Collectors.toList());
        for (int i = cards.size() - 1; i >= 0; i--) {
            Card card = cards.get(i);
            for (int j = 0; j < card.getMatches(); j++) {
                int childCardIndex = j + i + 1;
                if (childCardIndex < cards.size()) {
                    Card childCard = cards.get(childCardIndex);
                    card.getWonCards().add(childCard);
                }
            }
        }
        //cards are updated, now go through and find total num cards won
        return cards.stream()
            .map(Card::findDescendantScore)
            .reduce(0, Integer::sum) + cards.size();
    }

    @Getter
    @Setter
    private class Card {
        private List<Integer> winningNums;
        private List<Integer> foundNums;
        private int matches;
        private List<Card> wonCards = new ArrayList<>();

        public Card(List<Integer> winningNums, List<Integer> foundNums, int matches) {
            this.winningNums = winningNums;
            this.foundNums = foundNums;
            this.matches = matches;
        }

        public int findDescendantScore() {
            int numDescendants = 0;
            if (wonCards.isEmpty()) {
                return 0;
            } else {
                for (Card card : wonCards) {
                    numDescendants += 1;
                    numDescendants += card.findDescendantScore();
                }
            }
            return numDescendants;
        }
    }
}
