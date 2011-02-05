package net.assemble.yclock;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.net.Uri;
import android.util.Log;

/**
 * Receiver for Broadcast Intent
 *
 * 装置起動時や時刻変更時のタイマ再設定など
 */
public class YclockReceiver extends BroadcastReceiver
{
    private static final String TAG = "Yclock";

    @Override
    public void onReceive(Context ctx, Intent intent) {
        Log.d(TAG, "received intent: " + intent.getAction());

        if (YclockPreferences.getEnabled(ctx) == false) {
            return;
        }

        if (intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                // Restart service
                Log.i(TAG, "Yclock restarted. (at boot)");
                YclockService.startService(ctx);
            } else if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED"/*Intent.ACTION_PACKAGE_REPLACED*/)) {
                if (intent.getData() != null &&
                    intent.getData().equals(Uri.fromParts("package", ctx.getPackageName(), null))) {
                    // Restart service
                    Log.i(TAG, "Yclock restarted. (package replaced)");
                    YclockService.startService(ctx);
                }
            } else if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED)
                    || intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                // Restart alarm
                YclockService.startService(ctx);
            }
            return;
        }
    }

}
