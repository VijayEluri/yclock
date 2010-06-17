package net.assemble.yclock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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

        setContentView(R.layout.about);

        try {
            PackageInfo pi = getPackageManager().getPackageInfo("net.assemble.yclock", 0);
            setTitle(getResources().getString(R.string.app_name) + " ver." + pi.versionName);
        } catch (NameNotFoundException e) {}
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.icon);

        // テキスト
        AssetManager as = getResources().getAssets();
        try {
            InputStream fin;
            try {
                fin = as.open("about_" + Locale.getDefault().getLanguage() + ".txt");
            } catch (IOException e) {
                fin = as.open("about.txt");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(fin));
            StringBuffer str = new StringBuffer();
            String s;
            while ((s = in.readLine()) != null) {
                str.append(s + "\n");
            }
            in.close();

            TextView text = (TextView) findViewById(R.id.text);
            text.setText(str.toString());
        } catch (IOException e) {}

        // OK
        Button btn_ok = (Button) findViewById(R.id.ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
            
        });

        // Send report 
        Button btn_report = (Button) findViewById(R.id.report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:yh1224@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                startActivity(intent);
            }
        });
    }
}
