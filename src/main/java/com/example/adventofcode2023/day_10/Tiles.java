package com.example.adventofcode2023.day_10;

import com.example.adventofcode2023.common.TestDriven;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Tiles implements TestDriven<Integer> {

    @Override
    public Integer runPartOne(BufferedReader bufferedReader) {
        List<List<Character>> charMap = bufferedReader.lines()
                .map(String::chars)
                .map(char_stream -> char_stream.mapToObj(c -> (char) c).toList())
                .toList();

        Point sLocation = getSLocation(charMap);
        SimpleTile sStile = new SimpleTile('S', sLocation);

        List<List<SimpleTile>> tileMap = mapTilesFromChars(charMap);
        linkTiles(tileMap);

        boolean sNotRefound = true;
        // assume tile joins.
        SimpleTile currentTile = tileMap.get((sLocation.y() - 1)).get(sLocation.x());
        List<Point> possibleConnections = possibleConnections(currentTile);

        TileIterator tileIterator = new TileIterator(currentTile, sStile);

        SimpleTile nextTile;
        int numTile = 1;
        while (tileIterator.hasNext()) { // follow map all the way back to s
            sNotRefound = currentTile.getCharacter() == 'S';
            numTile++;
            currentTile = tileIterator.next();
        }
        System.out.println(numTile);

        return numTile/2;
    }

//    private boolean areTilesLinkable

    private static void linkTiles(List<List<SimpleTile>> tileMap) {
        int height = tileMap.size();
        int width = tileMap.getFirst().size();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                linkTile(x, y, tileMap);
            }
        }
    }

    private static void linkTile(int x, int y, List<List<SimpleTile>> tileMap) {
        int height = tileMap.size();
        int width = tileMap.getFirst().size();
        SimpleTile currentTile = tileMap.get(y).get(x);
        // if tile is '.', return
        if (currentTile.getCharacter() == '.' || currentTile.getCharacter() == 'S') return;

        // get attempted connections
        List<Point> attemptedConnections = possibleConnections(currentTile);

        // filter out connections not on map
        List<Point> attemptedConnectionsOnMap = new ArrayList<>(2);
        if (pointWithinBounds(attemptedConnections.getFirst(), width, height)) {
            attemptedConnectionsOnMap.add(attemptedConnections.getFirst());
        }
        if (pointWithinBounds(attemptedConnections.getLast(), width, height)) {
            attemptedConnectionsOnMap.add(attemptedConnections.getLast());
        }

        // are the connections returned by the next tiles? set if so.
        Point currentAttemptedConnection;
        List<Point> attemptedConnectionsFromCandidate;
        // for each connection out from this tile
        for (int i = 0; i < attemptedConnectionsOnMap.size(); i++) {
            currentAttemptedConnection = attemptedConnectionsOnMap.get(i);
            // get attempted valid connections made by that candidate tile
            attemptedConnectionsFromCandidate = possibleConnections(
                    tileFromPoint(currentAttemptedConnection, tileMap));
            // confirm c
            if (attemptedConnectionsFromCandidate.contains(currentTile.getLocation())) { //yes
                currentTile.setConnection(tileFromPoint(currentAttemptedConnection, tileMap), i);
            }
        }
    }

//    private static List<Point> connectionAttemptsWithinBounds(SimpleTile tile, int widthLimit, int heightLimit) {
//        var connectionAttempt1 = tile
//        List<Point> attemptedConnectionsWithinBounds = new ArrayList<>();
//        if()
//    }

    private static SimpleTile tileFromPoint(Point point, List<List<SimpleTile>> tileMap) {
        return tileMap.get(point.y()).get(point.x());
    }

    private static boolean pointWithinBounds(Point point, int width, int height) {
        return point.x() > -1 && point.x() < width && point.y() < height && point.y() > -1;
    }

    private static List<Point> possibleConnections(SimpleTile tile) {
        List<Point> possibleConnectionsArray = new ArrayList<>(2);
        int tile1xdir = 0;
        int tile1ydir = 0;
        int tile2xdir = 0;
        int tile2ydir = 0;
        switch (tile.getCharacter()) {
            case '|':
                tile1xdir = 0;
                tile2xdir = 0;
                tile1ydir = -1;
                tile2ydir = 1;
                break;
            case '-':
                tile1xdir = -1;
                tile2xdir = 1;
                tile1ydir = 0;
                tile2ydir = 0;
                break;
            case 'L': //    L is a 90-degree bend connecting north and east.
                tile1xdir = 0;
                tile2xdir = 1;
                tile1ydir = -1;
                tile2ydir = 0;
                break;
            case 'J': //    J is a 90-degree bend connecting north and west.
                tile1xdir = 0;
                tile2xdir = -1;
                tile1ydir = -1;
                tile2ydir = -1;
                break;
            case '7': //    7 is a 90-degree bend connecting south and west.
                tile1xdir = -1;
                tile2xdir = 0;
                tile1ydir = 0;
                tile2ydir = 1;
                break;
            case 'F': //    F is a 90-degree bend connecting south and east.
                tile1xdir = 1;
                tile2xdir = 0;
                tile1ydir = 0;
                tile2ydir = -1;
                break;
            case '.': //    . is ground; there is no pipe in this tile.
                //ignore
                break;
            case 'S': //    S is the starting point
                break;
        }
        if (tile1xdir == 0 && tile1ydir == 0 && tile2xdir == 0 && tile2ydir == 0) {
            return possibleConnectionsArray; // no connections to make for this tile, was either S or .
        }
        possibleConnectionsArray.add(new Point(tile1xdir + tile.getLocation().x(), tile1ydir + tile.getLocation().y()));
        possibleConnectionsArray.add(new Point(tile2xdir + tile.getLocation().x(), tile1ydir + tile.getLocation().y()));
        return possibleConnectionsArray;
    }

    private static List<List<SimpleTile>> mapTilesFromChars(List<List<Character>> charMap) {
        int height = charMap.size();
        int width = charMap.getFirst().size();
        List<List<SimpleTile>> outerList = new ArrayList<>(height);
        List<SimpleTile> innerList;
        SimpleTile currentTile;
        for (int y = 0; y < height; y++) {
            innerList = new ArrayList<>(width);
            for (int x = 0; x < width; x++) {
                currentTile = new SimpleTile(charMap.get(y).get(x), new Point(x, y));
                innerList.add(currentTile);
            }
            outerList.add(innerList);
        }
        return outerList;
    }

    private static Point getSLocation(List<List<Character>> charMap) {
        int height = charMap.size();
        int width = charMap.getFirst().size();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (charMap.get(y).get(x) == 'S') return new Point(x, y);
            }
        }
        throw new RuntimeException("Couldn't find S");
    }

    @Override
    public Integer runPartTwo(BufferedReader bufferedReader) {
        return null;
    }
}
