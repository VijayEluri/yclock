package net.assemble.yclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import net.assemble.yclock.R;

/**
 * メイン
 */
public class YclockActivity extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // *** convert from deprecated preference
        if (YclockPreferences.getMediaVol(this)) {
            AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
            e.putBoolean(YclockPreferences.PREF_KEY_USERINGVOLUME, false);
            e.putInt(YclockPreferences.PREF_KEY_VOLUME, audio.getStreamVolume(AudioManager.STREAM_MUSIC));
            e.commit();
        }

        // preferences button
        Button btn = (Button) findViewById(R.id.Button_prefs);
        btn.setOnClickListener(this);

        // Test play button
        Button btn_play = (Button) findViewById(R.id.Button_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new YclockVoice(getApplicationContext()).playTest();
            }
        });

        updateService();
    }

    /**
     * オプションメニューの生成
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * オプションメニューの選択
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        case R.id.menu_config:
            Intent intent = new Intent().setClass(this, YclockPreferencesActivity.class);
            startActivity(intent);
            break;
        }
        return true;
    };

    public void onClick(View v) {
        Intent intent = new Intent().setClass(this, YclockPreferencesActivity.class);
        startActivity(intent);
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
