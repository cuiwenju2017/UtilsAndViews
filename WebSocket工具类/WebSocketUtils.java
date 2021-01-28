package com.mirkowu.smarthome.utils;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WebSocketUtils {
    private String TAG = "web_socket";
    private static WebSocketUtils instance = null;
    WebSocketClient client;
    //转成的要操作的数据
    URI uri;
    //后台连接的url
//    String address = "ws://echo.websocket.org";
    //连接是否关闭，用于判断是主动关闭还是断开，断开需要重连
    boolean isHandClose;
    //记录心跳重联的次数
    int reconnectTimes;
    //心跳timer
    Disposable timerHeart;
    //重连timer
    Disposable timer;

    //懒加载单例模式，synchronized确保线程安全。
    // 适合没有多线程频繁调用的情况，多线程频繁调用的时候，可以用双重检查锁定DCL、静态内部类、枚举 等方法，文章后面会详细介绍。
    public static synchronized WebSocketUtils getInstance() {
        if (instance == null) {
            instance = new WebSocketUtils();
        }
        return instance;
    }

    public void initSocket(String address) {
        if (null != client && client.isOpen()) {
            Log.i(TAG, "initSocket: 登录成功!");
            return;
        }
        isHandClose = false;
        startSocket(address);
    }

    public void startSocket(String address) {
        try {
            uri = new URI(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (null == client || !client.isOpen()) {
            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    reconnectTimes = 0;
                    if (timer != null) {
                        timer.dispose();
                    }
                    //socket连接成功，通过eventbus通知房间，用户可以加入房间了
                    Log.i(TAG, "onOpen: 连接成功");
                   /* try {
                        registerWebSocket(address);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    //10秒钟进行一次心跳，防止异常断开
                    timerHeart = Observable.interval(10, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                           /* JSONObject jsonObject = new JSONObject();
                            jsonObject.put("type", "ping");
                            sendMessage(jsonObject.toString(),address);*/
                        }
                    });
                }

                @Override
                public void onMessage(String s) {
                    //后台返回的消息
                    Log.i(TAG, "onMessage: 收到消息" + s);
                    EventBus.getDefault().post(s, "webSocketMessage");
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    if (isHandClose) {
                        Log.i(TAG, "onClose: 主动关闭成功" + s);
                        reconnectTimes = 0;
                        if (timer != null) {
                            timer.dispose();
                        }
                        if (timerHeart != null) {
                            timerHeart.dispose();
                        }
                    } else {
                        //非正常关闭进行重连，重连每次两秒，最多十次
                        timer = Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.i(TAG, "accept: 重连");
                                reconnectTimes = reconnectTimes + 1;
                                startSocket(address);
                            }
                        });
                        //重联超过十次没成功，结束重连
                        if (reconnectTimes > 10) {
                            Log.i(TAG, "onClose: 关闭成功");
                            reconnectTimes = 0;
                            if (timer != null) {
                                timer.dispose();
                            }
                            if (timerHeart != null) {
                                timerHeart.dispose();
                            }
                        }
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.i(TAG, "onError: 出现错误" + e.getMessage());
                }
            };
            client.connect();
        }
    }

    //向服务器发送消息
    public void sendMessage(String s,String address) {
        if (client == null || client.isClosed() || !client.isOpen()) {
            Log.i(TAG, "sendMessage: 未建立实时通信");
            return;
        }
        try {
            client.send(s);
            Log.i(TAG, "sendMessage: 发送消息" + s);
        } catch (Exception e) {
            e.printStackTrace();
            startSocket(address);
        }
    }

    //关闭socket
    public void closeSocket() {
        isHandClose = true;
        if (client != null) {
            client.close();
            client = null;
        }
    }

    //向服务器发送初始化信息
    void registerWebSocket(String address) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "init");
        jsonObject.put("token", "");
        sendMessage(jsonObject.toString(),address);
    }

    //和服务器约定的传递的参数
    public void sendData(String type, String room_id,String address) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("token", "");
        jsonObject.put("room_id", room_id);
        sendMessage(jsonObject.toString(),address);
    }
}
