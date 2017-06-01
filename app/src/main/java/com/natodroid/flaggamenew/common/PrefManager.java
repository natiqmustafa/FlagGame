package com.natodroid.flaggamenew.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.natodroid.flaggamenew.config.Config;
import com.natodroid.flaggamenew.model.GameLavel;

/**
 * Created by natiqmustafa on 02.03.2017.
 */

public class PrefManager {

    private static final String TAG = "PrefManager";
    private static final String PREF_NAME = "flag_game_pref";
    private static final int PRIVATE_MODE = 0;

    private static final String PREF_PARAM_MODE = "pref_param_mode";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }



    public GameLavel getGameLevel() {
        return GameLavel.toLevel(preferences.getString(PREF_PARAM_MODE, GameLavel.EASY.toString()));
    }

    public void setGameLevel(GameLavel level) {
        editor = preferences.edit();
        editor.putString(PREF_PARAM_MODE, level.toString());
        editor.apply();
    }
}
