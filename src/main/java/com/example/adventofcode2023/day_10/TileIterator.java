package com.example.adventofcode2023.day_10;

import java.util.Iterator;

public class TileIterator implements Iterator<SimpleTile> {

    private SimpleTile prevTile;
    private SimpleTile currentTile;
    private SimpleTile nextTile;

    public TileIterator(SimpleTile tile, SimpleTile prevTile) {
        this.prevTile = prevTile;
        this.currentTile = tile;
        this.nextTile = null;
    }

    /**
     * Current tile probably has two linked ones.
     * The previous tile is either 1 or 2.
     * Find which the previous one is and ignore it. Look at the other.
     *
     * @return
     */
    @Override
    public boolean hasNext() {
        if (currentTile == null || currentTile.getTile1() == null || currentTile.getTile2() == null) return false;
        return (currentTile.getTile1().getLocation().equals(prevTile.getLocation())
                || currentTile.getTile2().getLocation().equals(prevTile.getLocation()));
    }

    //get next item
    @Override
    public SimpleTile next() {
        var tileToReturn = nextTile;
        prevTile = currentTile;
        currentTile = nextTile;
        nextTile = null;
        if (currentTile.getTile1().getLocation().equals(prevTile.getLocation())) {
            nextTile = currentTile.getTile2();
        }
        if (currentTile.getTile2().getLocation().equals(prevTile.getLocation())) {
            nextTile = currentTile.getTile1();
        }
        return tileToReturn;
    }
}
