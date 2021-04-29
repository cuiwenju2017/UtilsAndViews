-了解Android框架，了解各种优化技术，以及常见的系统问题

-对 Android 系统应用管理、进程管理、内存管理机制有深入理解

-精通Android UI布局开发，动画开发，以及多线程开发

-深入理解Android SDK NDK，熟悉Android平台体系结构，良好的架构设计能力；

-熟悉Android端缓存、消息队列、等相关技术，并了解其运行原理；

-熟练掌握Android高性能编程及性能调优

-Android平台地图或导航功能可视化软件开发
 
-扎实的Android技术,对四大组件,基本控件,自定义控件很熟悉的使用

-对多线程编程有充分了解

-基础的开源项目有基本的了解和使用

-精通Android开发平台的框架原理，深入了解Android软件架构，熟练使用Android SDK，NDK, JNI等进行开发

-精通Java，深入理解面向对象的设计模式，对数据结构、基本算法熟练掌握

-深入了解AndroidUI事件传递、布局、绘制等原理，熟悉不同分辨率屏幕适配

-熟悉Android开发模式（MVP、MVVM）、熟悉Android的各种开源组件及其原理

-熟悉Android下网络通信机制，对Socket通信、TCP/IP和HTTP有一定理解和经验

-对Android平台内存管理机制、进程管理机制、任务管理机制有深入理解、了解各种优化技术

-ArrayList的使用，ArrayList使用过程中有没有遇到过坑。

-> Arrays.asList不能add:此ArrayList非彼ArrayList，这是一个内部类，但是类名也叫 ArrayList.
   Arrays.asList方法创建出来的 ArrayList 和真正我们平时用的 ArrayList只是继承自同一抽象类的两个不同子类，
   而 Arrays.asList创建的 ArrayList 只能做一些简单的视图使用，不能做过多操作，所以 ArrayList的几种初始化方式里没有 Arrays.asList这一说。
```

```

-HashMap，aba问题，GC算法，泛型的边际。

-Handler中loop方法为什么不会导致线程卡死。

-动画的原理，有没有实战过补间动画；

-bugly上面收集到的最难的bug是怎样的，如何解决的?

-View的事件分发机制？

-MeasureSpec的意义，怎样计算MeasureSpec；

-LayoutParams是什么？

-自定义View和自定义ViewGroup的区别。

-View的绘制流程？ViewGroup的绘制流程？

-onmeasure，onLayout，onDraw的调度流程；

-自定义View的measure时机；

-有没有写过自定义View。

-Scroller 是怎么实现 View 的弹性滑动的？

-UI卡顿常见的原因及解决方案？

-Glide使用过程中的坑，EventBus使用过程中的坑。

-网络协议okhttp中的缓存机制，dex加载流程，组件化的原理。

-Fragment的生命周期管理过程中遇到的坑和解决办法。

-排序算法，观察者模式和单例模式，抽象类和接口的关系。

-databinding原理，binder原理。

-多进程通信问题；binder优势；aidl生成的java类细节；多进程遇到过哪些问题？

-子线程中维护的looper，消息队列无消息时候的处理节省性能的处理方案。

-你遇到的最难的技术问题和解决方案。

viewBinding的原理

Recycleview滑动怎么优化

一级缓存和二级缓存的区别

LayoutManager原理

onMeasure描述一下

onDraw描述一下

invalidate一定会调用onDraw么

Activity的生命周期

国际化插件

hashmap

2^n的原因（有多个原因）

22反转链表

协程原理

rxjava原理

leakcanary

如何分析引用链

线程池

给你一堆数据，如何画曲线？

事件分发

责任链模式

汽车加油问题

有序无序时删除链表的重复节点、都删除、保留一个

如何实现快手和抖音的整屏滑动效果

如何让运营能看懂社区文章

有没有遇到什么难点呀

线程池原理，几个参数

线程池里面的阻塞队列是什么类型

状态模式和策略模式的区别

sleep和wait的区别

Parcelable与Serializable

深拷贝与浅拷贝

如何实现深拷贝

sync和Reenterlock遇到异常的区别

可重入锁和不可重入锁

funA,funB,funC,funD，postInvalidate和Invalidate的区别

anr 分类及原理

viewmodel原理

用了哪些jetpack的东西

livedata为何具有生命周期感知能力

shareperference如何保证线程安全，为什么不是进程安全的，如何实现进程安全的

viewmodel是如何解决内存泄漏问题的？能解决么

mmkv共享内存和binder的区别

有做过多线程开发么

room如何实现orm的

kotlin和java产物一样么

各种图片格式的区别

如何保证内存与文件的同步

开一个线程commit，commit不用加锁么？

怎么确定bitmap被复用了

一张图片占用的内存大小

MVC,MMVM,MVP

MMVM如何解决MVP中存在的问题

内存泄漏

内存泄漏的几种情况

LeakCancary分析内存泄漏的原理

手写单例模式，并分析

找到两个数组中的两个元素的和等于某个值

StartService和BindService的生命周期

求浮点数的平方根

Binder的原理

设计模式

工厂模式

抽象工厂模式和普通工厂模式

建造者模式

启动模式有几种

共享内存原理

java能实现共享内存么

kotlin协程的四个dispatcher及区别

window，decorview，ViewRootImpl的关系
面试官 想让你回答如何window和windowManager如何联系的。viewRootImpl是联系window和decorview的纽带

进程间的通信方式

AIDL的用法

onIntercept在哪用

事件分发底层的产生

RecycleView的用法

里面的各种Manager作用
wake lock：使屏幕常亮

Activity是如何被限制到状态栏和导航栏之下的

canvas的save和restore的作用

canvas的density有什么作用

vsync是如何生成的

android中异步一般怎么实现

android中内存泄漏发生的情况

如何去获取view的宽高

如何实现一个悬浮窗

一个悬浮窗悬浮在顶层，覆盖了底层的app的启动图标，如果让事件启动图标接收到click事件

Android M之前与之后的权限变化

RxJava中map和flatmap的区别

view的绘制

viewgroup是如何刷新的

onMeasure,onlayout,onDraw分别起什么作用

onLayout的时候可以layout自己么？

洗牌问题

接雨水

两道设计

面向对象原则

用代码描述你觉得最有设计感的项目

典型情况下的Activity生命周期？

异常情况下的Activity的生命周期 & 数据如何保存和恢复？

从Activity A跳转到Activity B之后，然后再点击back建之后，它们的生命周期调用流程是什么？

如何统计Activity的工作时间？

Activity的启动模式 & 使用场景

如何在任意位置关掉应用所有Activity & 如何在任意位置关掉指定的Activity？

Activity的启动流程(从源码角度解析)？

Activity任务栈是什么？在项目中有用到它吗？说给我听听

广播是什么？

广播的注册方式有哪些？

广播的分类 & 特性 & 使用场景？

什么是内容提供者？

说说如何创建自己应用的内容提供者 & 使用场景

说说ContentProvider的原理

ContentProvider,ContentResolver,ContentObserver之间的关系

说说ContentProvider的权限管理

什么是Service?

说说Service的生命周期

Service和Thread的区别？

Android 5.0以上的隐式启动问题及其解决方案。

Service保活方案

IntentService是什么 & 原理 & 使用场景 & 和Service的区别。

创建一个独立进程的Service应该怎样做？

子线程一定不能更新UI吗？

给我说说Handler的原理

Handler导致的内存泄露你是如何解决的？

如何使用Handler让子线程和子线程通信？

HandlerThread是什么 & 原理 & 使用场景？

一个线程能否创建多个Handler,Handler和Looper之间的对应关系？

为什么Android系统不建议子线程访问UI？

AsyncTask是什么？能解决什么问题

给我谈谈AsyncTask的三个泛型参数作用 & 它的一些方法作用。

给我说说AsyncTask的原理

你觉得AsyncTask有不足之处吗？

Android中v4包下Fragment和app包下Fragment的区别是什么？

Fragment的生命周期 & 请结合Activity的生命周期再一起说说。

说说Fragment如何进行懒加载。

ViewPager + Fragment结合使用会出现内存泄漏吗 & 如何解决？

Fragment如何和Activity进行通信 & Fragment之间如何进行通信？

给我谈谈Fragment3种切换的方式以及区别 & 使用场景。

文件存储

说说Android中数据持久化的方式 & 使用场景

接触过MMKV吗？说说SharedPreference和它的区别

第三方数据库框架用过哪些？有没有自己封装过一个SQLite的库

SQLite是线程安全的吗 & SharedPreference是线程安全的吗？

请简单的给我说说什么是三级缓存？

SharedPreference的apply和commit的区别

谈谈你对SQLite事务的认识

ListView是什么？如何使用？

RecyclerView是什么？如何使用？如何返回不一样的Item。

ListView和RecycyclerView的区别是什么？

分别讲讲你对ListView & RecyclerView的优化经验。

给我说说RecyclerView的回收复用机制

说说你是如何给ListView & RecyclerView加上拉刷新 & 下拉加载更多机制

谈谈你是如何对ListView & RecycleView进行局部刷新的？

你对Bitmap了解吗？它在内存中如何存在？

有关Bitmap导致OOM的原因知道吗？如何优化？

给我谈谈图片压缩。

LruCache & DiskLruCache原理。

说说你平常会使用的一些第三方图片加载库,最好给我谈谈它的原理。

如果让你设计一个图片加载库，你会如何设计？

你知道Android中处理图片的一些库吗(OpenCv & GPUImage …)？

WebView会导致内存泄露吗？原因是什么？解决方式有哪些？

你知道Hybrid开发吗？说说你的相关经验

说说WebSettings & WebViewClient & WebChromeClient这三个类的作用 & 用法。

说说你了解的Hybrid框架。

如何提高原生的WebView加载速度？

谈谈你对webView工作机制的认识,你在项目中优化过它吗？说说是从哪些方面着手的？

什么是ViewPager?说说它的那些适配器。(校招&实习)

你了解ViewPager2吗？和ViewPager 1有哪些区别？

ViewPager + Fragment结合使用存在的内存泄漏的原因是什么？如何解决？

什么是事件分发机制？主要用来解决什么问题？(校招&实习)

给我说说事件分发的流程 & 你项目解决事件冲突的一些案例。

分别讲讲有关事件分发的三个方法的作用及关系。

如果我在一个设置了点击事件的TextView中dispatchTouchEvent方法强制返回ture或者false会发生什么？

谈谈你对MotionEvent的认识？Cancel事件是什么情况下触发的？

requestLayout(),onLayout(),onDraw(),drawChild()区别和联系？

加分项：

熟悉linux操作系统基本命令优先

熟悉ROS(Robot Operating System)操作优先

有openGL开发经验

熟悉Linux环境下的开发

有车载系统相关研发经验者优先

有Android端渲染软件研发经验者优先

有语音应用开发经验优先考虑

如果对kotlin、RXjava,Flutter有使用和掌握的优先

有地图、LBS产品经验加分，熟悉C++语言加分

熟悉底层通信协议（USB、BT、WIFI等）、有相关驱动研发经验者优先