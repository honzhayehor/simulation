package entities;

import attributes.Renderable;
import logic.Cell;

public class Tree extends Entity implements Renderable {

    public Tree(Cell cell) {
        super(cell, false);
    }

    @Override
    public String getAvatar() {
        return "\uD83C\uDF33";
    }
}
