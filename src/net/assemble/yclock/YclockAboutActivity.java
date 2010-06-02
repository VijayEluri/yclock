package net.assemble.yclock;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Window;

import net.assemble.yclock.R;

/**
 * 「柚子時計について」画面
 */
public class YclockAboutActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_LEFT_ICON);

        setContentView(R.layout.about_activity);

        try {
            PackageInfo pi = getPackageManager().getPackageInfo("net.assemble.yclock", 0);
            setTitle(getResources().getString(R.string.app_name) + " ver." + pi.versionName);
        } catch (NameNotFoundException e) {}
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.icon);
    }
}
