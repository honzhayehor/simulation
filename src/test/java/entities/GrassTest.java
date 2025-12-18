package entities;

import logic.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {

    @Test
    void testGrassReturnsCorrectString() {
        Grass grass = new Grass(new Cell(1,1));
        assertEquals("\uD83C\uDF3F", grass.getAvatar());
    }
}