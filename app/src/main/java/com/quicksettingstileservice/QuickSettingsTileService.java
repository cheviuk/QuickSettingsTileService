package com.quicksettingstileservice;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class QuickSettingsTileService extends TileService {

    public static final String TAG = QuickSettingsTileService.class.getSimpleName();
    private Icon activeIcon = null;
    private Icon disabledIcon = null;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + intent);
        if (activeIcon == null) {
            activeIcon = Icon.createWithResource(getApplicationContext(), R.drawable.on);
        }

        if (disabledIcon == null) {
            disabledIcon = Icon.createWithResource(getApplicationContext(), R.drawable.off);
        }
        return super.onBind(intent);
    }

    @Override
    public void onTileAdded() {
        Log.d(TAG, "onTileAdded");
    }

    @Override
    public void onStartListening() {
        Tile tile = getQsTile();
        Log.d(TAG, "onStartListening: " + tile.getLabel());
    }

    @Override
    public void onClick() {
        Tile tile = getQsTile();
        Icon icon;
        String log = "onClick: state changed form " + tile.getState();

        if (tile.getState() == Tile.STATE_ACTIVE) {
            tile.setState(Tile.STATE_INACTIVE);
            icon = disabledIcon;
        } else {
            tile.setState(Tile.STATE_ACTIVE);
            icon = activeIcon;
        }

        tile.setIcon(icon);
        tile.updateTile();
        Log.d(TAG, log + " to " + tile.getState());
    }
}

