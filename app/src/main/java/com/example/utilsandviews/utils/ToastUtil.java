package com.example.utilsandviews.utils;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utilsandviews.App;
import com.example.utilsandviews.R;

/**
 * Toast弹窗工具类
 */
public class ToastUtil {

    private static Toast mToast;
    private static TextView mTvMessage;

    /**
     * miui部分版本会自带包名 用此方法解决该Bug
     */
    private static void createToast(Context context, CharSequence message, int duration) {
        mToast = new Toast(context);

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.transient_notification, null);
        mTvMessage = (TextView) v.findViewById(R.id.message);
        mTvMessage.setText(message);

        mToast.setView(v);
        mToast.setDuration(duration);
    }

    public static void show(CharSequence message, int duration) {
        Context context = App.getInstance().getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0对Toast做了改动
            createToast(context, message, duration);
        } else {
            if (mToast == null) {
                createToast(context, message, duration);
            } else if (mTvMessage != null) {
                mTvMessage.setText(message);
            } else {
                mToast = null;
                show(message, duration);
            }
        }

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * short Toast
     *
     * @param msg
     */
    public static void s(String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * long Toast
     *
     * @param msg
     */
    public static void l(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }
}
