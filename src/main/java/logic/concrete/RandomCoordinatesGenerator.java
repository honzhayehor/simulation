package logic.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomCoordinatesGenerator {
    private final List<Integer> xValues;
    private final List<Integer> yValues;
    private int xIndex = 0;
    private int yIndex = 0;

    public RandomCoordinatesGenerator(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        xValues = generateShuffled(minWidth, maxWidth);
        yValues = generateShuffled(minHeight, maxHeight);
    }

    private List<Integer> generateShuffled(int min, int max) {
        List<Integer> list = new ArrayList<>();
        for (int i = min; i <= max; i++) list.add(i);
        Collections.shuffle(list, new Random(42));
        return list;
    }

    public record Coordinates(int x, int y) {}

    public Coordinates next() {
        if (xIndex >= xValues.size() || yIndex >= yValues.size()) {
            throw new UnsupportedOperationException("No more coordinates");
        }
        return new Coordinates(xValues.get(xIndex++), yValues.get(yIndex++));
    }
}
