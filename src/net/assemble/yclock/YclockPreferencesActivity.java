package net.assemble.yclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import net.assemble.yclock.R;

/**
 * 設定画面
 */
public class YclockPreferencesActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private Preference mAboutPref;
    private ListPreference mPeriodPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        mPeriodPref = (ListPreference)findPreference(YclockPreferences.PREF_KEY_PERIOD);
        mAboutPref = (Preference)findPreference(YclockPreferences.PREF_KEY_ABOUT);

        updateSummary();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        if (preference == mPeriodPref) {
        } else if (preference == mAboutPref) {
            //Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent().setClass(this, YclockAboutActivity.class);
            startActivity(intent);

//          AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//          alertDialogBuilder.setTitle(R.string.about_title);
//          alertDialogBuilder.setMessage(R.string.about_text);
//          alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int which) {
//              }});
//          alertDialogBuilder.setCancelable(true);
//          AlertDialog alertDialog = alertDialogBuilder.create();
//          alertDialog.show();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        updateService();
    }

    private void updateSummary() {
        String val = mPeriodPref.getValue();
        String[] entries = getResources().getStringArray(R.array.entries_period);
        String[] entryvalues = getResources().getStringArray(R.array.entryvalues_period);
        for (int i = 0; i < entries.length; i++) {
            if (val.equals(entryvalues[i])) {
                mPeriodPref.setSummary(getResources().getString(R.string.pref_period_summary) +
                    ": " + entries[i]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSummary();
    }

    /**
     * 設定に応じて処理をおこなう
     */
    private void updateService() {
        if (YclockPreferences.getEnabled(this)) {
            YclockService.startService(this);
        } else {
            YclockService.stopService(this);
        }
    }

}
