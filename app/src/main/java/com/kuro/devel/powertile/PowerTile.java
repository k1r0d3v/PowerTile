package com.kuro.devel.powertile;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.N)
public class PowerTile extends TileService
{
    @Override
    public void onTileAdded() {
        super.onTileAdded();
        getQsTile().setState(Tile.STATE_ACTIVE);
        getQsTile().updateTile();
    }

    @Override
    public void onStartListening() {
        getQsTile().setState(Tile.STATE_ACTIVE);
        getQsTile().updateTile();
    }

    @Override
    public void onClick()
    {
        getQsTile().setState(Tile.STATE_ACTIVE);
        getQsTile().updateTile();

        SU.tryExec(new String[]{
                "input", "keyevent", "26"
        }, this);
    }
}
