package entities;

import logic.Cell;

public class Rock extends Entity{
    public Rock(Cell cell) {
        super(cell, false);
    }

    @Override
    public String getAvatar() {
        return "\uD83E\uDEA8";
    }
}
