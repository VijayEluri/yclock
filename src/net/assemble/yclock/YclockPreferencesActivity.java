package net.assemble.yclock;

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
    private ListPreference mPeriodPref;
    private Preference mTestPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        mPeriodPref = (ListPreference)findPreference(YclockPreferences.PREF_KEY_PERIOD);
        mTestPref = (Preference)findPreference(YclockPreferences.PREF_KEY_TEST);

        updateSummary();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        if (preference == mTestPref) {
            new YclockVoice(getApplicationContext()).playTest();
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
