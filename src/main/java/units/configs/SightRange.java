package units.configs;

public enum SightRange {
    ZEBRA(2);
    private final int vision;
    SightRange(int vision) {
        this.vision = vision;
    }

    public int getVisionRange() {return vision;}
}
