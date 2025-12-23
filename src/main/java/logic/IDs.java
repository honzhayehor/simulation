package logic;

public class IDs {
    private static int lastId = 0;

    public static synchronized int assignId() {
        return ++lastId;
    }
}
