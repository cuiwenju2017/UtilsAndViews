## Andropid常用知识点收集

butterknife库的引入不用findViewById（详情查看：https://blog.csdn.net/juer2017/article/details/76914594）
```
implementation 'com.jakewharton:butterknife:8.8.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
```

去掉Activity上面的状态栏
```
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
setContentView(R.layout.activity_splash);
```

TextView文字两边对其库:快速使用build.gradle加入dependencies
```
implementation 'me.codeboy.android:align-text-view:2.3.2'

AlignTextView (不支持选择复制，在不需要进行选择复制的情况下使用，排版效果好)
 <me.codeboy.android.aligntextview.AlignTextView
        android:id="@+id/alignTv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

CBAlignTextView (新的版本，支持选择复制，排版效果比较的好)
<me.codeboy.android.aligntextview.CBAlignTextView
        android:id="@+id/cbAlignTv"
        android:textIsSelectable="true"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

如果需要支持android默认的选择复制，请在xml中加入以下代码:
android:textIsSelectable="true"
```

圆形图片的修改：
```
https://github.com/hdodenhof/CircleImageView
Gradle
dependencies {
    ...
    compile 'de.hdodenhof:circleimageview:2.1.0'
}
<de.hdodenhof.circleimageview.CircleImageView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/profile_image"
    android:layout_width="96dp"
    android:layout_height="96dp"
    android:src="@drawable/profile"
    app:civ_border_width="2dp"
    app:civ_border_color="#FF000000"/>
```
