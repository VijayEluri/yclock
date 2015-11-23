package net.assemble.yclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

import net.assemble.android.AboutActivity;
import net.assemble.taeclock.R;

/**
 * メイン
 */
public class YclockActivity extends Activity implements OnClickListener
{
    private ToggleButton mEnableButton;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        YclockPreferences.upgrade(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mEnableButton = (ToggleButton) findViewById(R.id.enable);
        mEnableButton.setOnClickListener(this);

        updateService();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        if (audio.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
//            Toast.makeText(this, R.string.media_volume_zero, Toast.LENGTH_LONG).show();
//        }

        updateView();
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
        case R.id.menu_about:
            intent = new Intent().setClass(this, AboutActivity.class);
            startActivity(intent);
            break;
        }
        return true;
    };

    public void onClick(View v) {
        YclockPreferences.setEnable(this, mEnableButton.isChecked());
        updateView();
        updateService();
    }

    private void updateView() {
        mEnableButton.setChecked(YclockPreferences.getEnabled(this));
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
