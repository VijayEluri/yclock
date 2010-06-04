package net.assemble.yclock;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * サービス ※未使用
 */
public class YclockService extends Service {
    private static ComponentName mService;
    private YclockVoice mVoice;

    @Override
    public void onCreate() {
        super.onCreate();
        mVoice = new YclockVoice(this);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
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
     * サービス開始
     */
    public static void startService(Context ctx) {
        mService = ctx.startService(new Intent(ctx, YclockService.class));
        if (mService == null) {
            Log.e(ctx.getClass().getName(), "YclockService could not start!");
        } else {
            Log.d(ctx.getClass().getName(), "YclockService started: " + mService);
        }
    }

    /**
     * サービス停止
     */
    public static void stopService(Context ctx) {
        if (mService != null) {
            Intent i = new Intent();
            i.setComponent(mService);
            boolean res = ctx.stopService(i);
            if (res == false) {
                Log.e(ctx.getClass().getName(), "YclockService could not stop!");
            } else {
                Log.d(ctx.getClass().getName(), "YclockService stopped: " + mService);
                mService = null;
            }
        }
    }
}
