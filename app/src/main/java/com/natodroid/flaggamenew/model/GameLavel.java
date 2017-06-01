package com.natodroid.flaggamenew.model;

/**
 * Created by natiqmustafa on 02.03.2017.
 */

public enum GameLavel {
    EASY(1), MEDIUM(2), HARD(3), HARDEST(4);

    private final int value;
    GameLavel(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GameLavel toLevel(String myEnumString) {
        try {
            return valueOf(myEnumString);
        } catch (Exception ex) {
            return EASY;
        }
    }
}
