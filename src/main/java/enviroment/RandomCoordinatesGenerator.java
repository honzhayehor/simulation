package enviroment;

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

    public int nextX() {
        if (xIndex >= xValues.size()) throw new UnsupportedOperationException("No more X values");
        return xValues.get(xIndex++);
    }

    public int nextY() {
        if (yIndex >= yValues.size()) throw new UnsupportedOperationException("No more Y values");
        return yValues.get(yIndex++);
    }
}
