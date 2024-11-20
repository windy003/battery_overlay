package com.example.batteryoverlay;

import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.content.Intent;
import android.util.Log;

public class BatteryOverlayTileService extends TileService {
    private static final String TAG = "BatteryOverlayTile";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "TileService Created");
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Log.d(TAG, "Tile Added");
        updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        Log.d(TAG, "Start Listening");
        updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        Log.d(TAG, "Tile Clicked");
        if (isServiceRunning()) {
            stopService(new Intent(this, BatteryOverlayService.class));
            Log.d(TAG, "Stopping Service");
        } else {
            startService(new Intent(this, BatteryOverlayService.class));
            Log.d(TAG, "Starting Service");
        }
        updateTile();
    }

    private void updateTile() {
        Tile tile = getQsTile();
        if (tile != null) {
            boolean running = isServiceRunning();
            Log.d(TAG, "Updating Tile, Service Running: " + running);
            
            // 设置图标
            tile.setIcon(Icon.createWithResource(this, 
                running ? R.drawable.ic_battery_overlay_on : R.drawable.ic_battery_overlay_off));
            
            // 设置状态
            tile.setState(running ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
            
            // 设置标签
            tile.setLabel(getString(R.string.tile_label));
            
            tile.updateTile();
        } else {
            Log.e(TAG, "Tile is null");
        }
    }

    private boolean isServiceRunning() {
        return BatteryOverlayService.isRunning;
    }
} 