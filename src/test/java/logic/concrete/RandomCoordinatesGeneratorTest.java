package logic.concrete;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomCoordinatesGeneratorTest {

    @Test
    void givenTenTenCoordinatesReturnsCorrect() {
        RandomCoordinatesGenerator randomCoordinatesGenerator = new RandomCoordinatesGenerator(1, 10, 1, 10);
        RandomCoordinatesGenerator.Coordinates coordinates = randomCoordinatesGenerator.next();
        assertTrue((coordinates.x() <= 10) && (coordinates.y() <= 10));
    }

    @Test
    void throwsExpectionWhenReachedEnd() {
        RandomCoordinatesGenerator randomCoordinatesGenerator = new RandomCoordinatesGenerator(1, 10, 1, 10);
        for (int i=0; i<10; i++) {
            randomCoordinatesGenerator.next();
        }
        assertThrows(UnsupportedOperationException.class, randomCoordinatesGenerator::next);
    }

    @Test
    void sizeIsCorrect() {
        RandomCoordinatesGenerator randomCoordinatesGenerator = new RandomCoordinatesGenerator(1, 5, 1, 5);
        List<RandomCoordinatesGenerator.Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());

        assertEquals(5, coordinatesList.size());
    }

    @Test
    void whenNegativeNumbersPassedAllGood() {
        assertDoesNotThrow(() -> new RandomCoordinatesGenerator(-5, 0, -5, 0));
    }

    @Test
    void whenNegativeNumbersPassedCreatesNormalCoordinates() {
        RandomCoordinatesGenerator randomCoordinatesGenerator = new RandomCoordinatesGenerator(-5, -1, -5, -1);
        List<RandomCoordinatesGenerator.Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());
        coordinatesList.add(randomCoordinatesGenerator.next());

        coordinatesList.forEach(e -> assertTrue((inRange(e.x(), -5, -1)) && (inRange(e.y(), -5, -1))));
    }

    private static boolean inRange(int num, int min, int max) {
        return num >= min && num <= max;
    }


}