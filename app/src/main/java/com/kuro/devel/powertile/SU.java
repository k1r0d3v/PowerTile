package com.kuro.devel.powertile;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SU
{
    public static final String LOG_ID = "PowerTile:SU";

    public static void exec(String[] cmd)
        throws Exception
    {
        Process su = null;
        try {
            su = Runtime.getRuntime().exec("su");

            StringBuilder r = new StringBuilder();
            for (String i : cmd)
                r.append(i).append(' ');
            r.append('\n');

            su.getOutputStream().write(r.toString().getBytes());
            su.getOutputStream().flush();

            su.getOutputStream().write("exit\n".getBytes());
            su.getOutputStream().flush();

            su.waitFor();
        } finally {
            if (su != null)
                su.destroy();
        }
    }

    public static boolean tryExec(String[] cmd, Context context) {
        try {
            SU.exec(cmd);
            return true;
        } catch (Exception e) {
            Log.e(LOG_ID, "", e.getCause());
            Toast.makeText(context, context.getString(R.string.have_you_root), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
