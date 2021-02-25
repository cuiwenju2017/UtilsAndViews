package com.example.utilsandviews.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.utilsandviews.views.BatteryHorizontalView;
import com.example.utilsandviews.views.BatteryVerticalView;

/**
 * 获取系统电量广播
 */
public class BatteryReceiver extends BroadcastReceiver {

    private BatteryHorizontalView pow;
    private BatteryVerticalView pow2;
    private TextView tvpow;

    public BatteryReceiver(BatteryHorizontalView pow, BatteryVerticalView pow2, TextView tvpow) {
        this.pow = pow;
        this.pow2 = pow2;
        this.tvpow = tvpow;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int current = intent.getExtras().getInt("level");// 获得当前电量
        int total = intent.getExtras().getInt("scale");// 获得总电量
        int percent = current * 100 / total;
        pow.setPower(percent);
        pow2.setPower(percent);
        tvpow.setText(percent + "%");
    }
}