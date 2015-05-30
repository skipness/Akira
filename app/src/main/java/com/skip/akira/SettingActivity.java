package com.skip.akira;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{

    private Toolbar toolbar;
    boolean wifi;
    private CheckBoxPreference overWifi;

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.pref_toolbar, root, false);
        root.addView(toolbar, 0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        overWifi = (CheckBoxPreference)findPreference("KEY_OVERWIFI");
        wifi = overWifi.getSharedPreferences().getBoolean("KEY_OVERWIFI",false);
        if(wifi == true){
            overWifi.setSummary("True");
        }else{
            overWifi.setSummary("False");
        }
        overWifi.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        wifi = (Boolean)newValue;
        switch(preference.getKey()){
            case "KEY_OVERWIFI":
                if(wifi) {
                    preference.setSummary("True");
                }else{
                    preference.setSummary("False");
                }
                break;
        };
        return true;
    }
}
