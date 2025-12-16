package entities;

import logic.Coordinates;

public class Tree extends Entity{

    public Tree(Coordinates coordinates) {
        super(coordinates, false);
    }

    @Override
    public String getAvatar() {
        return "\uD83C\uDF33";
    }
}
