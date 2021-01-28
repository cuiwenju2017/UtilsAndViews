## [Java-WebSocket](https://github.com/TooTallNate/Java-WebSocket)简单用法

//首先添加Java-WebSocket库的依赖
```
//Java-WebSocket
implementation "org.java-websocket:Java-WebSocket:1.5.1"
```

需要的地方初始化WebSocket
```
WebSocketUtils.getInstance().initSocket("ws://...");
```

关闭WebSocket
```
WebSocketUtils.getInstance().closeSocket();
```
