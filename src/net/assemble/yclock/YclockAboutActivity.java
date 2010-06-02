package net.assemble.yclock;

import android.app.Activity;
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

        setTitle(getResources().getString(R.string.app_name) + " "
        		+ getResources().getString(R.string.app_version));
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.icon);
    }
}
