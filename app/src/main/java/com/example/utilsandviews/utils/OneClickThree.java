package com.example.utilsandviews.utils;

/**
 * 防止重复点击
 * if (!OneClickThree.isFastClick()) {
 * ToastUtil.s("点击了我");
 * } else {
 * ToastUtil.s("请不要连续操作");
 * }
 */
public class OneClickThree {

    // 两次点击间隔不能少于n秒 true:点击间隔小于n秒 false:点击间隔大于n秒
    private static final int FAST_CLICK_DELAY_TIME = 1000 * 1;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
