package logic;

public enum Speed {
    ZEBRA(2),
    TIGER(2);
    private final int speed;

    Speed(int speed) {this.speed = speed;}

    public int getSpeed() {return this.speed;}
}
