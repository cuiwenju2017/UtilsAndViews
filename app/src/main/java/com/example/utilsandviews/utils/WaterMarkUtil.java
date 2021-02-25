package com.example.utilsandviews.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.utilsandviews.R;

/**
 * 水印工具类
 */
public class WaterMarkUtil {

    public static final WaterMarkUtil INSTANCE;

    public static void showWatermarkView(Activity activity, String string) {
        ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        View framView = LayoutInflater.from(activity).inflate(R.layout.layout_watermark, null);
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        int height = dm.heightPixels;// 屏幕高度（像素）
        ((ImageView) framView.findViewById(R.id.ivWm)).setImageDrawable(getMarkTextBitmapDrawable(activity, string, width, height));
        //可对水印布局进行初始化操作
        rootView.addView(framView);
    }

    private static Drawable getMarkTextBitmapDrawable(Context gContext, String gText, int width, int height) {
        Bitmap bitmap = getMarkTextBitmap(gContext, gText, width, height);
        if (bitmap != null) {
            BitmapDrawable drawable = new BitmapDrawable(gContext.getResources(), bitmap);
            drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            drawable.setDither(true);
            return drawable;
        } else {
            return null;
        }
    }

    private static Bitmap getMarkTextBitmap(Context gContext, String gText, int width, int height) {
        Resources resources = gContext.getResources();
        float textSize = TypedValue.applyDimension(2, 18.0F, resources.getDisplayMetrics());
        resources = gContext.getResources();
        float inter = TypedValue.applyDimension(1, 25.0F, resources.getDisplayMetrics());
        int sideLength = width > height ? (int) Math.sqrt((2 * width * width)) : (int) Math.sqrt((2 * height * height));
        Paint paint = new Paint(1);
        Rect rect = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(gText, 0, gText.length(), rect);
        int strwid = rect.width();
        int strhei = rect.height();
        Bitmap markBitmap = null;

        try {
            markBitmap = Bitmap.createBitmap(sideLength, sideLength, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(markBitmap);
            canvas.drawColor(0);
            paint.setColor(-16777216);
            paint.setAlpha((int) 25.5D);
            paint.setDither(true);
            paint.setFilterBitmap(true);
            if (width > height) {
                canvas.translate((float) width - (float) sideLength - inter, (float) (sideLength - width) + inter);
            } else {
                canvas.translate((float) height - (float) sideLength - inter, (float) (sideLength - height) + inter);
            }

            //将该文字图片逆时针方向倾斜n度
            canvas.rotate((float) -45);

            for (int i = 0; i <= sideLength; i = (int) ((float) i + (float) strwid + inter)) {
                int count = 0;

                for (int j = 0; j <= sideLength; ++count) {
                    if (count % 2 == 0) {
                        canvas.drawText(gText, (float) i, (float) j, paint);
                    } else {
                        canvas.drawText(gText, (float) (i + strwid / 2), (float) j, paint);
                    }

                    j = (int) ((float) j + inter + (float) strhei);
                }
            }

            canvas.save();
        } catch (OutOfMemoryError outOfMemoryError) {
            if (markBitmap != null && !markBitmap.isRecycled()) {
                markBitmap.recycle();
                markBitmap = null;
            }
        }

        return markBitmap;
    }

    private WaterMarkUtil() {
    }

    static {
        INSTANCE = new WaterMarkUtil();
    }
}
