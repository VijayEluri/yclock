package net.assemble.yclock;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 設定管理
 */
public class YclockPreferences
{
    public static final String PREF_ENABLE_KEY = "Enabled";
    public static final boolean PREF_ENABLE_DEFAULT = true;

    public static final String PREF_PERIOD_KEY = "Period";

    public static final String PREF_VIBRATE_KEY = "Vibrate";
    public static final boolean PREF_VIBRATE_DEFAULT = false;

    public static final String PREF_PERIOD_DEFAULT = "0";
    public static final String PREF_PERIOD_EACHHOUR = "0";
    public static final String PREF_PERIOD_EACH30MIN = "1";

    public static final String PREF_HOURS_KEY = "Hours";
    public static final int PREF_HOURS_DEFAULT = 0x00ffffff;

    public static final String PREF_USERINGVOLUME_KEY = "UseRingVolume";
    public static final boolean PREF_USERINGVOLUME_DEFAULT = false;

    public static final String PREF_VOLUME_KEY = "Volume";
    public static final int PREF_VOLUME_DEFAULT = 4;

    public static final String PREF_MEDIAVOL_KEY = "MediaVol";
    public static final boolean PREF_MEDIAVOL_DEFAULT = false;

    public static final String PREF_NOTIFICATIONICON_KEY = "NotificationIcon";
    public static final boolean PREF_NOTIFICATIONICON_DEFAULT = false;

    public static final String PREF_TEST_KEY = "Test";

    SharedPreferences mPref;

    public static boolean getEnabled(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                YclockPreferences.PREF_ENABLE_KEY,
                YclockPreferences.PREF_ENABLE_DEFAULT);
    }

    public static void setEnable(Context ctx, boolean val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        e.putBoolean(YclockPreferences.PREF_ENABLE_KEY, val);
        e.commit();
    }

    public static boolean getVibrate(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                YclockPreferences.PREF_VIBRATE_KEY,
                YclockPreferences.PREF_VIBRATE_DEFAULT);
    }

    public static String getPeriod(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(
                YclockPreferences.PREF_PERIOD_KEY,
                YclockPreferences.PREF_PERIOD_DEFAULT);
    }

    public static int getHours(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(
                YclockPreferences.PREF_HOURS_KEY,
                YclockPreferences.PREF_HOURS_DEFAULT);
    }

    public static boolean issetHour(Context ctx, Calendar cal) {
        Hours hours = new Hours(getHours(ctx));
        return hours.isSet(cal.get(Calendar.HOUR_OF_DAY));
    }

    public static boolean getUseRingVolume(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                YclockPreferences.PREF_USERINGVOLUME_KEY,
                YclockPreferences.PREF_USERINGVOLUME_DEFAULT);
    }

    public static int getVolume(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(
                YclockPreferences.PREF_VOLUME_KEY,
                YclockPreferences.PREF_VOLUME_DEFAULT);
    }

    public static boolean getMediaVol(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                YclockPreferences.PREF_MEDIAVOL_KEY,
                YclockPreferences.PREF_MEDIAVOL_DEFAULT);
    }

    public static boolean getNotificationIcon(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(
                YclockPreferences.PREF_NOTIFICATIONICON_KEY,
                YclockPreferences.PREF_NOTIFICATIONICON_DEFAULT);
    }

    /*
     * hours code as a single integer.
     */
    static final class Hours {

        // Bit mask of all hours
        private int mHours;

        Hours(int hours) {
            mHours = hours;
        }

        public boolean isSet(int hour) {
            return ((mHours & (1 << hour)) > 0);
        }

        public void set(int hour, boolean set) {
            if (set) {
                mHours |= (1 << hour);
            } else {
                mHours &= ~(1 << hour);
            }
        }

        public void set(Hours hours) {
            mHours = hours.mHours;
        }

        public int getCoded() {
            return mHours;
        }

        // Returns hours encoded in an array of booleans.
        public boolean[] getBooleanArray() {
            boolean[] ret = new boolean[24];
            for (int i = 0; i < 24; i++) {
                ret[i] = isSet(i);
            }
            return ret;
        }
    }
}
