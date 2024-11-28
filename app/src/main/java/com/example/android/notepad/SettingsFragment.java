package com.example.android.notepad;

import androidx.preference.Preference;

public interface SettingsFragment {
    boolean onPreferenceChange(Preference preference, Object newValue);
}
