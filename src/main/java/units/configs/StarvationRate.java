package units.configs;

public enum StarvationRate {
    ZEBRA(10),
    WOLF(15);

    private final int rate;

    StarvationRate(int starvation) {
        rate = starvation;
    }

    public int getRate() {
        return rate;
    }
}
