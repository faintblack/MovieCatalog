package com.system.perfect.tugas2.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.system.perfect.tugas2.R;

public class SharedPreference {

    SharedPreferences preferences;
    Context context;

    public SharedPreference(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstRun(Boolean first){
        SharedPreferences.Editor edit = preferences.edit();
        String pref = context.getResources().getString(R.string.pref_first_run_app);
        edit.putBoolean(pref, first);
        edit.apply();
    }

    public Boolean getFirstRun(){
        String check = context.getResources().getString(R.string.pref_first_run_app);
        return preferences.getBoolean(check, true);
    }

}
