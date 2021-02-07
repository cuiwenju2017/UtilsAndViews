## 跳转到应用市场/应用，判断是否安装某应用并进入某应用工具类

判断是否安装某应用(已安装：打开应用或携带链接打开应用；未安装：跳转到应用市场安装)
```
//以QQ浏览器为例
if (MarketUtils.getTools().isAppInstalled(this, "com.tencent.mtt")) {//已安装
   //携带链接打开QQ浏览器
   MarketUtils.getTools().openInstalledAppInURL(this, "com.tencent.mtt", "com.tencent.mtt.MainActivity", urlStr);
   /**
    * 直接打开浏览器
    * 打开其他应用传入相对的包名和类名就行，但是要注意的是要打开的页面要在配置文件中加入以下配置：不然会报Permission Denial: starting Intent 错误
    * <intent-filter>
    *    <action android:name="android.intent.action.MAIN" />
    * </intent-filter>
    */
   //MarketUtils.getTools().openInstalledApp(this, "com.tencent.mtt", "com.tencent.mtt.MainActivity");
} else {
   //没有安装直接跳转到本机应用市场，默认本软件包名
   //MarketUtils.getTools().openMarket(this);
   //没有安装通过指定应用包名到应用市场搜索下载安装
   MarketUtils.getTools().openMarket(this, "com.tencent.mtt");
   //没有安装通过指定应用包名打开指定应用市场搜索
   //MarketUtils.getTools().openMarket(this, "com.tencent.mtt",MarketUtils.PACKAGE_NAME.TENCENT_PACKAGE_NAME);
}
```
