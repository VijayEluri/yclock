package net.assemble.yclock;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;

import net.assemble.yclock.R;

/**
 *
 */
public class YclockVolumePreference extends DialogPreference {
    private CheckBox mCheckBox;
    private SeekBar mSeekBar;

    private int mMaxVolume;
    private int mVolume;
    private boolean mUseRingVolume;

    public YclockVolumePreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.volume_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        // 設定可能な最大値を取得
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume =am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // 現在の設定値を取得
        mUseRingVolume = YclockPreferences.getUseRingVolume(context);
        mVolume = YclockPreferences.getVolume(context);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mSeekBar.setProgress(mVolume);
        mSeekBar.setMax(mMaxVolume);

        mCheckBox = (CheckBox) view.findViewById(R.id.useringvolume);
        if (mUseRingVolume != false) {
            mCheckBox.setChecked(true);
            mSeekBar.setEnabled(false);
        }

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    mSeekBar.setEnabled(false);
                } else {
                    mSeekBar.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            mUseRingVolume = mCheckBox.isChecked();
            mVolume = mSeekBar.getProgress();
            //Log.d("debug", "use ring volume = " + mUseRingVolume);
            //Log.d("debug", "new volume = " + mVolume);

            Editor e = getSharedPreferences().edit();
            e.putBoolean(YclockPreferences.PREF_USERINGVOLUME_KEY, mUseRingVolume);
            e.putInt(YclockPreferences.PREF_VOLUME_KEY, mVolume);
            // remove deprecated preference
            e.remove(YclockPreferences.PREF_MEDIAVOL_KEY);
            e.commit();
        }
    }

    protected static SeekBar getSeekBar(View dialogView) {
        return (SeekBar) dialogView.findViewById(R.id.seekbar);
    }
}
