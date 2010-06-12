package net.assemble.yclock;

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
    private static final String TAG = "YclockService";

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
     * サービス動作有無取得
     */
    public static boolean isActive() {
        if (mService != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * サービス開始
     */
    public static boolean startService(Context ctx) {
        boolean result;
        boolean restart = YclockService.isActive();
        mService = ctx.startService(new Intent(ctx, YclockService.class));
        if (mService == null) {
            Log.e(TAG, "YclockService could not start!");
            result = false;
        } else {
            Log.d(TAG, "YclockService started: " + mService);
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
            if (res == false) {
                Log.e(TAG, "YclockService could not stop!");
            } else {
                Log.d(TAG, "YclockService stopped: " + mService);
                Toast.makeText(ctx, R.string.service_stopped, Toast.LENGTH_SHORT).show();
                mService = null;
            }
        }
    }
}
