package com.example.android.notepad;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;


public class NoteSettings  extends AppCompatActivity{
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.xml.root_preferences);
    if (savedInstanceState == null) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_settings, new SettingsFragment())
                .commit();
    }
}

public static class SettingsFragment extends PreferenceFragmentCompat implements com.example.android.notepad.SettingsFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        // 找到偏好设置项
        SwitchPreferenceCompat nightMode = findPreference("night_mode");
        SwitchPreferenceCompat notifications = findPreference("notifications");

        // 设置监听器
        if (nightMode != null) {
            nightMode.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
        }
        if (notifications != null) {
            ((Preference) notifications).setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        boolean isOn = (Boolean) newValue;

        switch (key) {
            case "night_mode":
                // 处理夜间模式切换
                if (isOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;

            case "notifications":
                // 处理通知开关
                if (isOn) {
                    enableNotifications();
                } else {
                    disableNotifications();
                }
                return true;
        }
        return false;
    }

    // 启用通知
    private void enableNotifications() {
        // 保存设置
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        prefs.edit().putBoolean("notifications_enabled", true).apply();

        // 这里可以添加启用通知的具体实现
        Toast.makeText(getContext(), "通知已启用", Toast.LENGTH_SHORT).show();
    }

    // 禁用通知
    private void disableNotifications() {
        // 保存设置
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        prefs.edit().putBoolean("notifications_enabled", false).apply();

        // 这里可以添加禁用通知的具体实现
        Toast.makeText(getContext(), "通知已禁用", Toast.LENGTH_SHORT).show();
    }
}
}
