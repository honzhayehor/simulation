package entities;

import logic.Cell;

public class Tree extends Entity{

    public Tree(Cell cell) {
        super(cell, false);
    }

    @Override
    public String getAvatar() {
        return "\uD83C\uDF33";
    }
}
