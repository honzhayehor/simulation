package entities;

import logic.Coordinates;

public class Rock extends Entity{
    public Rock(Coordinates coordinates) {
        super(coordinates, false);
    }

    @Override
    public String getAvatar() {
        return "\uD83E\uDEA8";
    }
}
