package com.example.adventofcode2023.day_10;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SimpleTile {

    private final char character;
    private final Point location;
    private SimpleTile tile1;
    private SimpleTile tile2;

    public void setConnection(SimpleTile tile, int index) {
        if (index == 0) {
            tile1 = tile;
        } else {
            tile2 = tile;
        }
    }

}
