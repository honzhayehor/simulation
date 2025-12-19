package logic;

import entities.Entity;

import java.util.*;

public class IDs {
    private static int lastId = 0;

    public static synchronized int assignID() {
        return ++lastId;
    }
}
