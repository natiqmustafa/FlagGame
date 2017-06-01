package com.natodroid.flaggamenew.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.natodroid.flaggamenew.common.PrefManager;
import com.natodroid.flaggamenew.config.Config;
import com.natodroid.flaggamenew.helper.DbHelper;
import com.natodroid.flaggamenew.model.Country;
import com.natodroid.flaggamenew.model.GameLavel;
import com.natodroid.flaggamenew.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by natiqmustafa on 02.03.2017.
 */

public class QuestionData {
    private static final String TAG = "QuestionData";
    private static int LIMIT = 30;
    private Context context;
    private DbHelper dbHelper;
    private List<Question> questionList;

    public QuestionData(Context context) {
        this.context = context;
        connectDb();
        init();
    }

    private void init() {
        questionList = new ArrayList<>();
        LIMIT = getLimit(Config.GAME_LEVEL);
    }

    public int getLimit(GameLavel gameLevel) {
        if (gameLevel == GameLavel.EASY)
            return Config.EASY_MODE_NUM;
        else if (gameLevel == GameLavel.MEDIUM)
            return Config.MEDIUM_MODE_NUM;
        else if (gameLevel == GameLavel.HARD)
            return Config.HARD_MODE_NUM;
        else if (gameLevel == GameLavel.HARDEST)
            return Config.HARDEST_MODE_NUM;

        return Config.EASY_MODE_NUM;
    }


    public long getTime(GameLavel gameLevel) {
        if (gameLevel == GameLavel.EASY)
            return Config.EASY_MODE_TIME;
        else if (gameLevel == GameLavel.MEDIUM)
            return Config.MEDIUM_MODE_TIME;
        else if (gameLevel == GameLavel.HARD)
            return Config.HARD_MODE_TIME;
        else if (gameLevel == GameLavel.HARDEST)
            return Config.HARDEST_MODE_TIME;

        return Config.EASY_MODE_TIME;
    }


    private void connectDb() {
        dbHelper = new DbHelper(this.context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            Log.e(TAG, "connectDb: " + e.getMessage());
        }
    }

    public void generateQuiz(){
        List<Country> countryList = new CountryData(this.context).getCountryList(LIMIT);
        for (Country country : countryList) {
            Question question = this.createQuestion(country);
            questionList.add(question);
//            Log.d(TAG, "generateQuiz: " + question.toString());
        }
    }


    public List<Question> getQuestionList() {
        return questionList;
    }



    private Question createQuestion(Country country) {
        int limit = 4;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;
        try {

            String sql = String.format(Locale.getDefault(), "select flag_id, " + Config.ANSWER_COLUMN +
                    "  from flags where flag_id <> %d ORDER BY Random() LIMIT %d ", country.getId(), limit);

            c = db.rawQuery(sql, null);
            if (c == null)
                return null;
            c.moveToFirst();

            List<QuestionModel> countryModelList = new ArrayList<>();
            int flagId = country.getId();
            String text = (Config.ANSWER_COLUMN.equals("short_name") ? country.getShorName() : country.getLongName());
            countryModelList.add(new QuestionModel(flagId, text));

            do {
                flagId = c.getInt(0);
                text = c.getString(1);
                countryModelList.add(new QuestionModel(flagId,  text));
            } while (c.moveToNext());



            Question question = new Question();
            question.setId(country.getId()); // questionId dogru cavabin id olsun

            int imageId=this.context.getResources().getIdentifier(country.getImgName().toLowerCase(),"drawable",this.context.getPackageName());
            question.setImageId(imageId);

            text = (Config.ANSWER_COLUMN.equals("short_name") ? country.getShorName() : country.getLongName());
            question.setCorrectAnswer(text);


            Collections.shuffle(countryModelList);
            for (int i = 0; i < countryModelList.size(); i++){
                switch (i){
                    case 1: question.setAnswerA(countryModelList.get(i).getText()); break;
                    case 2: question.setAnswerB(countryModelList.get(i).getText()); break;
                    case 3: question.setAnswerC(countryModelList.get(i).getText()); break;
                    case 4: question.setAnswerD(countryModelList.get(i).getText()); break;
                }
            }

            return question;

        }catch (Exception e){
            Log.e(TAG, "getCountryList: " + e.getMessage());
            return null;
        }
        finally {
            if (c != null)
                c.close();
        }
    }


    class QuestionModel{
        private int id;
        private String text;

        public QuestionModel(int id, String text) {
            this.id = id;
            this.text = text;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }


}
