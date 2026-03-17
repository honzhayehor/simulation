package logic.interfaces;

import enviroment.Cell;
import java.util.List;

public interface Pathfinder {
    List<Cell> findPath(Cell currentPos, Cell targetPos);
}
