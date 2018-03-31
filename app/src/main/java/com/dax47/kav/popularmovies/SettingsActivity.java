package com.dax47.kav.popularmovies;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public static final String SWITCH_KEY ="switch_preference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean  switchPref = sharedPref.getBoolean(SettingsActivity.SWITCH_KEY, false);
        //Toast.makeText(this, switchPref.toString(), Toast.LENGTH_SHORT).show();
    }
}
