## Android常用的三方库

```
//视频加载库JiaoZiVideoPlayer：
https://github.com/lipangit/JiaoZiVideoPlayer
//rxjava引入的包
implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
implementation 'io.reactivex.rxjava2:rxjava:2.1.7'
//retrofit引入的包
implementation 'com.squareup.retrofit2:retrofit:2.3.0'
implementation 'com.google.code.gson:gson:2.2.4'
//返回给我的是一个json解析后的bean
implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
//rxjava2+retrofit搭配使用的依赖
implementation 'com.squareup.retrofit2:adapter-rxjava:2.3.0'
//okhttp日记拦截器
implementation 'com.squareup.okhttp3:logging-interceptor:3.9.1'
//沉浸状态栏
implementation 'com.jaeger.statusbarutil:library:1.5.1'
//图片圆角库
implementation 'com.github.SheHuan:NiceImageView:1.0.5'
//RecyclerAdapter框架
implementation 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.30'
//图片加载库
implementation 'com.github.bumptech.glide:glide:3.7.0'
//下拉刷新上拉加载更多
implementation 'com.scwang.smartrefresh:SmartRefreshLayout:1.0.5.1'
//photoview图片预览库
implementation('com.github.chrisbanes.photoview:library:+') {
    exclude group: 'com.android.support'
}
//侧滑库
implementation 'com.github.mcxtzhang:SwipeDelMenuLayout:V1.3.0'
//butterknife库的引入不用findViewById
implementation 'com.jakewharton:butterknife:8.8.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
```