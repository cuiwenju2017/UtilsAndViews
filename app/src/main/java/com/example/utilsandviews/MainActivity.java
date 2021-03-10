package com.example.utilsandviews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.utilsandviews.broadcast.BatteryReceiver;
import com.example.utilsandviews.utils.ChinaDate;
import com.example.utilsandviews.utils.ChinaDate2;
import com.example.utilsandviews.utils.LunarUtils;
import com.example.utilsandviews.utils.MarketUtils;
import com.example.utilsandviews.utils.MobilePhone;
import com.example.utilsandviews.utils.NetworkUtils;
import com.example.utilsandviews.utils.TimeUtils;
import com.example.utilsandviews.utils.ToastUtil;
import com.example.utilsandviews.utils.WaterMarkUtil;
import com.example.utilsandviews.views.BatteryHorizontalView;
import com.example.utilsandviews.views.BatteryVerticalView;
import com.example.utilsandviews.views.TaiJiView;

import java.util.Calendar;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MainActivity extends BaseActivity {

    private TextView tv_time, tv_n_to_g, tv_g_to_n, tv_g_to_n2, tv_dianliang, tv_dianliang2, tv_baidu,
            tv_wangluo, tv_mohuchengdu, tv_caiyanglv;
    private Button btn_yanzheng, btn_start, btn_stop;
    private EditText et_phone;
    private Switch sc, sc_wuxian_wangluo;
    private TaiJiView tj;
    private BatteryReceiver receiver;
    private BatteryHorizontalView bv;
    private BatteryVerticalView bv2;
    private ImageView iv_gaosimohu;
    private SeekBar sb_mohuchengdu, sb_caiyanglv;

    public static final int MSG_ONE = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //通过消息的内容msg.what  分别更新ui
            switch (msg.what) {
                case MSG_ONE:
                    //年月日时分秒显示
                    tv_time.setText(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy年MM月dd日 HH:mm:ss"));
                    //网络连接情况
                    tv_wangluo.setText((NetworkUtils.isConnected() ? "有网络连接" : "无网络连接") + "\n" +
                            (NetworkUtils.getWifiEnabled() ? "Wifi已开启" : "Wifi未开启") + "\n" +
                            (NetworkUtils.isWifiConnected() ? "Wifi已连接" : "Wifi未连接") + "\n" +
                            (NetworkUtils.isWifiAvailable() ? "Wifi可用" : "Wifi不可用") + "\n" +
                            (NetworkUtils.isMobileData() ? "正在使用移动数据" : "未使用移动数据") + "\n" +
                            (NetworkUtils.is4G() ? "正在使用4G数据" : "未使用4G数据") + "\n" +
                            ("网络返回类型：" + NetworkUtils.getNetworkType()) + "\n" +
                            ("ipv4：" + NetworkUtils.getIPAddress(true)) + "\n" +
                            ("通过wifi返回ip地址：" + NetworkUtils.getIpAddressByWifi())
                    );
                    break;
                default:
                    break;
            }
        }
    };

    private String urlStr = "https://www.baidu.com/";
    private int mohuchengdu = 25;
    private int caiyanglv = 5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        tv_time = findViewById(R.id.tv_time);
        tv_n_to_g = findViewById(R.id.tv_n_to_g);
        tv_g_to_n = findViewById(R.id.tv_g_to_n);
        tv_g_to_n2 = findViewById(R.id.tv_g_to_n2);
        btn_yanzheng = findViewById(R.id.btn_yanzheng);
        et_phone = findViewById(R.id.et_phone);
        sc = findViewById(R.id.sc);
        tj = findViewById(R.id.tj);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        bv = findViewById(R.id.bv);
        tv_dianliang = findViewById(R.id.tv_dianliang);
        tv_dianliang2 = findViewById(R.id.tv_dianliang2);
        bv2 = findViewById(R.id.bv2);
        tv_baidu = findViewById(R.id.tv_baidu);
        tv_wangluo = findViewById(R.id.tv_wangluo);
        sc_wuxian_wangluo = findViewById(R.id.sc_wuxian_wangluo);
        iv_gaosimohu = findViewById(R.id.iv_gaosimohu);
        tv_mohuchengdu = findViewById(R.id.tv_mohuchengdu);
        tv_caiyanglv = findViewById(R.id.tv_caiyanglv);
        sb_mohuchengdu = findViewById(R.id.sb_mohuchengdu);
        sb_caiyanglv = findViewById(R.id.sb_caiyanglv);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initData() {
        //开启线程显示日期
        new TimeThread().start();

        //农历转公历，农历月，若为闰月则传入负数
        try {
            int year = Integer.parseInt(ChinaDate2.solarToLunar(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy-MM-dd"), true).substring(0, 4));
            int month = Integer.parseInt(ChinaDate2.solarToLunar(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy-MM-dd"), true).substring(5, 7));
            int monthDay = Integer.parseInt(ChinaDate2.solarToLunar(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy-MM-dd"), true).substring(8, 10));
            tv_n_to_g.setText(LunarUtils.getTranslateSolarString(year, month, monthDay));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //公历转农历
        tv_g_to_n.setText(LunarUtils.getTranslateLunarString(Integer.parseInt(TimeUtils.dateToString(TimeUtils.getTimeStame(), "yyyy")), Integer.parseInt(TimeUtils.dateToString(TimeUtils.getTimeStame(), "MM")), Integer.parseInt(TimeUtils.dateToString(TimeUtils.getTimeStame(), "dd"))));
        //公历转农历(带天干地支和属相)
        ChinaDate lunar = new ChinaDate(Calendar.getInstance());
        tv_g_to_n2.setText("" + lunar);

        //验证手机号
        btn_yanzheng.setOnClickListener(v -> {
            if (!MobilePhone.isMobileNO(et_phone.getText().toString())) {
                ToastUtil.s("手机号格式不正确");
            } else {
                ToastUtil.s("验证成功");
            }
        });

        //是否全屏显示（隐藏状态栏和字体）true表示全屏
        sc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fullScreen(true);
            } else {
                fullScreen(false);
            }
        });

        //为当前Activity添加水印
        WaterMarkUtil.showWatermarkView(MainActivity.this, "Hello World!");

        //自定义太极
        btn_start.setOnClickListener(v -> {
            tj.createAnimation();//动画开始  继续
        });

        btn_stop.setOnClickListener(v -> {
            tj.stopAnimation();//动画暂停
        });

        //注册电池电量广播监听
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver(bv, bv2, tv_dianliang);
        registerReceiver(receiver, filter);

        IntentFilter filter2 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver(bv, bv2, tv_dianliang2);
        registerReceiver(receiver, filter2);

        //跳转到应用市场
        tv_baidu.setOnClickListener(v -> {
            //以QQ浏览器为例
            if (MarketUtils.getTools().isAppInstalled(MainActivity.this, "com.UCMobile")) {//已安装
                //携带链接打开QQ浏览器
                MarketUtils.getTools().openInstalledAppInURL(MainActivity.this, "com.UCMobile", "com.uc.browser.ActivityUpdate", urlStr);
                /**
                 * 直接打开浏览器
                 * 打开其他应用传入相对的包名和类名就行，但是要注意的是要打开的页面要在配置文件中加入以下配置：不然会报Permission Denial: starting Intent 错误
                 * <intent-filter>
                 *    <action android:name="android.intent.action.MAIN" />
                 * </intent-filter>
                 */
                //MarketUtils.getTools().openInstalledApp(this, "com.UCMobile", "com.uc.browser.ActivityUpdate");
            } else {
                //没有安装直接跳转到本机应用市场，默认本软件包名
                //MarketUtils.getTools().openMarket(this);
                //没有安装通过指定应用包名到应用市场搜索下载安装
                MarketUtils.getTools().openMarket(MainActivity.this, "com.UCMobile");
                //没有安装通过指定应用包名打开指定应用市场搜索
                //MarketUtils.getTools().openMarket(this, "com.UCMobile",MarketUtils.PACKAGE_NAME.TENCENT_PACKAGE_NAME);
            }
        });

        //判断无限网络是否开启
        if (NetworkUtils.getWifiEnabled()) {
            sc_wuxian_wangluo.setChecked(true);
        } else {
            sc_wuxian_wangluo.setChecked(false);
        }

        //是否启用无限网络
        sc_wuxian_wangluo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                NetworkUtils.setWifiEnabled(true);
            } else {
                NetworkUtils.setWifiEnabled(false);
            }
        });

        tv_mohuchengdu.setText("模糊程度（" + mohuchengdu + ")：");
        tv_caiyanglv.setText("采样率（" + caiyanglv + ")：");
        //高斯模糊效果
        //BlurTransformation 第一个参数是1-25范围，随着数字越大，模糊度越高，第二个参数代表采样率，数字越大，越模糊
        Glide.with(this).load(R.drawable.icon_bg).apply(RequestOptions.bitmapTransform(new BlurTransformation(mohuchengdu, caiyanglv))).into(iv_gaosimohu);
        sb_mohuchengdu.setProgress(mohuchengdu);
        sb_caiyanglv.setProgress(caiyanglv);
        //改变模糊程度
        sb_mohuchengdu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mohuchengdu = progress;
                tv_mohuchengdu.setText("模糊程度（" + mohuchengdu + ")：");
                //高斯模糊效果
                //BlurTransformation 第一个参数是1-25范围，随着数字越大，模糊度越高，第二个参数代表采样率，数字越大，越模糊
                Glide.with(MainActivity.this).load(R.drawable.icon_bg).apply(RequestOptions.bitmapTransform(new BlurTransformation(mohuchengdu, caiyanglv))).into(iv_gaosimohu);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //改变采样率
        sb_caiyanglv.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                caiyanglv = progress;
                tv_caiyanglv.setText("采样率（" + caiyanglv + ")：");
                //高斯模糊效果
                //BlurTransformation 第一个参数是1-25范围，随着数字越大，模糊度越高，第二个参数代表采样率，数字越大，越模糊
                Glide.with(MainActivity.this).load(R.drawable.icon_bg).apply(RequestOptions.bitmapTransform(new BlurTransformation(mohuchengdu, caiyanglv))).into(iv_gaosimohu);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //开一个线程继承Thread
    public class TimeThread extends Thread {
        //重写run方法
        @Override
        public void run() {
            super.run();
            // do-while  一 什么什么 就
            do {
                try {
                    //每隔一秒 发送一次消息
                    Thread.sleep(1000);
                    Message msg = new Message();
                    //消息内容 为MSG_ONE
                    msg.what = MSG_ONE;
                    //发送
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁广播
        unregisterReceiver(receiver);
    }
}