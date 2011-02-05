package net.assemble.yclock;

import java.util.Calendar;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.telephony.TelephonyManager;
import android.util.Log;

import net.assemble.yclock.YclockVoice;

/**
 * Receiver for Alarm Intent
 */
public class YclockAlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context ctx, Intent intent) {
        Log.d(Yclock.TAG, "received intent: " + intent.getAction());

        if (Calendar.getInstance().get(Calendar.MINUTE) % 30 == 0) {
            TelephonyManager tel = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            if (tel.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                // 通話中は抑止
                return;
            }
            new YclockVoice(ctx).play();
        }
    }

}
