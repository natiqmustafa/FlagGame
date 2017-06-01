package com.natodroid.flaggamenew.config;

import com.natodroid.flaggamenew.model.GameLavel;

/**
 * Created by natiqmustafa on 02.03.2017.
 */

public class Config {
    public static final String ANSWER_COLUMN = "short_name"; // short_name, long_name

    public static final int EASY_MODE_NUM = 5; // NUMBER OF QUESTION IN EASY MODE
    public static final int MEDIUM_MODE_NUM = 50;
    public static final int HARD_MODE_NUM = 100;
    public static final int HARDEST_MODE_NUM = 200;


    public static final int START_GAME_TIMER = 6000;

    public static final long EASY_MODE_TIME = 10000L + 1000; // TIME OF QUESTION IN EASY MODE
    public static final long MEDIUM_MODE_TIME = 7000L + 1000;
    public static final long HARD_MODE_TIME = 6000L + 1000;
    public static final long HARDEST_MODE_TIME = 4000L + 1000;

    public static GameLavel GAME_LEVEL  = GameLavel.EASY;


}
