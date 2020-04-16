package dae.mob123.ui.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import dae.mob123.R;

//Author: DG
public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

    }
}
