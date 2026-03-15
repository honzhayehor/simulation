package enviroment;

import java.util.*;

public class Cell {
    private final int x;
    private final int y;

    Cell(int x, int y) {
        validateCoordinates(x, y);
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    private static void validateCoordinates(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("X and Y have to be greater or equal to zero");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell cell)) return false;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}