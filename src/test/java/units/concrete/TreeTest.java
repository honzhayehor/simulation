package units.concrete;

import enviroment.WorldMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import units.abstraction.Entity;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TreeTest {

    private final WorldMap worldMap = Mockito.mock(WorldMap.class);


    @BeforeEach
    void resetIdCounter() throws Exception {
        Field idField = Entity.class.getDeclaredField("globalIdCounter");
        idField.setAccessible(true);
        ((AtomicInteger) idField.get(null)).set(0);
    }

    @Test
    void grassIdsAreIncremented() {
        Tree tree1 = Tree.create(worldMap);
        Tree tree2 = Tree.create(worldMap);
        assertEquals(1, tree1.getId());
        assertEquals(2, tree2.getId());
    }

}