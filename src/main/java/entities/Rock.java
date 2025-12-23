package entities;

import attributes.Renderable;
import logic.Cell;

public class Rock extends Entity implements Renderable {
    public Rock(Cell cell) {
        super(cell, false);
    }

    @Override
    public String getAvatar() {
        return "\uD83E\uDEA8";
    }
}
