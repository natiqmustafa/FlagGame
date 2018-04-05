package com.natodroid.flaggamenew.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.natodroid.flaggamenew.config.Config;
import com.natodroid.flaggamenew.helper.DbHelper;
import com.natodroid.flaggamenew.model.Country;
import com.natodroid.flaggamenew.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by natiqmustafa on 01.03.2017.
 */

public class CountryData {
    private static final String TAG = "CountryData";
    private Context context;

    private DbHelper dbHelper;

    public CountryData(Context context){
        this.context = context;
        connectDb();
    }

    private void connectDb() {
        // comment
        dbHelper = new DbHelper(this.context);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            Log.e(TAG, "connectDb: " + e.getMessage());
        }
    }



    public List<Country> getCountryList(int limit) {
        List<Country> countryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;
        try {

            String sql = String.format(Locale.getDefault(), "select flag_id, img_name, short_name, long_name, capital, " +
                    " phone_code, tdl, region from flags ORDER BY Random() LIMIT %d ", limit);

            c = db.rawQuery(sql, null);
            if (c == null)
                return null;
            c.moveToFirst();

            do {
                Country item = new Country();
                item.setId(c.getInt(0));
                item.setImgName(c.getString(1));
                item.setShorName(c.getString(2));
                item.setLongName(c.getString(3));
                item.setCapital(c.getString(4));
                item.setPhoneCode(c.getString(5));
                item.setTdl(c.getString(6));
                item.setRegion(c.getString(7));
                countryList.add(item);
            } while (c.moveToNext());

            Collections.shuffle(countryList);
            return countryList;

        }catch (Exception e){
            Log.e(TAG, "getCountryList: " + e.getMessage());
            return null;
        }
        finally {
            if (c != null)
                c.close();
        }
    }
}
