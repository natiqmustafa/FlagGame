package com.natodroid.flaggamenew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.natodroid.flaggamenew.activities.PlayingActivity;
import com.natodroid.flaggamenew.common.PrefManager;
import com.natodroid.flaggamenew.config.Config;
import com.natodroid.flaggamenew.data.CountryData;
import com.natodroid.flaggamenew.data.QuestionData;
import com.natodroid.flaggamenew.helper.DbHelper;
import com.natodroid.flaggamenew.model.GameLavel;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PrefManager prefManager = new PrefManager(this);
        prefManager.setGameLevel(GameLavel.EASY);
        Config.GAME_LEVEL = prefManager.getGameLevel();

        Button button = (Button) findViewById(R.id.play_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PlayingActivity.class));
            }
        });
    }
}
