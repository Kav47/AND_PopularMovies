package com.dax47.kav.popularmovies;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * A simple {@link PreferenceFragmentCompat} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
    //added this fragment to the existing SettingsActivity to display preferences, rather than showing a separate fragment screen.

}
