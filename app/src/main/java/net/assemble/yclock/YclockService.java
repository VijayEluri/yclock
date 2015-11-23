package net.assemble.yclock;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * サービス
 */
public class YclockService extends Service {
    private static ComponentName mService;
    private YclockVoice mVoice;

    @Override
    public void onCreate() {
        super.onCreate();
        mVoice = new YclockVoice(this);
        mVoice.setAlarm();
    }

    // This is the old onStart method that will be called on the pre-2.0
    // platform.  On 2.0 or later we override onStartCommand() so this
    // method will not be called.
    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        handleCommand(intent);
    }

    @TargetApi(5)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleCommand(intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    private void handleCommand(@SuppressWarnings("UnusedParameters") Intent intent) {
        mVoice.setAlarm();
    }

    public void onDestroy() {
        mVoice.resetAlarm();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    /**
     * サービス動作有無取得
     */
    public static boolean isActive() {
        return mService != null;
    }

    /**
     * サービス開始
     */
    public static boolean startService(Context ctx) {
        boolean result;
        boolean restart = isActive();
        mService = ctx.startService(new Intent(ctx, YclockService.class));
        if (mService == null) {
            Log.e(Yclock.TAG, "YclockService could not start!");
            result = false;
        } else {
            Log.d(Yclock.TAG, "YclockService started: " + mService);
            result = true;
        }
        if (!restart && result) {
            Toast.makeText(ctx, R.string.service_started, Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /**
     * サービス停止
     */
    public static void stopService(Context ctx) {
        if (mService != null) {
            Intent i = new Intent();
            i.setComponent(mService);
            boolean res = ctx.stopService(i);
            if (!res) {
                Log.e(Yclock.TAG, "YclockService could not stop!");
            } else {
                Log.d(Yclock.TAG, "YclockService stopped: " + mService);
                Toast.makeText(ctx, R.string.service_stopped, Toast.LENGTH_SHORT).show();
                mService = null;
            }
        }
    }
}
