package com.natodroid.flaggamenew.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natodroid.flaggamenew.R;
import com.natodroid.flaggamenew.config.Config;
import com.natodroid.flaggamenew.data.QuestionData;
import com.natodroid.flaggamenew.model.GameResult;
import com.natodroid.flaggamenew.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by natiqmustafa on 02.03.2017.
 */

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "PlayingActivity";
    private Button answerAButton;
    private Button answerBButton;
    private Button answerCButton;
    private Button answerDButton;
    private ImageView image;

    private TextView txtScore;
    private TextView txtQuestion;

    private TextView txtCountDown;
    private TextView txtStartGameTimer;

    private CountDownTimer countDownTimer;
    private RelativeLayout relativeLayoutCounter;
    private ProgressBar progressBar;

    private int questionIntex;
    private boolean startGame;

    private List<Question> listofQuestions;
    private static int LIMIT;
    private static long TIME;
    private static int START_TIMER;

    private long startGameTime;
    private long gameDuration;

    private GameResult gameResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_byflag);
        initVariable();
        initComponent();
        showQuestion();

//        chronometer.start();



    }
    //http://www.androidtutorialshub.com/android-count-down-timer-tutorial/

    private void initVariable() {
        gameResult = new GameResult();
        listofQuestions = new ArrayList<>();
        QuestionData questionData = new QuestionData(this);
        questionData.generateQuiz();
        listofQuestions = questionData.getQuestionList();
        LIMIT = questionData.getLimit(Config.GAME_LEVEL);
        TIME = questionData.getTime(Config.GAME_LEVEL);
        START_TIMER = Config.START_GAME_TIMER;
        startGame =false;
    }

    private void initComponent() {
        relativeLayoutCounter = (RelativeLayout) findViewById(R.id.relative_layout_counter);

        progressBar = (ProgressBar) findViewById(R.id.progressBarCircle);
        int PROGESS = START_TIMER / 1000;
        Log.d(TAG, "initComponent: " + PROGESS);
        progressBar.setMax(PROGESS);
        progressBar.setProgress(PROGESS);



        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setText(String.valueOf(0));

        TextView txtGameLevel = (TextView) findViewById(R.id.txtGameLevel);
        txtGameLevel.setText(Config.GAME_LEVEL.toString());

        txtStartGameTimer = (TextView) findViewById(R.id.txtStartGameTimer);

        answerAButton = (Button) findViewById(R.id.btnAnswerA);
        answerBButton = (Button) findViewById(R.id.btnAnswerB);
        answerCButton = (Button) findViewById(R.id.btnAnswerC);
        answerDButton = (Button) findViewById(R.id.btnAnswerD);

        image = (ImageView) findViewById(R.id.question_flag);

        answerAButton.setOnClickListener(this);
        answerBButton.setOnClickListener(this);
        answerCButton.setOnClickListener(this);
        answerDButton.setOnClickListener(this);

        txtCountDown = (TextView) findViewById(R.id.txtCountDown);
        initListener();


    }

    private void initListener() {


        final CountDownTimer cdtStartGame = new CountDownTimer(START_TIMER, 1000) {
            @Override
            public void onTick(long l) {
                progressBar.setProgress(((int) (l / 1000))-1);
                txtStartGameTimer.setText(String.valueOf(progressBar.getProgress()));
                if (progressBar.getProgress() == 0){
                    this.cancel();
                    startGame();
                }
            }

            @Override
            public void onFinish() {
                startGame();
            }
        };

        cdtStartGame.start();


        countDownTimer = new CountDownTimer(TIME, 1000) {

            public void onTick(long millisUntilFinished) {
                txtCountDown.setText(hmsTimeFormatter(millisUntilFinished));
            }

            public void onFinish() {
                txtCountDown.setText(hmsTimeFormatter(0));
                showQuestion();

            }
        };


        relativeLayoutCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdtStartGame.cancel();
                startGame();
            }
        });
    }

    private void startGame() {
        startGameTime = SystemClock.elapsedRealtime();
        relativeLayoutCounter.setVisibility(View.GONE);
        countDownTimer.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAnswerA: answer(view);  break;
            case R.id.btnAnswerB: answer(view);  break;
            case R.id.btnAnswerC: answer(view);  break;
            case R.id.btnAnswerD: answer(view);  break;
        }
    }

    private int score = 0;
    private void answer(View view) {
        if (startGame ) {
            if (view.getTag().toString().equals(image.getTag().toString())) {
                score += 10;
                txtScore.setText(String.valueOf(score));
            }
            showQuestion();
        }
    }

    private void showQuestion(){
        startGame = (questionIntex < listofQuestions.size());
        if (!startGame) {
            gameOver();
        }

        Log.d(TAG, "questionIntex: " + questionIntex + " listofQuestions.size(): " + listofQuestions.size() + " startGame: " + startGame);
        if (listofQuestions != null && listofQuestions.size() > 0 && questionIntex < listofQuestions.size()) {
            Question question = listofQuestions.get(questionIntex);

            image.setBackgroundResource(question.getImageId());

            image.setTag(question.getCorrectAnswer());

            answerAButton.setTag(question.getAnswerA());
            answerBButton.setTag(question.getAnswerB());
            answerCButton.setTag(question.getAnswerC());
            answerDButton.setTag(question.getAnswerD());

            answerAButton.setText(question.getAnswerA());
            answerBButton.setText(question.getAnswerB());
            answerCButton.setText(question.getAnswerC());
            answerDButton.setText(question.getAnswerD());


            if (questionIntex < listofQuestions.size()) {
                questionIntex++;
                countDownTimer.start();
            }
            else {
                countDownTimer.cancel();
            }
            txtQuestion.setText(String.valueOf(questionIntex) + "/" + LIMIT);
        }


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayingActivity.this);
        builder.setTitle(R.string.exit_game_title);
        builder.setMessage(R.string.exit_game);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

    private String hmsTimeFormatter(long milliSeconds) {

//        %02d:%02d:%02d",
        String hms = String.format("%02d:%02d",
//                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }


    private void gameOver() {
        gameDuration = SystemClock.elapsedRealtime() - startGameTime;
        gameResult.setLevel(Config.GAME_LEVEL.getValue());
        gameResult.setScore(score);
        gameResult.setTime(gameDuration / 1000);
        gameResult.setQuestionCount(questionIntex);

        Log.d(TAG, "gameOver: " + gameResult.toString());

//        AlertDialog.Builder builder = new AlertDialog.Builder(PlayingActivity.this);
//        builder.setTitle(R.string.exit_game_title);
//        builder.setMessage("Duration: " + (gameDuration / 1000));
//        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//            }
//        });
//        builder.setNegativeButton(R.string.no, null);
//        builder.show();
    }

}
