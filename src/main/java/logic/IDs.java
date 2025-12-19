package logic;

import entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class IDs {
    private final List<Integer> ids = new ArrayList<>();
    private final Random random = new Random();

    public void addEntityId(Entity entity) {

    }

    private boolean checkIfIdInList(int id) {
        if (ids.contains(id)) {
            return true;
        }
        return false;
    }

}
