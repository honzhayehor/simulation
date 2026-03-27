package logic.concrete;

import logic.interfaces.Pathfinder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {

    @Test
    void creatingClassWithNullInConstructorRaisesError() {
        assertThrows(NullPointerException.class, () -> new AStar(null) );
    }
}