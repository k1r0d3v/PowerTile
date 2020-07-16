package com.kuro.devel.powertile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class PowerPopup extends Activity implements View.OnClickListener
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.gravity = Gravity.TOP | Gravity.RIGHT;

        getWindow().setAttributes(p);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.power_popup_background));

        setContentView(R.layout.power_popup);
        View info = findViewById(R.id.lock);
        info.setOnClickListener(this);

        info = findViewById(R.id.power_off);
        info.setOnClickListener(this);

        info = findViewById(R.id.restart);
        info.setOnClickListener(this);

        info = findViewById(R.id.restart_recovery);
        info.setOnClickListener(this);

        info = findViewById(R.id.goto_app_info);
        info.setOnClickListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        // Bug in activity dialog finish?
        overridePendingTransition(0, R.anim.exit_to_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lock:
                SU.tryExec(new String[]{
                        "input", "keyevent", "26"
                }, this);
                break;
            case R.id.power_off:
                SU.tryExec(new String[]{
                        "svc", "power", "shutdown"
                }, this);
                break;
            case R.id.restart:
                SU.tryExec(new String[]{
                        "svc", "power", "reboot"
                }, this);
                break;
            case R.id.restart_recovery:
                SU.tryExec(new String[]{
                        "svc", "power", "reboot", "recovery"
                }, this);
                break;
            case R.id.goto_app_info:
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.setData(Uri.fromParts("package", getPackageName(), null));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
                break;
        }
        finish();
    }
}
