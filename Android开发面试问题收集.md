[GitHub持续更新：（声明：本答案为个人收集与总结并非标准答案，仅供参考，如有错误还望指出，谢谢！如有重复可能是常问问题）](https://github.com/cuiwenju2017/UtilsAndViews/blob/master/Android%E5%BC%80%E5%8F%91%E9%9D%A2%E8%AF%95%E9%97%AE%E9%A2%98%E6%94%B6%E9%9B%86.md)

### ArrayList的使用，ArrayList使用过程中有没有遇到过坑。
[参考：读了这一篇，让你少踩 ArrayList 的那些坑](https://www.cnblogs.com/fengzheng/p/12986513.html)

Arrays.asList不能add:

此ArrayList非彼ArrayList，这是一个内部类，但是类名也叫 ArrayList.Arrays.asList方法创建出来的 ArrayList
和真正我们平时用的 ArrayList只是继承自同一抽象类的两个不同子类，而 Arrays.asList创建的 ArrayList 只能做
一些简单的视图使用，不能做过多操作，所以 ArrayList的几种初始化方式里没有 Arrays.asList这一说。

subList 方法:

1.上面提到了那个问题和 subList的坑有异曲同工之妙，都是由于返回的对象并不是真正的 ArrayList类型，而是和 ArrayList
集成同一父类的不同子类而已。所以会产生第一个坑，就是把当把 subList返回的对象转换成 ArrayList 的时候。

2.当你在 subList 中操作的时候，其实就是在操作原始的 ArrayList。

3.如果你使用 subList 方法获取了一个子列表，这之后又在原始列表上进行了新增或删除的操作，这是，你之前获取到的 
subList 就已经废掉了，不能用了，不能用的意思就是你在 subList 上进行遍历、增加、删除操作都会抛出异常，没错，
连遍历都不行了。其实与二坑的原因相同，subList 其实操作的是原始列表，当你在 subList 上进行操作时，会执行 checkForComodification
方法，此方法会检查原始列表的个数是否和最初的相同，如果不相同，直接抛出 ConcurrentModificationException异常。


### GC算法
[参考：几种常见GC算法介绍](https://blog.csdn.net/iva_brother/article/details/87870576)

常用的GC算法（引用计数法、标记-清除法、复制算法、标记-清除算法）


### 泛型的边际
[参考：java泛型之泛型边界](https://blog.csdn.net/renwuqiangg/article/details/51296621)


### Handler中loop方法为什么不会导致线程卡死。
[参考：为什么Looper中的Loop()方法不能导致主线程卡死?](https://blog.csdn.net/weixin_33738578/article/details/91412781)

1.耗时操作本身并不会导致主线程卡死, 导致主线程卡死的真正原因是耗时操作之后的触屏操作, 没有在规定的时间内被分发。

2.Looper 中的 loop()方法, 他的作用就是从消息队列MessageQueue 中不断地取消息, 然后将事件分发出去。


### 动画的原理
[参考：Android三种动画实现原理及使用](https://blog.csdn.net/weixin_39001306/article/details/80614286)

Android动画目前分为三种：

1.Frame Animation 帧动画，通过顺序播放一系列图像从而产生动画效果，。图片过多时容易造成OOM（Out Of Memory内存用完）异常。

2.Tween Animation 补间动画（又叫view动画），是通过对场景里的对象不断做图像变换（透明度、缩放、平移、旋转）
从而产生动画效果，是一种渐进式动画，并且View动画支持自定义。

3.Accribute Animation 属性动画，这也是在android3.0之后引进的动画，在手机的版本上是android４.0就可以使用这
个动 画，通过动态的改变对象的属性从而达到动画效果。

补间动画和属性动画的区别:

补间动画只是改变了View的显示效果而已，并不会真正的改变View的属性。而属性动画可以改变View的显示效果和属性。
举个例子：例如屏幕左上角有一个Button按钮，使用补间动画将其移动到右下角，此刻你去点击右下角的Button，它是绝
对不会响应点击事件的，因此其作用区域依然还在左上角。只不过是补间动画将其绘制在右下角而已，而属性动画则不会。


### View的事件分发机制？
[参考：一文读懂Android View事件分发机制](https://www.jianshu.com/p/238d1b753e64)


### View刷新机制
View绘制分三个步骤:

顺序是：onMeasure，onLayout，onDraw。调用invalidate方法只会执行onDraw方法；调用requestLayout方法只会执行
onMeasure方法和onLayout方法，并不会执行onDraw方法。所以当我们进行View更新时，若仅View的显示内容发生改变且
新显示内容不影响View的大小、位置，则只需调用invalidate方法；若View宽高、位置发生改变且显示内容不变，只需调
用requestLayout方法；若两者均发生改变，则需调用两者，按照View的绘制流程，推荐先调用requestLayout方法再调用
invalidate方法。


### 如果后台的Activity由于某原因被系统回收了如何存取数据
onSaveInstanceState是用来保存UI状态的，你可以使用它保存你所想保存的东西，在Activity杀死之前，它一般在onStop
或者onPause之前触发，onRestoreInstanceState则是在onResume之前触发回复状态，至于复写这个方法后onCreate方法
是否会被调用。

1.Activity被杀死了，onCreate会被调用，且onRestoreInstanceState 在 onResume之前恢复上次保存的信息。

2.Activity没被杀死，onCreate不会被调用，但onRestoreInstanceState 仍然会被调用，在 onResume之前恢复上次保
存的信息。

onSaveInstanceState和onRestoreInstanceState 是一对兄弟，一个负责存储，一个负责取出.“不一定”是成对的被调用的。


### activity的启动模式
standard:这个是android默认的Activity启动模式，每启动一个Activity都会被实例化一个Activity，并且新创建的
Activity在堆栈中会在栈顶。

singleTop:如果当前要启动的Activity就是在栈顶的位置，那么此时就会复用该Activity，并且不会重走onCreate方法，
会直接它的onNewIntent方法，如果不在栈顶，就跟standard一样的。如果当前activity已经在前台显示着，突然来了一
条推送消息，此时不想让接收推送的消息的activity再次创建，那么此时正好可以用该启动模式，如果之前activity栈中
是A-->B-->C如果点击了推动的消息还是A-->B--C，不过此时C是不会再次创建的，而是调用C的onNewIntent。而如果现
在activity中栈是A-->C-->B，再次打开推送的消息，此时跟正常的启动C就没啥区别了，当前栈中就是A-->C-->B-->C了。

singleTask:该种情况下就比singleTop厉害了，不管在不在栈顶，在Activity的堆栈中永远保持一个。这种启动模式相对
于singleTop而言是更加直接，比如之前activity栈中有A-->B-->C---D，再次打开了B的时候，在B上面的activity都会
从activity栈中被移除。下面的acitivity还是不用管，所以此时栈中是A-->B，一般项目中主页面用到该启动模式。

singleInstance:该种情况就用得比较少了，主要是指在该activity永远只在一个单独的栈中。一旦该模式的activity的
实例已经存在于某个栈中，任何应用在激活该activity时都会重用该栈中的实例，解决了多个task共享一个activity。其
余的基本和上面的singleTask保持一致。


### 请描述Activity和Fragment的关联。 
Fragment与Activity关联主要有两种方式，一种是通过在Activity的布局文件中写入fragment控件，使用name属性指定
一个Fragment；

另一种是在java代码中动态的添加与删除Fragment。


### 请描述Android中线程与线程，进程与进程之间如何通信
线程间通信：

1)主线程中创建子线程，如果为内部类或匿名内部类（new Thread(){}.start()）方式启动子线程，则可在子线程内部直
接调用主线程的成员变量、final修饰的局部变量；

2)若外部类（继承Thread接口的子线程类），可设置setter方法，主线程调用该方法通过参数传值。

3)子线程中向主线程通信，可通过主线程的handler发送信息或Runnable代码对象，或调用activity.runOnUiThread(){}
方式运行Runnable代码对象。

进程间通信：

1）隐式意图intent跨进程启动Activity，通过intent传递数据，通常以Uri形式；

2）ContentProvider内容提供者，提供其它进程调用增删改查数据的入口；

3）Broadcast广播，发送广播并通过intent传递数据

4）通过AIDL连接其它进程的Service服务


### 内存溢出和内存泄漏有什么区别？何时会产生内存泄漏？内存优化有哪些方法？
 一、原理：
 
 内存溢出（Out of memory）:系统会给每个APP分配内存也就是Heap size值，当APP所需要的内存大于了系统分配的内存，
就会造成内存溢出；通俗点就是10L桶只能装10L水，但是你却用来装11L的水，那就有1L的水就会溢出。

内存泄漏（Memory leak）:当一个对象不在使用了，本应该被垃圾回收器（JVM）回收，但是这个对象由于被其他正在使用
的对象所持有，造成无法被回收的结果，通俗点就是系统把一定的内存值A借给程序，但是系统却收不回完整的A值，那就是
内存泄漏。

二、两者的关系：

内存泄漏是造成内存溢出（OOM）的主要原因，因为系统分配给每个程序的内存也就是Heap size的值都是有限的，当内存
泄漏到一定值的时候，最终会发生程序所需要的内存值加上泄漏值大于了系统所分配的内存额度，就是触发内存溢出。

三、危害：

内存溢出：会触发Java.lang.OutOfMemoryError，造成程序崩溃

内存泄漏：过多的内存泄漏会造成OOM的发送，同样也会造成相关UI的卡顿现象

四、造成的原因以及处理：

A、大量的图片、音频、视频处理，当在内存比较低的系统上也容易造成内存溢出建议使用第三方，或者JNI来进行处理

B、Bitmap对象的不正确处理（内存溢出）不要在主线程中处理图片使用Bitmap对象要用recycle释放高效的处理大图

C、非静态匿名内部类Handler由于持有外部类Activity的引用所造成的内存泄漏根据WeakReference对象，对handler使
用弱引用，并且调用removeCallbacksAndMessages移除。

D、线程由于匿名内部类runnable持有activity的引用，从而关闭activity，线程未完成造成内存泄漏，把线程改成静态
内部类，调用WeakReference来持有外部资源。

E、BraodcastReceiver、File、Cursor等资源的使用未及时关闭在销毁activity时，应该及时销毁或者回收

F、static关键字修饰的变量由于生命周期过长，容易造成内存泄漏尽量少使用静态变量，一定要使用要及时进行制null处理

G、单列模式造成的内存泄漏，如context的使用，单列中传入的是activity的context，在关闭activity时，activity的
内存无法被回收，因为单列持有activity的引用在context的使用上，应该传入application的context到单列模式中，这
样就保证了单列的生命周期跟application的生命周期一样单列模式应该尽量少持有生命周期不同的外部对象，一旦持有该
对象的时候，必须在该对象的生命周期结束前制null。


### 请描述Android多分辨率的屏幕适配方法。
1）使用dp、sp单位，带有一定的适配性，根据dpi自动适配屏幕

2）创建不同分辨率、dpi、屏幕方向等规格的value文件夹，及对应dimens文件

3）创建不同分辨率、dpi、屏幕方向等规格的layout

4）代码动态设置View控件的高度、宽度

5）使用weight权重属性，按照比例分配View布局


### GC是什么？为什么要有GC？ 
GC是垃圾回收的意思（gabage collection），内存处理器是编程人员容易出现问题的地方，忘记或者错误的内存回收导致
程序或者系统的不稳定甚至崩溃，java的GC功能可以自动监测对象是否超过作用域从而达到自动回收内存的目的，java语
言没有提供释放已分配内存的俄显示操作方法。


### 列出几个XML包括解释技术，并简述其区别？
包括：DOM（Document Object Modal）文档对象模型，SAX（Simple API for XML）。

DOM是一次性将整个文档读入内存操作，如果是文档比较小，读入内存，可以极大提高操作的速度，但如果文档比较大，那
么这个就吃力了。所以此时SAX应用而生，它不是一次性的将整个文档读入内存，这对于处理大型文档就比较就力了


### switch语句能否作用在byte上，能否作用在long上，能否作用在String上？
switch可作用于char byte short int

switch可作用于char byte short int对应的包装类

switch不可作用于long double float boolean，包括他们的包装类

switch中可以是字符串类型,String(jdk1.7之后才可以作用在string上)

switch中可以是枚举类型


### 构造方法能否被重写或重载？什么是构造方法？构造方法的特点是什么？
概述：构造方法存在于类中，给对象数据（属性）初始化；

特点：方法名与类名一样；无返回值无void;

默认构造方法：我们不创建一个构造方法时，系统默认提供一个无参构造；当我们创建一个构造方法时，系统不再提供无
参构造，所以在实际项目中，全部手动给出无参构造

重载：存在于在一个类中，方法名相同，方法参数的类型或个数不同

重写：存在于子父类中，方法名、方法参数、返回值全部相同

所以：构造方法可以重载，不能重写

在一个类中，可以有多个构造方法（方法参数不同） ，即重载，来实现对象属性不同的初始化；但是子类中不能定义一个
方法无void无返回值的方法，编译错误，即子类无法继承构造方法，但是子类的构造器中可以调用父类的构造方法（默认自动调用无参构造）


### String s = new String(“xyz”);创建了几个String Object?二者之间有什么区别？
这个跟常量池没有关系，只要是new，都是重新分配堆空间，如果不区分栈和堆，这里创建了1个String Object。如果是
从jvm角度来说的话，它是创建了两个对象，String s是在栈里创建了一个变量，new String("xyz")是在堆里创建了一
个对象并被s引用到。


### try{}里有一个return语句，那么紧跟在这个try后的finally{}里的code会不会执行，什么时候被执行，在return前还是后？
会在return中间执行！

try 中的 return 语句调用的函数先于 finally 中调用的函数执行，也就是说 return 语句先执行，finally 语句后执
行，但 return 并不是让函数马上返回，而是 return 语句执行后，将把返回结果放置进函数栈中，此时函数并不是马上
返回，它要执行 finally 语句后才真正开始返回！但此时会出现两种情况：

①、如果finally中也有return，则会直接返回并终止程序，函数栈中的return不会被完成！；
②、如果finally中没有return，则在执行完finally中的代码之后，会将函数栈中的try中的return的内容返回并终止程序；
catch同try;
```
public class Test1 {
    public static void main(String[] args) {
        try {
            System.out.println(new Test1().testname());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String testname() throws Exception {
        String t = "";

        try {
            t = "try";
            System.out.println("try");
            return t;
        } catch (Exception e) {
            // result = "catch";
            t = "catch";
            return t;
        } finally {

            System.out.println("finally");
            // return t = "finally";
        }
    }
}
```
打印结果如下：

try

finally

try

将finally中的注释放开，打印结果如下：

try

finally

finally


### Integer与int的区别 
1、Integer是int的包装类，int则是java的一种基本数据类型 

2、Integer变量必须实例化后才能使用，而int变量不需要 

3、Integer实际是对象的引用，当new一个Integer时，实际上是生成一个指针指向此对象；而int则是直接存储数据值 

4、Integer的默认值是null，int的默认值是0


### 同步和异步有何异同，在什么情况下分别使用他们？
Java中交互方式分为同步和异步两种：

同步交互：指发送一个请求,需要等待返回,然后才能够发送下一个请求，有个等待过程；

异步交互：指发送一个请求,不需要等待返回,随时可以再发送下一个请求，即不需要等待。 

区别：一个需要等待，一个不需要等待，在部分情况下，我们的项目开发中都会优先选择不需要等待的异步交互方式。
哪些情况建议使用同步交互呢？比如银行的转账系统，对数据库的保存操作等等，都会使用同步交互操作，其余情况都优
先使用异步交互,如果数据将在线程间共享。例如正在写的数据以后可能被另一个线程读到，或者正在读的数据可能已经被
另一个线程写过了，那么这些数据就是共享数据，必须进行同步存取。当应用程序在对象上调用了一个需要花费很长时间来
执行的方法，并且不希望让程序等待方法的返回时，就应该使用异步编程，在很多情况下采用异步途径往往更有效率。


### 启动一个线程是用run()还是start()?
启动一个线程是start()方法。

启动线程之后start()方法会去调用run方法内容。

区别：start是创建并启动一个线程，而run是要运行线程中的代码。


### 字节流与字符流的区别？
字节流在操作的时候本身是不会用到缓冲区（内存）的，是与文件本身直接操作的，而字符流在操作的时候是使用到缓冲区的
字节流在操作文件时，即使不关闭资源（close方法），文件也能输出，但是如果字符流不使用close方法的话，则不会输出任
何内容，说明字符流用的是缓冲区，并且可以使用flush方法强制进行刷新缓冲区，这时才能在不close的情况下输出内容


### error和exception有什么区别？
Exception：

1．可以是可被控制(checked) 或不可控制的(unchecked)。

2．表示一个由程序员导致的错误。

3．应该在应用程序级被处理。

Error：

1．总是不可控制的(unchecked)。

2．经常用来用于表示系统错误或低层资源的错误。

3．如何可能的话，应该在系统级被捕捉。


### 当一个线程进入一个对象的一个synchronized方法后，其它线程是否可进入此对象的其它方法？
不能，一个对象的一个synchronized方法只能由一个线程访问。 

对象的synchronized方法不能进入了，但它的其他非synchronized方法还是可以访问的。


### ArrayList和Vector的区别，HashMap和Hashtable的区别？
ArrayList与Vector都实现的List接口，当不同的是ArrayList是线程不安全的，而Vector是线程安全的。

HashMap是线程不安全的，Hashtable是线程安全的，如果考虑性能的话使用HashMap,如果多个线程使用一个Map时，使用

Hashtable.HashMap可以使用null作为key与value的值，但Hashtable不行，如果那样用了，则会出现NullPointerException异常


### String 和StringBuffer有什么差别？在什么情况下使用它们？
String 对一串字符进行操作。不可变类。

StringBuffer 也是对一串字符进行操作，但是可变类。

String:是对象不是原始类型.为不可变对象,一旦被创建,就不能修改它的值.对于已经存在的String对象的修改都是重新创
建一个新的对象,然后把新的值保存进去.

String是final类,即不能被继承.

StringBuffer:是一个可变对象,当对他进行修改的时候不会像String那样重新建立对象它只能通过构造函数来建立,

StringBuffer sb = new StringBuffer();

note:不能通过付值符号对他进行付值.


### 介绍JAVA开发中常用的Collection FrameWork(集合框架)？
Collection

List 

ArrayList

LinkedList

Set 

HashSet

TreeSet

Map

HashMap

TreeMap

Collection是最基本的集合接口，一个Collection代表一组Object，即Collection的元素（Elements）Map提供key到value的映射。


### 描述下横竖屏切换时候activity的生命周期
此时的生命周期跟清单文件里的Activity的配置有关系。

1）不设置Activity的android:configChanges时，切屏会重新调用各个生命周期默认首先销毁当前activity，然后重新加载。

2）设置Activity如下属性后，由onConfigurationChanged方法接管屏幕方向、尺寸、键盘显隐的配置改变，则切屏不会重新
调用各个生命周期，只会执行onConfigurationChanged方法。通常在游戏开发，屏幕的朝向都是写死的。
android:configChanges="orientation|keyboardHidden|screenSize"


### android中的动画有哪几种，它们的特点和区别是什么？
帧动画，Frame动画，指通过指定的每一帧的图片和播放时间，有序的进行播放而形成的动画效果

视图动画，也就是所谓的补间动画。指通过指定View的初始状态、变化时间、方式、通过一系列的算法去进行图片变换，
从而实现动画效果。主要有scale、alpha、Translate、Rotate四种效果。 

注意：只是在视图层实现了动画效果，并没有真正改变View的属性

属性动画，通过不断地改变View的属性，不断重绘而形成动画效果。相比较视图动画，View的属性是真正改变了。
 
注意：Android3.0(API 11)以上才支持。


### 描述handler机制的原理
Message：消息，其中包含了消息ID，消息处理对象以及处理的数据等，由MessageQueue统一列队，终由Handler处理。
 
Handler：处理者，负责Message的发送及处理。使用Handler时，需要实现handleMessage(Message msg)方法来对特定
的Message进行处理，例如更新UI等。

MessageQueue：消息队列，用来存放Handler发送过来的消息，并按照FIFO规则执行。当然，存放Message并非实际意义
的保存，而是将Message以链表的方式串联起来的，等待Looper的抽取。

Looper：消息泵，不断地从MessageQueue中抽取Message执行。因此，MessageQueue需要一个Looper。

Thread：线程，负责调度整个消息循环，即消息循环的执行场所。

Handler，Looper和MessageQueue就是简单的三角关系。Looper和MessageQueue一一对应，创建一个Looper的同时，会
创建一个MessageQueue。而Handler与它们的关系，只是简单的聚集关系，即Handler里会引用当前线程里的特定Looper
和MessageQueue。这样说来，多个Handler都可以共享同一Looper和MessageQueue了。这些Handler也就运行在同一个线
程里，每个线程一个Loop而 一个MessageQueue。


### 如何将SQLite数据库（dictionary.db 文件）与apk文件一起发布？
可以将dictionary.db文件复制到Eclipse Android工程中的res\raw目录中，所有在res\raw目录中的文件不会被压缩，
这样可以直接提取该目录中的文件。

使用openDatabase方法来打开数据库文件，如果该文件不存在，系统会自动创建/sdcard/dictionary目录，并将res\raw
目录 中的 dictionary.db文件复制到/sdcard/dictionary目录中。

openDatabase方法的实现代码如下：
``` 
private SQLiteDatabase openDatabase()
    {
        try
        {
            // 获得dictionary.db文件的绝对路径
            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);
            // 如果/sdcard/dictionary目录中存在，创建这个目录
            if (!dir.exists())
                dir.mkdir();
            // 如果在/sdcard/dictionary目录中不存在
            // dictionary.db文件，则从res\raw目录中复制这个文件到
            // SD卡的目录（/sdcard/dictionary）
            if (!(new File(databaseFilename)).exists())
            {
                // 获得封装dictionary.db文件的InputStream对象
                InputStream is = getResources().openRawResource(R.raw.dictionary);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                // 开始复制dictionary.db文件
                while ((count = is.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            // 打开/sdcard/dictionary目录中的dictionary.db文件
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                    databaseFilename, null);
            return database;
        }
        catch (Exception e)
        {
        }
        return null;
    }
```
在openDatabase方法中使用了几个常量，这些常量是在程序的主类（Main）中定义的，代码如下：
``` 
public class Main extends Activity implements OnClickListener, TextWatcher{
    private final String DATABASE_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/dictionary";
    private final String DATABASE_FILENAME = "dictionary.db";
}
```


### 如何启用Service,如何停用service
Context.startService()和Context.bindService

解除绑定，可以调用unbindService()方法，调用该方法也会导致系统调用服务的onUnbind()-->onDestroy()方法


### 为什么要用ContentProvider?它和sql的实现上有什么差别?
ContentProvider出现的原因是为了数据共享，它和sql主要区别为它可以自定义共享的文件的暴露，sql不具备设置共享数据的权限。


### 请介绍一下Android的数据存储方式
使用SharedPreferences存储数据

文件存储数据

SQLite数据库存储数据

使用ContentProvider存储数据

网络存储数据


### 同步和异步的区别
 同步执行的话，就是程序会呆板地从头执行到尾，耗时间的东西不执行完，程序不会继续往下走，等待时间长的话，有时候
就会造成失去响应了。

异步的好处，就是把一些东西，特别是耗时间的东西扔到后台去运行了(doInBackground)，程序可以继续做自己的事情，
防止程序卡在那里失去响应。

同步是指两个线程的运行是相关的，其中一个线程要阻塞等待另外一个线程的运行。

异步的意思是两个线程毫无相关，自己运行自己的。


### 常见的内存泄漏的解决方案?
1. 非静态内部类、匿名内部类

解决办法： 将非静态内部类、匿名内部类 改成静态内部类，或者直接抽离成一个外部类。 
如果在静态内部类中，需要引用外部类对象，那么可以将这个引用封装在一个WeakReference中。

2.静态的View

解决办法： 在使用静态View时，需要确保在资源回收时，将静态View detach掉。

3. Handler：将Handler放入单独的类或者将Handler放入到静态内部类中（静态内部类不会持有外部类的引用）。如果想要在handler
内部去调用所在的外部类Activity，可以在handler内部使用弱引用的方式指向所在Activity，这样不会导致内存泄漏。
或者在onDestory时，调用相应的方法移除回调和删除消息。

4.监听器（各种需要注册的Listener，Watcher等）

解决办法： 在onDestory时，取消注册，editText.removeTextChangedListener

5. 资源对象没关闭造成内存泄漏

解决办法： 及时关闭资源

6. 属性动画

解决办法： 在在onDestory时，调用动画的cancel方法

7. RxJava

解决办法： 参考Uber出品的一个开源库AutoDispose的使用，可以参考下文： 
Android架构中添加AutoDispose解决RxJava内存泄漏

8. WebView

解决办法： 在销毁webview前一定要onDetachedFromWindow，我们先将webview从它的父view中移除再调用destroy方法

9. 其他的系统控件以及自定义View

在 Android Lollipop 之前使用 AlertDialog 可能会导致内存泄漏 
参考：一个内存泄漏引发的血案 
Dialog和DialogFragment在Android5.0以下的内存泄漏 
参考：解决Android5.0以下Dialog引起的内存泄漏 
View的post方法导致的内存泄漏分析
view中有线程或者动画 要及时停止
这是为了防止内存泄漏，可以在onDetachedFromWindow方法中结束，这个方法回调的时机是 当View的Activity退出或者
当前View被移除的时候 会调用 这时候是结束动画或者线程的好时机 另外还有一个对应的方法 onAttachedToWindow 这
个方法调用的时机是在包含View的Activity启动时回调,回调在onDraw方法 之前


### kotlin具有哪些优势？
更少的空指针异常

更少的代码量

更快的开发速度

更一致的开发体验 


### Android9.0有哪些新特性？
一、深度集成“Project Treble”，更方便对安卓系统进行升级。

二、支持利用Wifi进行定位并且测量距离

三、可注册5个蓝牙音频设备并保存每台蓝牙设备的音量偏好

四、升级多任务界面功能，在多任务界面中可以对App进行操作：

五、加强了对刘海屏的支持

六、禁止从安卓系统的新版本刷回到老版本


### SQLite如何查询第20条到第30条记录？SQLite如何拼接两个字符串？
``` 
Select * from table limit 19,11;
SELECT 'I''M '||'Chinese.' 
```


### MeasureSpec的意义，怎样计算MeasureSpec；
[参考：自定义View：Measure过程说明之MeasureSpec类详细讲解](https://blog.csdn.net/carson_ho/article/details/94545178)

意义：MeasureSpec是View的一个内部类，真正的身份就是帮助View完成测量功能。

计算：子View的MeasureSpec值根据子View的布局参数（LayoutParams）和父容器的MeasureSpec值计算得来的，具体计
算逻辑封装在getChildMeasureSpec()里。即：子view的大小由父view的MeasureSpec值 和 子view的LayoutParams属性共同决定。


### LayoutParams是什么？
[参考：自定义控件知识储备-LayoutParams的那些事](https://blog.csdn.net/yisizhu/article/details/51582622)

LayoutParams，顾名思义，就是Layout Parameters :布局参数。


### 自定义View和自定义ViewGroup的区别。
[参考：自定义View和自定义ViewGroup一步到位](https://blog.csdn.net/zxl1173558248/article/details/82901254)

ViewGroup是个View容器，它装纳child View并且负责把child View放入指定的位置。

自定义ViewGroup时必须要重写onLayout()方法（依次排列子view）,而自定义View没有子View，所以不需要onLayout()。


### View的绘制流程？ViewGroup的绘制流程？
[参考：View和ViewGroup的基本绘制流程](https://blog.csdn.net/u011155781/article/details/52584044)

view的绘制分为6步:

对视图的背景进行绘制

如果有必要，保存画布的图层，为褪色做准备(暂时忽略它)

对视图的内容进行绘制, 在onDraw(canvas)方法中完成

对当前视图的所有子视图进行绘制 ，调用dispatchDraw。

如果有必要，绘制褪色边缘和恢复层(暂时忽略它)

绘制装饰品（如滚动条）任何一个视图都是有滚动条的，只是一般情况下我们都没有让它显示出来而已.

即我们关心四个步骤:

绘制背景

绘制内容

绘制孩子

绘制装饰

绘制需要两个类, 画布(Canvas)和画笔(Paint)

ViewGroup的绘制流程：

ViewGroup继承View,绘制流程跟View是一致

ViewGroup的测量：

相同点:measure -> onMeasure

不同点：ViewGroup需要在onMeasure去测量孩子

自定义ViewGroup一定要重写onMeasure方法，如果不重写则子View获取不到宽和高。重写是在onMeasure方法中调用measureChildern（）
方法，遍历出所有子View并对其进行测量。

ViewGroup的布局：

相同点:layout (父容器调用) -》 onLayout

不同点:ViewGroup需要实现onLayout方法去布局孩子，调用孩子的layout方法，指定孩子上下左右的位置

requestLayout();//请求重新布局 onLayout

ViewGroup的绘制：

相同点:draw -> onDraw

不同点：ViewGroup一般不绘制自己，ViewGroup默认实现dispatchDraw去绘制孩子


### 自定义View的measure时机；
[参考：自定义控件View之onMeasure调用时机源码分析](https://blog.csdn.net/hty1053240123/article/details/76516426/)


### Scroller是怎么实现View的弹性滑动的？
[参考：Android Scroller实现View弹性滑动完全解析](https://www.jianshu.com/p/9419262a342a)

通过startScroll()下面的invalidate();
通过使View重绘，会间接的执行computeScroll()方法,
仅仅通过Scroller，并不能实现View的滑动效果，同时需要配合View的invalidate()、computeScroll()、scrollTo()方法才可以完成。

### UI卡顿常见的原因及解决方案？
人为在UI线程中做轻微耗时操作，导致UI线程卡顿；

布局Layout过于复杂，无法在16ms内完成渲染；

同一时间动画执行的次数过多，导致CPU或GPU负载过重；

View过度绘制，导致某些像素在同一帧时间内被绘制多次，从而使CPU或GPU负载过重；

View频繁的触发measure、layout，导致measure、layout累计耗时过多及整个View频繁的重新渲染；

内存频繁触发GC过多（同一帧中频繁创建内存），导致暂时阻塞渲染操作；

冗余资源及逻辑等导致加载和执行缓慢；

臭名昭著的ANR；

### Glide使用过程中的坑
[参考：Android glide使用过程中遇到的坑(进阶篇)](https://www.jianshu.com/p/deccde405e04)


### EventBus使用过程中的坑。
[参考：EventBus使用过程中，遇到的问题点](https://blog.csdn.net/wolfking0608/article/details/70239105)


### 网络协议okhttp中的缓存机制
[参考：OKHttp全解析系列（五） --OKHttp的缓存机制](https://www.jianshu.com/p/fb81207af121)


### dex加载流程
[参考：Android动态加载Dex过程](https://blog.csdn.net/a2923790861/article/details/80539862)


### 组件化的原理。
[参考：“终于懂了” 系列：Android组件化，全面掌握！ | 掘金技术征文-双节特别篇](https://juejin.cn/post/6881116198889586701)


### Fragment的生命周期管理过程中遇到的坑和解决办法。
[参考：踩坑，Fragment使用遇到那些坑](https://blog.csdn.net/xiaoxiaocaizi123/article/details/79074501)


### 抽象类和接口的关系。
[参考：Java 接口和抽象类区别](https://blog.csdn.net/xw13106209/article/details/6923556)


### databinding原理
[参考：Android的DataBinding原理介绍](https://blog.csdn.net/xiangzhihong8/article/details/52688943)


### binder原理。
[参考：简单理解Binder机制的原理](https://blog.csdn.net/augfun/article/details/82343249)


### 子线程中维护的looper，消息队列无消息时候的处理节省性能的处理方案。
[参考：子线程中：new Handler需要做哪些准备？消息队列中无消息的时候，Looper的处理方案是什么？](https://blog.csdn.net/yichen97/article/details/106107874/)


### viewBinding的原理[参考：Android ViewBinding使用及原理](https://www.jianshu.com/p/431c040b6af8)
原理就是Google在那个用来编译的gradle插件中增加了新功能，当某个module开启ViewBinding功能后，编译的时候就去
扫描此模块下的layout文件，生成对应的binding类。


### Recycleview滑动怎么优化
[参考：RecyclerView性能优化](https://www.jianshu.com/p/1853ff1e8de6?utm_campaign=maleskine)


### 一级缓存和二级缓存的区别
[参考：一级缓存，二级缓存，分布式缓存和页面缓存](https://blog.csdn.net/androidxiaogang/article/details/52915905)


### LayoutManager原理
[参考：自定义LayoutManager的详解及其使用](https://blog.csdn.net/lylodyf/article/details/52846602)


### 协程原理
[参考：Kotlin 协程实现原理](https://blog.csdn.net/suyimin2010/article/details/91125803)


### rxjava原理
[参考：RxJava原理解析一](https://www.jianshu.com/p/53b79866f58a)


### leakcanary
[参考：LeakCanary 内存泄漏原理完全解析](https://www.jianshu.com/p/59106802b62c)


### 线程池
[参考：深入理解线程和线程池（图文详解）](https://blog.csdn.net/weixin_40271838/article/details/79998327)


### 事件分发
[参考：图解 Android 事件分发机制](https://www.jianshu.com/p/e99b5e8bd67b)


### 责任链模式
[参考：一篇文章搞懂Java设计模式之责任链模式](https://blog.csdn.net/u012810020/article/details/71194853)


### 汽车加油问题
[参考：汽车加油问题 贪心算法 Java（详细注释）](https://blog.csdn.net/qq_37294163/article/details/103277358)


### 如何实现快手和抖音的整屏滑动效果
[参考：Android中模仿抖音的滑动RecycleView的实现](https://blog.csdn.net/weixin_36495794/article/details/80845103)


### 状态模式和策略模式的区别
[参考：状态模式和策略模式的区别](https://blog.csdn.net/ruangong1203/article/details/52514919)


### sleep和wait的区别
[参考：sleep和wait的区别](https://blog.csdn.net/qq_20009015/article/details/89980966)

1、sleep是Thread的静态方法，wait是Object的方法，任何对象实例都能调用。

2、sleep不会释放锁，它也不需要占用锁。wait会释放锁，但调用它的前提是当前线程占有锁(即代码要在synchronized中)。

3、它们都可以被interrupted方法中断。


### Parcelable与Serializable
[参考：序列化Serializable和Parcelable的理解和区别](https://www.jianshu.com/p/a60b609ec7e7)

Serializable（Java自带）：

Serializable是序列化的意思，表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。

Parcelable（android 专用）：

除了Serializable之外，使用Parcelable也可以实现相同的效果，
不过不同于将对象进行序列化，Parcelable方式的实现原理是将一个完整的对象进行分解，
而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了。


### 深拷贝与浅拷贝
[参考：彻底讲明白浅拷贝与深拷贝](https://www.jianshu.com/p/35d69cf24f1f)


### sync和Reenterlock遇到异常的区别
synchronized是关键字，ReentrantLock是类

ReentrantLock可以对获取锁的等待时间进行设置，避免死锁

ReentrantLock可以获取各种锁的信息

ReentrantLock可以灵活地实现多路通知

机制: sync操作Mark Word , lock调用Unsafe类的park()方法


### 可重入锁和不可重入锁
[参考：可重入锁和不可重入锁](https://blog.csdn.net/u014473112/article/details/82998477)


### postInvalidate和Invalidate的区别
[参考：简单讲下postInvalidate和Invalidate的区别](https://blog.csdn.net/codeyanbao/article/details/82694281)


### anr分类及原理
[参考：安卓ANR问题1_ANR问题类型及产生原理](https://www.jianshu.com/p/ddfc4678067d)

ANR问题类型及产生原理：

ANR(Application Not Responding):即应用无响应. 在日常使用安卓手机的过程中, 对最anr最直接的印象就是手机弹框
显示应用未响应. 选择继续等待或者关闭.如果应用程序的主线程在规定的时间内, 没有完成特定操作和事件, 就会发生ANR.

四种ANR类型：

KeyDispatchTimeout : input事件在5S内没有处理完成发生ANR

ServiceTimeout : bind,create,start,unbind等操作,前台Service在20s内,后台Service在200s内没有处理完成发生ANR

BroadcastTimeout : BroadcastReceiver onReceiver处理事务时前台广播在10S内,后台广播在60s内 (应用程序应该避
免在BroadcastReceiver里做耗时的操作或计算。如果响应Intent广播需要执行一个耗时的动作的话，应用程序应该启动一个 Service).
没有处理完成发生ANR

ProcessContentProviderPublishTimedOutLocked : ContentProvider publish在10s内没有处理完成发生ANR其中第四种ANR发生的概率最小.

ANR产生的常见原因：主线程耗时操作,如复杂的layout,庞大的for循环,IO等. (实际APP开发时开发者会避开这种, 没有见到过这种问题产生ANR);
主线程被子线程同步锁block. (当子线程先拿着锁, 主线程等待这把锁的时候, 子线程太耗时. 导致主线程一直被阻塞, 从而ANR)
主线程被Binder对端阻塞
Binder被占满导致主线程无法和SystemServer通信
得不到系统资源(CPU/RAM/IO) (耗时的动画需要大量的计算工作，可能导致CPU负载过重.)


### viewmodel原理
[参考：ViewModel 使用及原理解析](https://blog.csdn.net/xfhy_/article/details/88703853)


### livedata为何具有生命周期感知能力
[参考：Android LiveData我的理解](https://blog.csdn.net/alcoholdi/article/details/97259805)


### viewmodel是如何解决内存泄漏问题的？能解决么
[参考：使用ViewModel+Data Binding解决内存泄漏问题](https://www.jianshu.com/p/c8b3ce047de4?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)


### 各种图片格式的区别
目前android支持的5种图片格式，就是svg、png、webp、jpeg、gif、bmp。使用最多的还是png格式，因为支持RGB三色和
透明度设置，可以设置很好的logo效果。SVG格式，则是因为体积小，这个对于压缩包大小很有利，而且实现的效果和png差
不多，所以目前使用的也是越来越多。webp格式一般用于网络加载图片，图片体积相对于png或者jpg都是很有优势的。GIF
一般是作为动画展示的，但是由于图片太大，所以一般不建议使用，可以使用Lottie动画库代替。

SVG图片：

矢量图，由视觉设计出SVG图片，使用Androidstudio导入，最后是xml文件，可以适配各种分辨率的屏幕。先定义好要画的
图形，等待显示的时候，才会将图形画出来。这种方式体积很小，相对于png图片，可以减少50%，但是不适合复杂的图形。

PNG图片：

无损压缩，支持RGB三色和Alpha透明度设置，android基本使用都是这种方式，但是因为体积较大，所以每次apk包大的时
候都会将图片进行一遍压缩，https://tinypng.com/网站可以对png图片进行压缩。

WEBP图片：

google开发的压缩格式，体积相对于png图片减少25%，同时也支持透明度的设置，一般用于网络图片格式。

JPEG图片：

有损压缩，不支持透明背景，不适用与android系统的logo图片，适用于大图。

GIF图片：

无损压缩，主要是展示动画，可以设置透明背景色。可以使用Glide和Fresco两个库进行加载，Picasso不支持加载动态图片。

BMP图片：

bitmap，没有进行任何压缩，所以图片占空间很大，一般很少使用。


### 一张图片占用的内存大小
[参考：Android中一张图片占用的内存大小是如何计算的](https://www.cnblogs.com/dasusu/p/9789389.html)


### MVC,MMVM,MVP
[参考：MVC、MVP、MVVM，我到底该怎么选？](https://blog.csdn.net/singwhatiwanna/article/details/80904132)


### 手写单例模式，并分析
[参考：手写单例模式](https://blog.csdn.net/wand1995/article/details/97760451)

懒汉式:
``` 
public class Singleton{
	private static Singleton instance;
	private Singleton(){}
	public static synchronized Singleton getInstance(){
		if(instance == null){
			instance = new Singleton();
		}
		return instance;
	}
}
```
代码简单明了，而且使用了懒加载模式，加上了synchronized关键字，解决了在多线程情况下正常工作，但是它并不高效。
因为在任何时候只能有一个线程调用 getInstance() 方法。但是同步操作只需要在第一次调用时才被需要，即第一次创建
单例实例对象时。

双重检验锁模式:
``` 
public class Singleton{
	private volatile static Singleton instance;
	private Singleton(){}
	public static Singleton getInstance(){
		if(instance == null){ 		  //Single Checked
			Synchronized(Singleton.class){
				if(instance == null){ //Double Checked
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
}
```
双重检验锁模式（double checked locking pattern），是一种使用同步块加锁的方法。程序员称其为双重检查锁，因为
会有两次检查 instance == null，一次是在同步块外，一次是在同步块内。为什么在同步块内还要再检验一次？因为可能
会有多个线程一起进入同步块外的 if，如果在同步块内不进行二次检验的话就会生成多个实例了。

饿汉式static final field:
``` 
public class Singleton{
	//第一次加载类到内存中时就会初始化
	private static final Singleton instance = new Singleton();
	private Singleton(){}
	public static Singleton getInstance(){
		return instance;
	}
}
```
饿汉式写法很简单，因为单例的实例被声明成 static 和 final 变量了，在第一次加载类到内存中时就会初始化，所以创
建实例本身是线程安全的。缺点是它不是一种懒加载模式（lazy initialization），单例会在加载类后一开始就被初始化，
即使客户端没有调用 getInstance()方法。饿汉式的创建方式在一些场景中将无法使用，例如 Singleton 实例的创建是依
赖参数或者配置文件的，在 getInstance() 之前必须调用某个方法设置参数给它，那样这种单例写法就无法使用了。

静态内部类 static nested class:
``` 
public class Singleton{
	public static class Single{
		private static final Singleton INSTANCE = new Singleton();
	}
	private Singleton(){}
	public static final Singleton getInstance(){
		return Single.INSTANCE;
	}
}
```
这种写法仍然使用JVM本身机制保证了线程安全问题；由于 SingletonHolder 是私有的，除了 getInstance() 之外没有
办法访问它，因此它是懒汉式的；同时读取实例的时候不会进行同步，没有性能缺陷；也不依赖 JDK 版本。

枚举 Enum:
``` 
public enum EasySingleton{
    INSTANCE;
}
```
我们可以通过EasySingleton.INSTANCE来访问实例，这比调用getInstance()方法简单多了。创建枚举默认就是线程安全的，
所以不需要担心double checked locking，而且还能防止反序列化导致重新创建新的对象。


### 找到两个数组中的两个元素的和等于某个值
[参考：快速找出一个数组中的两个数字，让这两个数字之和等于一个给定的值](https://blog.csdn.net/mimi9919/article/details/51335337/)


### StartService和BindService的生命周期
[参考：startService和bindService的区别，生命周期以及使用场景](https://www.jianshu.com/p/73f10b6730c6)


### 求浮点数的平方根
[参考：求一个浮点数的平方根——牛顿迭代法](https://blog.csdn.net/HuanCaoO/article/details/79860213)


### 工厂模式
[参考：工厂模式](https://blog.csdn.net/qq_38238296/article/details/79841395)


### 建造者模式
[参考：一篇文章就彻底弄懂建造者模式(Builder Pattern)](https://www.jianshu.com/p/3d1c9ffb0a28)


### 共享内存原理
[参考：共享内存实现原理](https://blog.csdn.net/mw_nice/article/details/82888091)


### kotlin协程的四个dispatcher及区别
[参考：Kotlin协程核心库分析-1 Dispatchers](https://blog.csdn.net/qfanmingyiq/article/details/105184822)


### window，decorview，ViewRootImpl的关系,面试官 想让你回答如何window和windowManager如何联系的。viewRootImpl是联系window和decorview的纽带
Activity：

不负责控制视图，只是控制生命周期和处理事件，真正控制视图的是Window,Activity中含有一个Window，Window才是真
正代表一个窗口

Window：

视图的承载器，内部持有DecorView,而DecorView是View的根布局，Window是一个抽象类，真正的实现类是PhoneWindow，
PhoneWindow有个内部类DecorView,通过其来加载R.layout.activity_main。Window通过WindowManager将DecorView加
载其中，并将DecorView交给ViewRoot，进行视图的绘制及其他交互

DecorView：

是FrameLayout的子类，是android的根视图，相当于顶级View，一般来说内部包含竖直方向LinearLayout,在linearlayout
中含有三部分，上面是ViewStub,延迟加载的视图，中间是标题栏，下面是内容栏，就是我们熟悉的android.R.id.content

ViewRoot：

所有View的绘制及事件分发交互都是通过它来进行的，有个真正的实现类ViewRootImpl,它是链接WindowManagerService
和DecorView的纽带，View的测量，布局，绘制都是通过它来实现的，所以我们常见的事件分发真正的过程是


### 进程间的通信方式
[参考：Android进程间通信 - 几种方式的对比总结](https://blog.csdn.net/hzw2017/article/details/81275438)

常用有如下几种：

Bundle （四大组件间）

文件共享：可参考Android进程通信 - 序列化Serialzable与Parcelable中的示例

AIDL （基于Binder）：能自动生成Binder文件的工具，相当于工具。
Android进程通信 - AIDL的使用方法

Messenger（基于Binder）：类似于Hnadler发消息用法
Android进程间通信 - Messenger的使用和理解

ContentProvider（基于Binder）：Android进程间通信 - ContentProvider内容提供者

Socket（网络）：Android进程间通信 - Socket使用（TCP、UDP）


### AIDL的用法
[参考：Android AIDL使用详解](https://www.jianshu.com/p/29999c1a93cd)


### 使屏幕常亮
[参考：Android让屏幕保持常亮的三种方法](https://blog.csdn.net/llljjlj/article/details/80631664)


### canvas的save和restore的作用
[参考：canvas的save与restore方法的作用](https://blog.csdn.net/u014788227/article/details/52250208)

save：用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。 

restore：用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。


### vsync是如何生成的
[参考：理解 VSync](https://blog.csdn.net/zhaizu/article/details/51882768)


### android中异步一般怎么实现
[参考：Android实现异步的几种方法](https://blog.csdn.net/u011803341/article/details/52774867)


### android中内存泄漏发生的情况
[参考：Android 中内存泄漏的原因和解决方案](https://www.jianshu.com/p/abee7c186bfa)


### 如何去获取view的宽高
[参考：在activity中如何正确获取View的宽高](https://blog.csdn.net/zgh0711/article/details/70336354)


### 如何实现一个悬浮窗
[参考：Android 悬浮窗功能的实现](https://blog.csdn.net/huangliniqng/article/details/95372212/)


### Android M之前与之后的权限变化
[参考：Android 6.0 的权限管理变化](https://www.jianshu.com/p/f60260e98418)

运行时权限：Android 6.0 中不仅要在 AndroidManifest.xml 中声明权限，还在运行的时候增加了权限动态判断

涉及到的以下权限都会在运行时被判断：传感器、日历、摄像头、通讯录、地理位置、麦克风、电话、短信、存储空间、兼容方式

在 Android 6.0 中默认对 targetSdkVersion 小于 23 的应用申请的权限进行允许，但是在 targetSdkVersion 大于等
于 23 的应用中 ，就需要在代码上去进行动态的判断


### RxJava中map和flatmap的区别
[参考：Rxjava map和flatMap区别](https://blog.csdn.net/new_abc/article/details/84318464)


### viewgroup是如何刷新的
[参考：ViewGroup和View的理解和当子视图发生更新时通知viewgroup更新](https://blog.csdn.net/utilc/article/details/9838341)


### onMeasure,onlayout,onDraw分别起什么作用
[参考：Android onMeasure，onLayout，onDraw的理解](https://blog.csdn.net/JimTrency/article/details/52837776)

测量——onMeasure()：决定View的大小

布局——onLayout()：决定View在ViewGroup中的位置

绘制——onDraw()：如何绘制这个View。


### 洗牌问题
[参考：面试题之洗牌问题（java实现）](https://blog.csdn.net/qq_22993855/article/details/106932204)

``` 
    static List<Integer> left = new ArrayList<>();
    static List<Integer> right = new ArrayList<>();
    static int num1;
    static int num2;

    public static void main(String[] args) {
        //输入需要的牌的数量,洗牌的次数,以及每张牌的号码
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入牌数n:");
        int n = sc.nextInt();
        System.out.println("请输入洗牌的次数k:");
        int[] arr = new int[n];
        int[] newArr = new int[n];
        int k = sc.nextInt();
        System.out.println("请输入牌的序列:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println("您好,你此次拥有的牌数为:" + n + "张牌,你要求的洗牌次数为:"+ k + "次,其中:");
        //进行洗牌操作:传入牌数组,洗牌的次数,洗牌之后保存的新牌数组
        shuffleArr(arr,k,newArr);
    }

    //封装洗牌的方法
    public static void shuffleArr(int[] arr, int k, int[] newArr) {
        //进行洗牌操作
        for (int i = 0; i < k; i++) {
            //重点!!!!!!!!!!!!每次进行洗牌前,需要将左手和右手清空,同时,重新将排序后的新排序重新分给左右手,再进行新的排序操作!!!!!!!!!!!!!!!!!!!!!
            cards(arr);
            if(i % 2 == 0){
                //重新分配牌,并将牌分配到左右手上展示
                //cards(arr);
                int flag = 1;
                //当进行奇数次排序
                for (int p = newArr.length - 1;p >= 0;p--){
                    if(flag == 1 && p != 0){
                        newArr[p] = right.get(num2);
                        num2--;
                        flag = 0;
                    }else if(flag == 0 && p != 0){
                        newArr[p] = left.get(num1);
                        num1--;
                        flag = 1;
                    }else if(p == 0){
                        newArr[p] = left.get(num1);
                        System.out.println("第" + (i+1) + "次洗牌后的牌序为:");
                        printArrays(newArr);
                        copyArr(newArr,arr);
                    }
                }
                //最后再存储一次牌,并打印一下
                //cards(arr);

            }else {
                //重新分配牌,并打印左右手上此时有什么牌

                //进行偶数次排序
                int flag = 0;
                //当进行奇数次排序
                for (int p = newArr.length - 1;p >= 0;p--){
                    if(flag == 0 && p != 0){
                        newArr[p] = left.get(num1);
                        num1--;
                        flag = 1;
                    }else if(flag == 1 && p != 0){
                        newArr[p] = right.get(num2);
                        num2--;
                        flag = 0;
                    }else if(flag == 0 && p == 0){
                        newArr[p] = left.get(num1);
                        System.out.println("第" + (i+1) + "次洗牌后的牌序为:");
                        printArrays(newArr);
                        copyArr(newArr,arr);
                    }else if(flag == 1 && p == 0){
                        newArr[p] = right.get(num2);
                        System.out.println("第" + (i+1) + "次洗牌后的牌序为:");
                        //打印排序过后的新牌组
                        printArrays(newArr);
                        copyArr(newArr,arr);
                    }
                }
            }
            //cards(arr);
        }

    }
    //打印每次洗牌后的排序的方法
    public static void printArrays(int[] newArr) {
        for (int i = 0; i < newArr.length; i++) {
            System.out.print(newArr[i] + " ");
        }
        System.out.println("");
    }

    //将洗牌后的新牌组代替旧牌组的方法
    public static void copyArr(int[] newArr,int[] arr) {
        for (int i = 0; i < newArr.length; i++) {
            arr[i] = newArr[i];
        }
    }

    //存储每一次洗牌后的新牌组到左右手的方法,为下一次洗牌进行操作
    public static void store(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr.length % 2 != 0){
                if(i <= arr.length/2){
                    left.add(arr[i]);
                }else {
                    right.add(arr[i]);
                }
            }else{
                if(i < arr.length/2){
                    left.add(arr[i]);
                }else {
                    right.add(arr[i]);
                }
            }

        }
        num1 = left.size() - 1;
        num2 = right.size() - 1;
    }

    //打印左右手上各有哪些牌
    public static void printCards(){
        //打印左右手上各有什么牌
        System.out.println("左手上的牌为:");
        for (Integer card : left) {
            System.out.print(card + " ");
        }
        System.out.println("");
        System.out.println("右手上的牌为:");
        for (Integer card : right) {
            System.out.print(card + " ");
        }
        System.out.println("");
    }


    //将左右手清空,重新分配牌和左右手展示牌封装成一个方法
    public static void cards(int[] arr){
        left.clear();
        right.clear();
        store(arr);
        printCards();
    }
```


### 面向对象原则
[参考：面向对象的六大原则](https://blog.csdn.net/xiao_nian/article/details/87097110)

三大特性指的是封装、继承和多态；

六大原则指的是单一职责原则、开闭式原则、迪米特原则、里氏替换原则、依赖倒置原则以及接口隔离原则，其中，单一职
责原则是指一个类应该是一组相关性很高的函数和数据的封装，这是为了提高程序的内聚性，而其他五个原则是通过抽象来
实现的，目的是为了降低程序的耦合性以及提高可扩展性。


### 典型情况下的Activity生命周期？
[参考：Activity的生命周期（典型和异常生命周期）](https://blog.csdn.net/daxiong25/article/details/80745697)


### Activity的启动模式 & 使用场景
[参考：android activity 四大启动模式及使用场景](https://blog.csdn.net/u011337574/article/details/79979573)

android activity的启动模式有4种：分别是standard,singleTop,singleTask和singleInstance。在AndroidManifest.xml中，
通过标签的android:launchMode属性可以设置启动模式。


### 如何在任意位置关掉应用所有Activity？
[参考：随时随地退出应用（结束之前所有的Activity）](https://blog.csdn.net/juer2017/article/details/78728634?spm=1001.2014.3001.5506)


### 如何在任意位置关掉指定的Activity？
[参考：你知道吗？Android里如何关闭某个指定activity](https://blog.csdn.net/androidokk/article/details/96477182)


### Activity的启动流程(从源码角度解析)？
[参考：Android源码分析-Activity的启动过程](https://blog.csdn.net/singwhatiwanna/article/details/18154335)


### Activity任务栈是什么？在项目中有用到它吗？说给我听听
[参考：Activity启动模式与任务栈(Task)全面深入记录（上）](https://blog.csdn.net/javazejian/article/details/52071885)


### 广播的注册方式有哪些？
[参考：Android中广播的使用](https://blog.csdn.net/daluyang/article/details/79702321)


### 广播的分类 & 特性 & 使用场景？
[参考：Android：BroadcastRecevicer广播类型汇总](https://blog.csdn.net/carson_ho/article/details/53160580)

普通广播（Normal Broadcast）

系统广播（System Broadcast）

有序广播（Ordered Broadcast）

粘性广播（Sticky Broadcast）

App应用内广播（Local Broadcast）

使用场景：充电电池电量监听、时间变化监听等。


### 什么是内容提供者？
[参考：Android开发之内容提供者——创建自己的ContentProvider(详解)](https://blog.csdn.net/dmk877/article/details/50387741)

首先我们必须要明白的是ContentProvider(内容提供者)是android中的四大组件之一，但是在一般的开发中，可能使用比
较少。ContentProvider为不同的软件之间数据共享，提供统一的接口。


### ContentProvider,ContentResolver,ContentObserver之间的关系
[参考：Android ContentProvider、ContentResolver和ContentObserver的使用](https://blog.csdn.net/heqiangflytosky/article/details/31777363)


### 说说ContentProvider的权限管理
[参考：ContentProvider权限设置](https://blog.csdn.net/robertcpp/article/details/51337891)


### 什么是Service?
[参考：Android Service介绍和使用](https://blog.csdn.net/yh18668197127/article/details/86213380)

Service服务是Android四大组件之一,是一种程序后台运行的方案,用于不需要用户交互,长期运行的任务

Service并不是在单独进程中运行,也是运行在应用程序进程的主线程中,在执行具体耗时任务过程中要手动开启子线程,应用
程序进程被杀死,所有依赖该进程的服务也会停止运行


### 说说Service的生命周期
[参考：Android Service生命周期浅析](https://www.jianshu.com/p/cc25fbb5c0b3)


### Service和Thread的区别？
[参考：Android中Service和Thread的区别](https://blog.csdn.net/mynameishuangshuai/article/details/51821662)

Thread 是程序执行的最小单元，它是分配CPU的基本单位。可以用 Thread 来执行一些异步的操作。

Service是Android的四大组件之一，被用来执行长时间的后台任务。默认情况下Service是运行在主线程中的。

二者的使用上的区别：

1.在Android中，Thread只是一个用来执行后台任务的工具类，它可以在Activity中被创建，也可以在Service中被创建。

2.Service组件主要有两个作用：后台运行和跨进程访问。service可以在android系统后台独立运行，线程是不可以。

3.Service类是可以供其他应用程序来调用这个Service的而Thread只是在本类中在使用，如果本类关闭那么这个thread也
就下岗了而Service类则不会。

4.如果需要执行复杂耗时的操作，必须在Service中再创建一个Thread来执行任务。Service的优先级高于后台挂起的Activity，
当然也高于Activity所创建的Thread，因此，系统可能在内存不足的时候优先杀死后台的Activity或者Thread，而不会轻
易杀死Service组件，即使被迫杀死Service，也会在资源可用时重启被杀死的Service。


### Android 5.0以上的隐式启动问题及其解决方案。
[参考：Android 5.0之后隐式声明Intent 启动Service引发的问题](https://blog.csdn.net/l2show/article/details/47421961)


### Service保活方案
[参考：Android进程保活（最新）带你浅析这几种可行性的保活方案](https://blog.csdn.net/qq_37199105/article/details/81224842)


### IntentService是什么 & 原理 & 使用场景 & 和Service的区别。
[参考：Android面试一天一题（1Day）IntentService作用是什么](https://blog.csdn.net/zxccxzzxz/article/details/52377191)

[参考：IntentService和Service区别](https://www.jianshu.com/p/5a32226d2ce0)


### 创建一个独立进程的Service应该怎样做？
[参考：Android 通过Service 单独进程模仿离线推送](https://blog.csdn.net/yan8024/article/details/48790339)


### 子线程一定不能更新UI吗？
[参考：Android：为什么子线程不能更新UI](https://www.jianshu.com/p/58c999d3ada7)

子线程可以在ViewRootImpl还没有被创建之前更新UI；

访问UI是没有加对象锁的，在子线程环境下更新UI，会造成不可预期的风险；

ViewRootImpl对象是在onResume方法回调之后才创建，那么就说明了为什么在生命周期的onCreate方法里，甚至是onResume
方法里都可以实现子线程更新UI，因为此时还没有创建ViewRootImpl对象，并不会进行是否为主线程的判断；


### Handler的原理
[参考：android Handler机制原理解析（一篇就够，包你形象而深刻）](https://blog.csdn.net/luoyingxing/article/details/86500542)


### Handler导致的内存泄露是如何解决的？
[参考：handler导致内存泄露的真正原因](https://blog.csdn.net/alex01550/article/details/82744191)

1.使用static 修饰的handler，但是一般会弱引用activity对象，因为要使用activity对象中的成员

2.单独定义handler，同样可以弱引用activity

3.使用内部类的handler，在onDestroy方法中removeCallbacksAndMessages


### 如何使用Handler让子线程和子线程通信？
[参考：Handler实现子线程与子线程、主线程之间通信](https://blog.csdn.net/androidsj/article/details/79816866)


### HandlerThread是什么 & 原理 & 使用场景？
[参考：handlerThread使用场景分析及源码解析](https://blog.csdn.net/nightcurtis/article/details/77676349)


### 一个线程能否创建多个Handler,Handler和Looper之间的对应关系？
[参考：【面试】一个线程能否创建多个Handler，Handler跟Looper之间的对应关系 ？](https://blog.csdn.net/u013293125/article/details/105411971/)

一个线程能够创建多个Handler

Handler跟Looper没有对应关系，线程才跟Looper有对应关系，一个线程对应着一个Looper


### 为什么Android系统不建议子线程访问UI？
[参考：Android：为什么子线程不能更新UI](https://www.jianshu.com/p/58c999d3ada7)

谷歌提出：“一定要在主线程更新UI”，实际是为了提高界面的效率和安全性，带来更好的流畅性；反推一下，假如允许多线
程更新UI，但是访问UI是没有加锁的，一旦多线程抢占了资源，那么界面将会乱套更新了，体验效果就不言而喻了；
所以在Android中规定必须在主线程更新UI。


### AsyncTask是什么？能解决什么问题
[参考：AsyncTask](https://www.jianshu.com/p/6751aa65fcb6)

AsyncTask是什么：AsyncTask是Android封装的一个轻量级的异步类，可以在线程池中执行异步任务，并可以将执行进度和结果传递给UI线程。

AsyncTask的内部封装了两个线程池(SerialExecutor、THREAD_POOL_EXECUTOR)和一个Handler。

其中SerialExecutor线程池用于任务的排队，让需要执行的多个耗时任务，按顺序排列，THREAD_POOL_EXECUTOR线程池
才真正地执行任务，Handler用于从工作线程切换到主线程。

AsyncTask出现的契机：线程的创建和销毁都会有开销，如果在进程中频繁的创建和销毁线程，是不可取的。应该采用线程池，可以避免因为频繁创
建和销毁线程所带来的系统开销。


### 给我谈谈AsyncTask的三个泛型参数作用 & 它的一些方法作用。
[参考：AsyncTask](https://www.jianshu.com/p/6751aa65fcb6)

``` 
public abstract class AsyncTask<Params, Progress, Result>{}
```
Params：开始异步任务执行时传入的参数类型；

Progress：异步任务执行过程中，返回下载进度值的类型；

Result：异步任务执行完成后，返回的结果类型；

如果AsyncTask确定不需要传递具体参数，那么这三个泛型参数可以用Void来代替。


### AsyncTask的原理
[参考：AsyncTask的原理和缺点](https://blog.csdn.net/wjinhhua/article/details/60578133)


### Android中v4包下Fragment和app包下Fragment的区别是什么？
1.app包中的fragment在3.0以上才可以使用，最好使用兼容低版本的。

2.v4包下的可以兼容到 1.6版本。

3.两者都可以使用<fragment>标签，但是app包下的fragment所在的activity继承Activity即可。v4包下的fragment所在
的activity必须继承FragmentActivity，否则会报错。

4.getSupportFragmentManager（）对应的是 v4 ；getFragmentManager（）对应的是 app；


### Fragment的生命周期 & 请结合Activity的生命周期再一起说说。
[参考：Activity与Fragment的生命周期](https://blog.csdn.net/zjclugger/article/details/10442335)


### Fragment如何进行懒加载。
[参考：Androidx 下 Fragment 懒加载的新实现](https://www.jianshu.com/p/2201a107d5b5?utm_campaign=hugo)


### ViewPager + Fragment结合使用会出现内存泄漏吗 & 如何解决？
[参考：记录ViewPager+fragment 内存泄露问题](https://blog.csdn.net/qq_32536991/article/details/88837924)


### Fragment如何和Activity进行通信 & Fragment之间如何进行通信？
[参考：Android：手把手教你 实现Activity 与 Fragment 相互通信（含Demo）](https://www.jianshu.com/p/825eb1f98c19)

[参考：【Android】Fragment之间数据传递的三种方式](https://www.jianshu.com/p/f87baad32662)


### 给我谈谈Fragment3种切换的方式以及区别 & 使用场景。
[参考：Fragment生命周期-3种不同的切换方式生命周期变化](https://blog.csdn.net/luoxianli2011/article/details/106899777)

1.通过add、hide、show方式来切换fragment

当以这种方式进行Fragment1和Fragment2切换时，Fragment隐藏的时候并不走onDestroyView()，所有的现实也不会走onCreateView()，所有的view都会保存在内存。

2.使用replace()方法进行切换Fragment

通过replace方法进行替换的方式，Fragment都是进行了销毁、重建新Fragment的过程，相当于走了一整套的生命周期

3.使用ViewPager方式切换Fragment

当使用ViewPager进行Fragment切换时，所有的Fragment都会进行预加载。


### 说说Android中数据持久化的方式 & 使用场景
第一种： 使用SharedPreferences存储数据：适用范围：保存少量的数据，且这些数据的格式非常简单：字符串型、基本类型的值。比如应用程序的各种配置信息
（如是否打开音效、是否使用震动效果、小游戏的玩家积分等），解锁口 令密码等

核心原理：保存基于XML文件存储的key-value键值对数据，通常用来存储一些简单的配置信息。通过DDMS的File Explorer
面板，展开文件浏览树,很明显SharedPreferences数据总是存储在/data/data//shared_prefs目录下。SharedPreferences
对象本身只能获取数据而不支持存储和修改,存储修改是通过SharedPreferences.edit()获取的内部接口Editor对象实现。
SharedPreferences本身是一 个接口，程序无法直接创建SharedPreferences实例，只能通过Context提供的getSharedPreferences(String name, int mode)
方法来获取SharedPreferences实例，该方法中name表示要操作的xml文件名，第二个参数具体如下：

Context.MODE_PRIVATE: 指定该SharedPreferences数据只能被本应用程序读、写。

Context.MODE_WORLD_READABLE: 指定该SharedPreferences数据能被其他应用程序读，但不能写。

Context.MODE_WORLD_WRITEABLE: 指定该SharedPreferences数据能被其他应用程序读，写

SharedPreferences对象与SQLite数据库相比，免去了创建数据库，创建表，写SQL语句等诸多操作，相对而言更加方便，
简洁。但是SharedPreferences也有其自身缺陷，比如其职能存储boolean，int，float，long和String五种简单的数据
类型，比如其无法进行条件查询等。所以不论SharedPreferences的数据存储操作是如何简单，它也只能是存储方式的一种
补充，而无法完全替代如SQLite数据库这样的其他数据存储方式。

第二种： 文件存储数据：可以在设备本身的存储设备或者外接的存储设备中创建用于保存数据的文件。同样在默认的状态下，文件是不能在不同的程序间共享。

写文件：调用Context.openFileOutput()方法根据指定的路径和文件名来创建文件，这个方法会返回一个FileOutputStream对象。

读取文件：调用Context.openFileInput()方法通过制定的路径和文件名来返回一个标准的Java FileInputStream对象。

第三种：SQLite存储数据：SQLite Database数据库。Android对数据库的支持很好，它本身集成了SQLite数据库，每个应用都可以方便的使用它，或
者更确切的说，Android完全依赖于SQLite数据库，它所有的系统数据和用到的结构化数据都存储在数据库中。 它具有以
下优点： a. 效率出众，这是无可否认的 

b. 十分适合存储结构化数据 

c. 方便在不同的Activity，甚至不同的应用之间传递数据。　　

第四种：ContentProvider：Android系统中能实现所有应用程序共享的一种数据存储方式，由于数据通常在各应用间的是互相私密的，所以此存储方式
较少使用，但是其又是必不可少的一种存储方式。例如音频，视频，图片和通讯录，一般都可以采用此种方式进行存储。
每个ContentProvider都会对外提供一个公共的URI（包装成Uri对象），如果应用程序有数据需要共享时，就需要使用ContentProvider
为这些数据定义一个URI，然后其他的应用程序就通过Content Provider传入这个URI来对数据进行操作。


### SharedPreference和MMKV的区别
[参考：MMKV组件实现原理以及和SharedPreferences的比较（一）](https://blog.csdn.net/qq_39424143/article/details/95103783)

MMKV是基于mmap内存映射关系的key-value组件，底层序列化/反序列化使用protobuf实现。性能高，稳定性强。

SharedPreferences是Android提供的一种使用XML文件保存内容的机制，内部通过XML写入文件


### SQLite是线程安全的吗 & SharedPreference是线程安全的吗？
[参考：SQLite线程安全相关整理](https://blog.csdn.net/u013427969/article/details/90209917)


### 什么是三级缓存？
[参考：android三级缓存详解](https://blog.csdn.net/u012138137/article/details/50921209?locationNum=3&fps=1)


### SharedPreference的apply和commit的区别
[参考：SharedPreferences中的commit和apply方法](https://www.jianshu.com/p/c8d10357c939)

commit和apply虽然都是原子性操作，但是原子的操作不同，commit是原子提交到数据库，所以从提交数据到存在Disk中都是同步过程，中间不可打断。

而apply方法的原子操作是原子提交的内存中，而非数据库，所以在提交到内存中时不可打断，之后再异步提交数据到数据库中，因此也不会有相应的返回值。

所有commit提交是同步过程，效率会比apply异步提交的速度慢，但是apply没有返回值，永远无法知道存储是否失败。

在不关心提交结果是否成功的情况下，优先考虑apply方法。


### 谈谈你对SQLite事务的认识
[参考：Sqlite事务理解](https://blog.csdn.net/qq_40111789/article/details/82670810)


### ListView和RecycyclerView的区别是什么？
[参考：ListView和RecyclerView的使用和区别](https://blog.csdn.net/qq_39899087/article/details/86513378)

1）ListView布局单一，RecycleView可以根据LayoutManger有横向，瀑布和表格布局

2）自定义适配器中，ListView的适配器继承ArrayAdapter;RecycleView的适配器继承RecyclerAdapter,并将范类指定为子项对象类.ViewHolder(内部类)。

3）ListView优化需要自定义ViewHolder和判断convertView是否为null。 而RecyclerView是存在规定好的ViewHolder。

4）绑定事件的方式不同，ListView是在主方法中ListView对象的setOnItemClickListener方法；RecyclerView则是在子项具体的View中去注册事件。


### 分别讲讲你对ListView & RecyclerView的优化经验。
[参考：ListView的优化](https://www.jianshu.com/p/f0408a0f0610)

[参考：RecyclerView性能优化及高级使用](https://blog.csdn.net/smileiam/article/details/88396546)


### RecyclerView的回收复用机制
[参考：基于滑动场景解析RecyclerView的回收复用机制原理](https://www.jianshu.com/p/9306b365da57)


### 说说你是如何给ListView & RecyclerView加上拉刷新 & 下拉加载更多机制
[参考：ListView增加下拉刷新，上拉加载更多](https://www.jianshu.com/p/adb2f8aa863c)

[参考：让RecyclerView上拉刷新下拉加载更多更简便易用](https://www.jianshu.com/)


### 谈谈你是如何对ListView & RecycleView进行局部刷新的？
[参考：android ListView 局部刷新](https://blog.csdn.net/bzlj2912009596/article/details/80660112)

[参考：Android recyclerview 局部刷新问题](https://blog.csdn.net/yanmantian/article/details/103615971)


### Bitmap在内存中如何存在的？
[参考：Bitmap 在内存中有多大？](https://blog.csdn.net/u011494285/article/details/80523775)


### 有关Bitmap导致OOM的原因？如何优化？
因为Android系统对内存有一个限制，如果超出该限制，就会出现OOM。为了避免这个问题，需要在加载资源时尽量考虑如何节约内存，尽快释放资源等等。

Android系统版本对图片加载，回收的影响：

1，在Android 2.3以及之后，采用的是并发回收机制，避免在回收内存时的卡顿现象。

2，在Android 2.3.3(API Level 10)以及之前，Bitmap的backing pixel 数据存储在native memory, 与Bitmap本身是
分开的，Bitmap本身存储在dalvik heap 中。导致其pixel数据不能判断是否还需要使用，不能及时释放，容易引起OOM错误。
 从Android 3.0(API 11)开始，pixel数据与Bitmap一起存储在Dalvik heap中。

在加载图片资源时，可采用以下一些方法来避免OOM的问题：1，在Android 2.3.3以及之前，建议使用Bitmap.recycle()方法，及时释放资源。

2，在Android 3.0开始，可设置BitmapFactory.options.inBitmap值，(从缓存中获取)达到重用Bitmap的目的。如果设置，
则inPreferredConfig属性值会被重用的Bitmap该属性值覆盖。

3，通过设置Options.inPreferredConfig值来降低内存消耗：

默认为ARGB_8888: 每个像素4字节. 共32位。

Alpha_8: 只保存透明度，共8位，1字节。

ARGB_4444: 共16位，2字节。

RGB_565:共16位，2字节。

如果不需要透明度，可把默认值ARGB_8888改为RGB_565,节约一半内存。

4，通过设置Options.inSampleSize 对大图片进行压缩，可先设置Options.inJustDecodeBounds，获取Bitmap的外围数据，
宽和高等。然后计算压缩比例，进行压缩。

5，设置Options.inPurgeable和inInputShareable：让系统能及时回收内存。inPurgeable:设置为True,则使用BitmapFactory
创建的Bitmap用于存储Pixel的内存空间，在系统内存不足时可以被回收，当应用需要再次访问该Bitmap的Pixel时，系统
会再次调用BitmapFactory 的decode方法重新生成Bitmap的Pixel数组。设置为False时，表示不能被回收。inInputShareable：设
置是否深拷贝，与inPurgeable结合使用，inPurgeable为false时，该参数无意义。

6，使用decodeStream代替其他decodeResource,setImageResource,setImageBitmap等方法来加载图片。区别： decodeStream
直接读取图片字节码，调用nativeDecodeAsset/nativeDecodeStream来完成decode。无需使用Java空间的一些额外处理过程，
节省dalvik内存。但是由于直接读取字节码，没有处理过程，因此不会根据机器的各种分辨率来自动适应，需要在hdpi,mdpi
和ldpi中分别配置相应的图片资源，否则在不同分辨率机器上都是同样的大小(像素点数量)，显示的实际大小不对。decodeResource
会在读取完图片数据后，根据机器的分辨率，进行图片的适配处理，导致增大了很多dalvik内存消耗。

decodeStream调用过程：decodeStream(InputStream,Rect,Options) -> nativeDecodeAsset/nativeDecodeStream

decodeResource调用过程：即finishDecode之后，调用额外的Java层的createBitmap方法，消耗更多dalvik内存。

decodeResource(Resource,resId,Options)  -> decodeResourceStream (设置Options的inDensity和inTargetDensity参数) 
 -> decodeStream() (在完成Decode后，进行finishDecode操作)finishDecode() -> Bitmap.createScaleBitmap()
 (根据inDensity和inTargetDensity计算scale) -> Bitmap.createBitmap()
 
 
### 给我谈谈图片压缩。
[参考：浅谈android中加载高清大图及图片压缩方式(二)](https://blog.csdn.net/u013064109/article/details/51415879)


### LruCache & DiskLruCache原理。
[参考：LruCache——解决OOM的利器](https://blog.csdn.net/wzhseu/article/details/81745799)

[参考：LruCache 和 DiskLruCache 的使用以及原理分析](https://blog.csdn.net/qq_15893929/article/details/85229364)


### 说说你平常会使用的一些第三方图片加载库,最好给我谈谈它的原理。
[参考：Glide实现原理解析](https://blog.csdn.net/hxl517116279/article/details/99639520)

[参考：Android图片加载框架之(Glide和Picasso的区别，Glide的简单使用)](https://blog.csdn.net/jing_80/article/details/81020718)


### 如果让你设计一个图片加载库，你会如何设计？
[参考：Android 框架练成 教你打造高效的图片加载框架](https://blog.csdn.net/lmj623565791/article/details/41874561)

[参考：如何设计一个图片加载框架](https://blog.csdn.net/u012124438/article/details/113797946)


### WebView会导致内存泄露吗？原因是什么？解决方式有哪些？
[参考：腾讯Android高工灵魂三问：WebView会存在内存泄漏吗？为什么？泄漏了怎么解决？](https://blog.csdn.net/zzz777qqq/article/details/110482537)


### 说说WebSettings & WebViewClient & WebChromeClient这三个类的作用 & 用法。
[参考：Android：最全面的 Webview 详解](https://blog.csdn.net/carson_ho/article/details/52693322)

WebSettings类作用：对WebView进行配置和管理

WebViewClient类作用：处理各种通知 & 请求事件

WebChromeClient类作用：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。


### 如何提高原生的WebView加载速度？
[参考：Android:WebView提升首次加载速度](https://blog.csdn.net/qq_23575795/article/details/83473418)


### 谈谈你对webView工作机制的认识,你在项目中优化过它吗？说说是从哪些方面着手的？
[参考：Android：手把手教你构建 全面的WebView 缓存机制 & 资源加载方案](https://www.jianshu.com/p/5e7075f4875f)


### 什么是ViewPager?说说它的那些适配器。(校招&实习)
[参考：ViewPager概述](https://blog.csdn.net/qq_35255047/article/details/75646364)


### 你了解ViewPager2吗？和ViewPager 1有哪些区别？
[参考：探索取代ViewPager的ViewPager2](https://blog.csdn.net/qq_39872430/article/details/104023554)

1.ViewPager2 API最大的变化是它现在使用RecyclerView。

2.使用 ViewPager2 需要迁移到 AndroidX，因为android.support库中不支持ViewPager2。

3.FragmentStateAdapter 替换 FragmentStatePagerAdapter

4.RecyclerView.Adapter 替代 PagerAdapter

5.registerOnPageChangeCallback 替换 addPageChangeListener

关于为什么使用ViewPager2而不是继续使用ViewPager的原因，请看下面的ViewPager2变化。

ViewPager2 基于 RecyclerView

允许垂直分页，说明也支持LayoutManager，在源码中也可以看到。

支持RTL布局，国内一般适配的很少，到目前为止我还没有见过，可谁知道产品的想法呢。

改善数据更改通知

支持使用代码滚动ViewPager2

引入了MarginPageTransformer 以提供在页面之间创建空间的功能。

引入CompositePageTransformer 来组合多个Page Transformer。

getCurrentItem() 和 getCurrentItem() 方法的隐式使用

由于RecyclerView包含ViewPager2的一部分，因此支持DiffUtil

引入ItemDecorator可以对行进行操作，和RecyclerView一致


### ViewPager + Fragment结合使用存在的内存泄漏的原因是什么？如何解决？
[参考：viewpager + fragment+FragmentStatePagerAdapter中用List存放多个Fragment 造成的内存泄漏](https://blog.csdn.net/k_hello/article/details/82996162)


### 如果我在一个设置了点击事件的TextView中dispatchTouchEvent方法强制返回ture或者false会发生什么？
[参考：Android学习之路--View--事件传递机制](https://blog.csdn.net/jiayi_fly/article/details/54098367)


### viewGroup 怎么知道view有没有消费事件
[参考：Android ViewGroup/View 事件分发机制详解](https://blog.csdn.net/WALLEZhe/article/details/51737034)

onTouchEvent 返回true时，表示事件被消费掉了。一旦事件被消费掉了，其他父元素的onTouchEvent方法都不会被调用。
如果没有人消耗事件，则最终当前Activity会消耗掉。则下次的MOVE、UP事件都不会再传下去了。


### 内存泄漏有哪些？怎么排查
[参考：Android内存泄漏检测和定位](https://www.jianshu.com/p/1972a6d1f0fc)


### android的handler机制
[参考：Android Handler异步通信：深入详解Handler机制源码](https://blog.csdn.net/carson_ho/article/details/80388560)


### android的anr机制
[参考：Android ANR机制的原理以及问题分析（一）](https://blog.csdn.net/wcsbhwy/article/details/108704392)


### android冷启动优化
[参考：Android性能优化之冷启动优化](https://blog.csdn.net/dfskhgalshgkajghljgh/article/details/100084219)


### android弱网优化
[参考：Android 网络性能优化（4）弱网优化](https://blog.csdn.net/rikkatheworld/article/details/109050268)


### android长图片加载怎么实现
[参考：Android中如何加载大图片和长图片](https://blog.csdn.net/haoxuhong/article/details/80879982)


### 一个app发布一个版本后，发现变卡了，你如何复现？如何得知某个地方变卡，如何得知用户在某行代码变卡
[参考：Android性能优化-检测App卡顿](https://www.jianshu.com/p/9e8f88eac490?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)

[参考：Android卡顿检测及优化](https://blog.csdn.net/u013309870/article/details/106801022)


### android 动画机制有哪些？
[参考：Android动画机制及其使用](https://blog.csdn.net/qq_15128547/article/details/56496625)


### lottie的原理
[参考：Android 之 Lottie 实现炫酷动画背后的原理](https://blog.csdn.net/singwhatiwanna/article/details/90687150)


### 直播中的动画要怎么做？要做成动态的，比如礼物是可以配置的？
[参考：直播App中Android酷炫礼物动画实现方案（上篇）](https://blog.csdn.net/urDFmQCUL2/article/details/78349700)

[SVGA官网](http://svga.io/)

[SVGAPlayer-Android库](https://github.com/svga/SVGAPlayer-Android)


### HashMap 1.7，1.8的差异，1.8中什么情况下转换为红黑树，构造函数中参数代表的意思。
[参考：美团面试题：Hashmap的结构，1.7和1.8有哪些区别，史上最深入的分析](https://blog.csdn.net/qq_36520235/article/details/82417949)

（1）JDK1.7用的是头插法，而JDK1.8及之后使用的都是尾插法，那么他们为什么要这样做呢？因为JDK1.7是用单链表进行的纵向延伸，
当采用头插法时会容易出现逆序且环形链表死循环问题。但是在JDK1.8之后是因为加入了红黑树使用尾插法，能够避免出现逆序且链表死循环的问题。

（2）扩容后数据存储位置的计算方式也不一样：1. 在JDK1.7的时候是直接用hash值和需要扩容的二进制数进行&（这里就是为什
么扩容的时候为啥一定必须是2的多少次幂的原因所在，因为如果只有2的n次幂的情况时最后一位二进制数才一定是1，这样能最大
程度减少hash碰撞）（hash值 & length-1）

（3）在JDK1.7的时候是先扩容后插入的，这样就会导致无论这一次插入是不是发生hash冲突都需要进行扩容，如果这次插入的并
没有发生Hash冲突的话，那么就会造成一次无效扩容，但是在1.8的时候是先插入再扩容的，优点其实是因为为了减少这一次无效的
扩容，原因就是如果这次插入没有发生Hash冲突的话，那么其实就不会造成扩容，但是在1.7的时候就会急造成扩容

（4）而在JDK1.8的时候直接用了JDK1.7的时候计算的规律，也就是扩容前的原始位置+扩容的大小值=JDK1.8的计算方式，而不再
是JDK1.7的那种异或的方法。但是这种方式就相当于只需要判断Hash值的新增参与运算的位是0还是1就直接迅速计算出了扩容后的储存方式。


### 抽象类与接口的区别
[参考：Java中抽象类与接口有什么区别](https://blog.csdn.net/xiaoxu9522/article/details/90748914)

抽象类和接口都不能直接实例化，如果要实例化，抽象类变量必须指向实现所有抽象方法的子类对象，接口变量必须指向实现所有接口方法的类对象。

抽象类要被子类继承，接口要被类实现。

接口里定义的变量只能是公共的静态的常量，抽象类中的变量是普通变量。

抽象类里可以没有抽象方法。

接口可以被类多实现（被其他接口多继承），抽象类只能被单继承。

接口中没有 this 指针，没有构造函数，不能拥有实例字段（实例变量）或实例方法。

抽象类不能在Java 8 的 lambda 表达式中使用。


### 分别讲讲 final，static，synchronized 关键字可以修饰什么，以及修饰后的作用？
[参考：this、super、final、static、synchronized 关键字可以修饰什么，以及修饰后的作用？](https://blog.csdn.net/qq_38127722/article/details/107013476)

1.对于基本数据类型的数据而言，final修饰符表示，该数据不会被修改。
对于非基本类型的对象引用而言，final修饰符所限定的引用恒定不变。一旦引用被初始化指向一个对象，就无法对其改变以指向另
一个对象（然而，对象自身却是可以被修改的。Java并未提供任何使“对象”恒定不变的途径。这一限制同样适用于数组，它也是对象）。

2.final方法—不能被重载

3.final类–不能被继承，final类中的方法默认为final

1、static变量：按照是否静态的对类成员变量进行分类可分两种：一种是被static修饰的变量，叫静态变量或类变量；另一种是没有被static修饰的变量，叫实例变量。两者的区别是：
对于静态变量在内存中只有一个拷贝（节省内存），JVM只为静态分配一次内存，在加载类的过程中完成静态变量的内存分配，可用类名直接访问（方便），当然也可以通过对象来访问（但是这是不推荐的）。
对于实例变量，没创建一个实例，就会为实例变量分配一次内存，实例变量可以在内存中有多个拷贝，互不影响（灵活）。

2、静态方法：静态方法可以直接通过类名调用，任何的实例也都可以调用，因此静态方法中不能用this和super关键字，不能直接访问所属类的实
例变量和实例方法(就是不带static的成员变量和成员成员方法)，只能访问所属类的静态成员变量和成员方法。因为实例成员与特
定的对象关联！这个需要去理解，想明白其中的道理，不是记忆！！！
因为static方法独立于任何实例，因此static方法必须被实现，而不能是抽象的abstract。

3、static代码块：static代码块也叫静态代码块，是在类中独立于类成员的static语句块，可以有多个，位置可以随便放，它不在任何的方法体内，
JVM加载类时会执行这些静态的代码块，如果static代码块有多个，JVM将按照它们在类中出现的先后顺序依次执行它们，每个代码块只会被执行一次。

4、类加载：JVM在第一次使用一个类时，会到classpath所指定的路径里去找这个类所对应的字节码文件，并读进JVM保存起来，这个过程称之为类加载。
可见，无论是变量，方法，还是代码块，只要用static修饰，就是在类被加载时就已经"准备好了",也就是可以被使用或者已经被执行。
都可以脱离对象而执行。反之，如果没有static，则必须通过对象来访问。

注意：声明为static的变量实质上就是全局变量。当声明一个对象时，并不产生static变量的拷贝，而是该类所有的实例变量共用同一个static变量，
例如：声明一个static的变量count作为new一个类实例的计数。

声明为static的方法有以下几条限制：它们仅能调用其他的static 方法。

它们只能访问static数据。

它们不能以任何方式引用this 或super。

static方法与覆盖：静态方法只能被隐藏，不能被覆盖，隐藏表明还存在，还会起作用–子类隐藏父类的静态方法，仍会执行父类的静态方法.

synchronized 是java语言关键字，当它用来修饰一个方法或者一个代码块的时候，能够保证在同一时刻最多只有一个线程执行该段代码。
synchronized 关键字，它包括两种用法：synchronized 方法和 synchronized 块。

可以修饰哪些内容：一、 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象；

二、修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象；

三、修饰一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象；

四、修饰一个类，其作用的范围是synchronized后面括号括起来的部分，作用的对象是这个类的所有对象。

synchonized(this)和synchonized(object)区别：其实并没有很大的区别，synchonized(object)本身就包含synchonized(this)这种情况，使用的场景都是对一个代码块进行加锁，
效率比直接在方法名上加synchonized高一些（下面分析），唯一的区别就是对象的不同。


### Java 中深拷贝与浅拷贝的区别？
[参考：Java深入理解深拷贝和浅拷贝区别](https://blog.csdn.net/riemann_/article/details/87217229)

注：深拷贝和浅拷贝都是对象拷贝

浅拷贝：

被复制对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用仍然指向原来的对象。即对象的浅拷贝会对“主”对象进行拷贝
，但不会复制主对象里面的对象。”里面的对象“会在原来的对象和它的副本之间共享。

简而言之，浅拷贝仅仅复制所考虑的对象，而不复制它所引用的对象。

深拷贝：

深拷贝是一个整个独立的对象拷贝，深拷贝会拷贝所有的属性,并拷贝属性指向的动态分配的内存。当对象和它所引用的对象一起拷贝时即发生深拷贝。
深拷贝相比于浅拷贝速度较慢并且花销较大。

简而言之，深拷贝把要复制的对象所引用的对象都复制了一遍。

### 谈谈List,Set,Map的区别？
[参考：java中list，set，map集合的区别，及面试要点](https://blog.csdn.net/qq_30225725/article/details/88020157)

List：

1.可以允许重复的对象。

2.可以插入多个null元素。

3.是一个有序容器，保持了每个元素的插入顺序，输出的顺序就是插入的顺序。

4.常用的实现类有 ArrayList、LinkedList 和 Vector。ArrayList 最为流行，它提供了使用索引的随意访问，而 LinkedList 则对于经常需要从 List 中添加或删除元素的场合更为合适。

Set：

1.不允许重复对象

2. 无序容器，你无法保证每个元素的存储顺序，TreeSet通过 Comparator 或者 Comparable 维护了一个排序顺序。

3. 只允许一个 null 元素

4.Set 接口最流行的几个实现类是 HashSet、LinkedHashSet 以及 TreeSet。最流行的是基于 HashMap 实现的 HashSet；
TreeSet 还实现了 SortedSet 接口，因此 TreeSet 是一个根据其 compare() 和 compareTo() 的定义进行排序的有序容器。而且可以重复

Map:

1.Map不是collection的子接口或者实现类。Map是一个接口。

2.Map 的 每个 Entry 都持有两个对象，也就是一个键一个值，Map 可能会持有相同的值对象但键对象必须是唯一的。

3. TreeMap 也通过 Comparator 或者 Comparable 维护了一个排序顺序。

4. Map 里你可以拥有随意个 null 值但最多只能有一个 null 键。

5.Map 接口最流行的几个实现类是 HashMap、LinkedHashMap、Hashtable 和 TreeMap。（HashMap、TreeMap最常用）


### activity启动模式
[参考：细谈Activity四种启动模式](https://blog.csdn.net/zy_jibai/article/details/80587083)

1.默认启动模式standard：该模式可以被设定，不在manifest设定时候，Activity的默认模式就是standard。在该模式下，
启动的Activity会依照启动顺序被依次压入Task中

2.栈顶复用模式singleTop：在该模式下，如果栈顶Activity为我们要新建的Activity（目标Activity），那么就不会重复创建新的Activity。
    
3.栈内复用模式singleTask：与singleTop模式相似，只不过singleTop模式是只是针对栈顶的元素，而singleTask模式下，
如果task栈内存在目标Activity实例，则：
将task内的对应Activity实例之上的所有Activity弹出栈。
将对应Activity置于栈顶，获得焦点。

4.全局唯一模式singleInstance：在该模式下，我们会为目标Activity分配一个新的affinity，并创建一个新的Task栈，将目标
Activity放入新的Task，并让目标Activity获得焦点。新的Task有且只有这一个Activity实例。如果已经创建过目标Activity实例，
则不会创建新的Task，而是将以前创建过的Activity唤醒（对应Task设为Foreground状态）

### 异步同步
[参考：Java 异步同步](https://blog.csdn.net/weixin_38019699/article/details/104888402)

异步：多个线程同时对共享资源进行操作，在操作数据时，互相之间不需要等待。提高执行效率，降低了资源的安全性。

同步：多个线程在操作共享资源时，同一时刻只能有一个线程在操作相当于独占资源，另外的线程必须等待。牺牲了程序的执行效率，提高了资源的安全性。

### Java中各类型占几个字节
[参考：JAVA中的几种基本数据类型是什么，各自占用多少字节](https://www.cnblogs.com/banma/p/12787268.html)

byte=1字节=8bit

short=2字节

int=4字节

long=8字节

float=4字节

double=8字节

char=2字节

名词解析：

bit : 位，计算机存储数据的最小单元，二进制数中的一个位数。

byte : 字节，计算机存储数据的基本单位，一个字节由8位二进制数组成。通常一个汉字占两个字节。


### boolen占几个字节 
[参考：Oracle官网](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html)

[参考：Java中boolean类型占几个字节，你知道吗？](https://blog.csdn.net/amoscn/article/details/97377833)

boolean数据类型只有两个可能的值：true和false。将此数据类型用于跟踪真/假条件的简单标志。这种数据类型代表一位信息，但它的“大小”并不是精确定义的。

boolean类型被编译为int类型，等于是说JVM里占用字节和int完全一样，int是4个字节，于是boolean也是4字节

boolean数组在Oracle的JVM中，编码为byte数组，每个boolean元素占用8位=1字节


### activity生命周期
[参考：基础总结篇之一：Activity生命周期](https://blog.csdn.net/liuhe688/article/details/6733407)

[参考：Activity生命周期的实践](https://blog.csdn.net/juer2017/article/details/86481665?spm=1001.2014.3001.5502)

1.启动Activity：系统会先调用onCreate方法，然后调用onStart方法，最后调用onResume，Activity进入运行状态。

2.当前Activity被其他Activity覆盖其上或被锁屏：系统会调用onPause方法，暂停当前Activity的执行。

3.当前Activity由被覆盖状态回到前台或解锁屏：系统会调用onResume方法，再次进入运行状态。

4.当前Activity转到新的Activity界面或按Home键回到主屏，自身退居后台：系统会先调用onPause方法，然后调用onStop方法，进入停滞状态。

5.用户后退回到此Activity：系统会先调用onRestart方法，然后调用onStart方法，最后调用onResume方法，再次进入运行状态。

6.当前Activity处于被覆盖状态或者后台不可见状态，即第2步和第4步，系统内存不足，杀死当前Activity，而后用户退回当前Activity：再次调用onCreate方法、onStart方法、onResume方法，进入运行状态。

7.用户退出当前Activity：系统先调用onPause方法，然后调用onStop方法，最后调用onDestory方法，结束当前Activity。


### Java 中使用多线程的方式有哪些
[参考：JAVA多线程实现的三种方式](https://blog.csdn.net/aboy123/article/details/38307539)

JAVA多线程实现方式主要有三种：继承Thread类、实现Runnable接口、使用ExecutorService、Callable、Future实现有返回结果的多线程。其中前两种方式线程执行完后都没有返回值，只有最后一种是带返回值的。

### 谈一谈java线程常见的几种锁？
[参考：浅谈多线程编程中常用的几种锁](https://blog.csdn.net/weixin_41507324/article/details/90814891)

一、synchronized 线程同步锁：

synchronized是java中的一个关键字，也就是说是java语言内置的特性。

如果一个代码块被synchronized 修饰了，当一个线程获取了对应的锁（同一把锁，synchronized中的参数相同就是同一把锁），
并执行该代码块时，其他线程便只能一直等待，等待获取锁的线程释放锁，而这里获取锁的线程释放锁只会有两种情况： 

1.获取锁的线程执行完了该代码块，然后线程释放对锁的占有。

2.线程执行发生异常，此时JVM会让线程自动释放锁。

二、Lock锁

Lock和 synchronized的区别：

1）、Lock不是java语言内置的， synchronized是java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问。

2）、Lock和 synchronized一个非常大的不同就是， synchronized不需要用户去手动释放锁，当 synchronized方法或者
 synchronized代码块执行完毕后或者在发生异常时候，系统会自动让线程释放对锁的占用；而lock必须要用户去手动释放锁，
 就有可能导致出现死锁现象，因此使用Lock时需要在finally块中释放锁。（使用Lock一定要在try{}catch{}块中进行，在finally
 去释放锁unlock,否则在发生异常的时候，会造成死锁）。

3）、Lock可以让等待的线程相应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能都响应中断；

4）、Lock可以知道有没有成功获取锁，而synchronized却无法办到。

5）、Lock可以提高多个线程进行读操作的效率。

在性能上来说，如果竞争资源不激烈，两者的性能是差不多，而当竞争资源非常激烈时（即有大量的线程同时竞争），此时Lock的性能
要远远优于synchronized。所以说，在具体使用时要根据适当情况选择。


### 简述JVM中类的加载机制与加载过程
[参考：JVM类加载机制详解（一）JVM类加载过程](https://blog.csdn.net/zhangliangzi/article/details/51319033)

首先，在代码编译后，就会生成JVM（Java虚拟机）能够识别的二进制字节流文件（*.class）。而JVM把Class文件中的类描述数
据从文件加载到内存，并对数据进行校验、转换解析、初始化，使这些数据最终成为可以被JVM直接使用的Java类型，这个说来简单
但实际复杂的过程叫做JVM的类加载机制。


### 谈一谈Activity，View，Window三者的关系？
[参考：一篇文章看明白 Activity 与 Window 与 View 之间的关系](https://blog.csdn.net/freekiteyu/article/details/79408969)

Window 是 Android 中窗口的宏观定义，主要是管理 View 的创建，以及与 ViewRootImpl 的交互，将 Activity 与 View 解耦。

Activity 与 PhoneWindow 与 DecorView 之间什么关系？

一个 Activity 对应一个 Window 也就是 PhoneWindow，一个 PhoneWindow 持有一个 DecorView 的实例，DecorView 本身是一个 FrameLayout。


### 什么是冒泡排序？如何优化？
[参考：java代码之冒泡排序及其优化](https://blog.csdn.net/qq_42079455/article/details/88229883)

1、交换排序的基本思想：两两比较待排序记录的关键字，如果发现两个记录的次序相反则进行交换，知道所有记录都没有反序为止。

2、常见的交换排序：冒泡排序、快速排序
3、冒泡排序的基本思想：冒泡排序属于比较简单的排序，以非递减为例，依次遍历数组，发现a[i]>a[i+1}的情况，swap(a[i],a[i+1])，
直到没有逆序的数据，完成排序，可以用两个for循环嵌套实现，外层控制遍历次数，内层用来实现交换，也可以用一个boolean类
型变量来控制是否有交换发生，如果没有交换，表明已经正序，可以直接输出。

冒泡排序核心代码:
```
public static int[] bubbleSort1(int[] a) {
		int i, j;
		for (i = 0; i < a.length; i++) {// 表示n次排序过程。
			for (j = 1; j < a.length - i; j++) {
				if (a[j - 1] > a[j]) {// 前面的数字大于后面的数字就交换
					// 交换a[j-1]和a[j]
					int temp;
					temp = a[j - 1];
					a[j - 1] = a[j];
					a[j] = temp;
				}
			}
		}
		return a;
	}
```

优化：这里设置一个标志flag，如果这一趟发生了交换，则为true，否则为false。明显如果有一趟没有发生交换，说明排序已经完成。
```
public static int[] bubbleSort2(int[] a) {
		int j, k = a.length;
		boolean flag = true;// 发生了交换就为true, 没发生就为false，第一次判断时必须标志位true。
		while (flag) {
			flag = false;// 每次开始排序前，都设置flag为未排序过
			for (j = 1; j < k; j++) {
				if (a[j - 1] > a[j]) {// 前面的数字大于后面的数字就交换
					// 交换a[j-1]和a[j]
					int temp;
					temp = a[j - 1];
					a[j - 1] = a[j];
					a[j] = temp;

					// 表示交换过数据;
					flag = true;
				}
			}
			k--;// 减小一次排序的尾边界
		} // end while
		return a;
	}
```

再进一步做优化：
```
public static int[] bubbleSort3(int[] a) {
		int j, k;
		int flag = a.length;// flag来记录最后交换的位置，也就是排序的尾边界

		while (flag > 0) {// 排序未结束标志
			k = flag; // k 来记录遍历的尾边界
			flag = 0;

			for (j = 1; j < k; j++) {
				if (a[j - 1] > a[j]) {// 前面的数字大于后面的数字就交换
					// 交换a[j-1]和a[j]
					int temp;
					temp = a[j - 1];
					a[j - 1] = a[j];
					a[j] = temp;

					// 表示交换过数据;
					flag = j;// 记录最新的尾边界.
				}
			}
		}
		return a;
	}
```


### anr定位分析处理
[参考：ANR产生的原因及其定位分析](https://blog.csdn.net/a820703048/article/details/74907259)


### 死锁产生场景
[参考：死锁的成因、场景以及死锁的避免](https://blog.csdn.net/java_zyq/article/details/89640211)

锁顺序死锁：线程A和线程B都继续执行，此时线程A需要o1锁才能继续往下执行。此时线程B需要o2锁才能继续往下执行。
但是：线程A的o1锁并没有释放，线程B的o2锁也没有释放。
所以他们都只能等待，而这种等待是无期限的-->永久等待-->死锁

动态锁顺序死锁：经典场景引入：银行转账问题：线程 A 从 X 账户向 Y 账户转账，线程 B 从账户 Y 向账户 X 转账，那么就会发生死锁。

协作对象之间发生死锁

避免死锁可以概括成三种方法：

1、固定加锁的顺序(针对锁顺序死锁)

2、开放调用(针对对象之间协作造成的死锁)

3、使用定时锁-->tryLock()
如果等待获取锁时间超时，则抛出异常而不是一直等待！


### 临时变量存储位置(堆栈相关知识)
[参考：内存分配及变量存储位置（堆、栈、方法区常量池、方法区静态区）](https://www.cnblogs.com/protected/p/6419217.html)

程序运行时，有六个地方都可以保存数据：

1、 寄存器：这是最快的保存区域，因为它位于和其他所有保存方式不同的地方：处理器内部。然而，寄存器的数量十分有限，所以
寄存器是根据需要由编译器分配。我们对此没有直接的控制权，也不可能在自己的程序里找到寄存器存在的任何踪迹。

2、 堆栈：存放基本类型的数据和对象的引用，但对象本身不存放在栈中，而是存放在堆中（new 出来的对象）。驻留于常规RAM（随机访问存储器）
区域。但可通过它的“堆栈指针”获得处理的直接支持。堆栈指针若向下移，会创建新的内存；若向上移，则会释放那些内存。这是一
种特别快、特别有效的数据保存方式，仅次于寄存器。创建程序时，java编译器必须准确地知道堆栈内保存的所有数据的“长度”以
及“存在时间”。这是由于它必须生成相应的代码，以便向上和向下移动指针。这一限制无疑影响了程序的灵活性，所以尽管有些java
数据要保存在堆栈里——特别是对象句柄，但java对象并不放到其中。

3、 堆：存放用new产生的数据。一种常规用途的内存池（也在RAM区域），其中保存了java对象。和堆栈不同：“内存堆”或“堆”最
吸引人的地方在于编译器不必知道要从堆里分配多少存储空间，也不必知道存储的数据要在堆里停留多长的时间。因此，用堆保存数
据时会得到更大的灵活性。要求创建一个对象时，只需用new命令编制相碰的代码即可。执行这些代码时，会在堆里自动进行数据的
保存。当然，为达到这种灵活性，必然会付出一定的代价：在堆里分配存储空间时会花掉更长的时间

4、 静态域：存放在对象中用static定义的静态成员。这儿的“静态”是指“位于固定位置”。程序运行期间，静态存储的数据将随时
等候调用。可用static关键字指出一个对象的特定元素是静态的。但java对象本身永远都不会置入静态存储空间。

5、 常量池：存放常量。常数值通常直接置于程序代码内部。这样做是安全的。因为它们永远都不会改变，有的常数需要严格地保护，
所以可考虑将它们置入只读存储器（ROM）。

6、 非RAM存储：硬盘等永久存储空间。若数据完全独立于一个程序之外，则程序不运行时仍可存在，并在程序的控制范围之外。
其中两个最主要的例子便是“流式对象”和“固定对象”。对于流式对象，对象会变成字节流，通常会发给另一台机器，而对于固定对象，
对象保存在磁盘中。即使程序中止运行，它们仍可保持自己的状态不变。对于这些类型的数据存储，一个特别有用的技艺就是它们能
存在于其他媒体中，一旦需要，甚至能将它们恢复成普通的、基于RAM的对象。


### 静态变量是否会被销毁
[参考：静态变量什么时候会被回收](https://blog.csdn.net/yogkin/article/details/53404855)

静态变量是在类被load的时候分配内存的，并且存在于方法区。当类被卸载的时候，静态变量被销毁。


### view分发机制
[参考：一文读懂Android View事件分发机制](https://www.jianshu.com/p/238d1b753e64)


### 用什么Map可以保证线程安全，为什么？ConcurrentHashMap为什么能保证线程安全？1.7和1.8原理有什么差异。
[参考：线程安全的Map——ConcurrentHashMap如何保证线程安全？](https://blog.csdn.net/Hushboom/article/details/107340190)

我们知道，HashMap是线程不安全的，在ConcurrentHashMap出现之前，JDK用HashTable来实现线程安全，但HashTable是将整
个哈希表锁住，采用sychronized同步方法，所以性能很低；

JDK1.7中 Segment是ReentrantLock的子类，ConcurrentHashMap将数据分别放到多个Segment中，默认16个，每一个Segment
中又包含了多个HashEntry列表数组

不同Segement之间是异步，这样对一个段的修改只会对该段加锁，不会影响到其他段的操作；
Segement初始化为16之后不可再扩容；结构:16Segment+哈希表；

JDK1.8 ConcurrentHashMap锁进一步细化，去除segment，结构类似于HashMap.哈希表+红黑树，锁当前桶的头结点，锁的个数
进一步提升(锁个数会随着哈希表扩容而增加)，支持的并发线程数进一步提升；
使用CAS+synchronized来保证线程安全 ；

ConcurrentHashMap在jdk1.8中取消segments字段，直接采用transient volatile Node<K,V>[] table保存数据，采用table
数组元素（链表的首个元素或者红黑色的根节点）作为锁，从而实现了对每一行数据进行加锁，减少并发冲突的概率；


### 有多少种单例模式，枚举算不算单例，单例模式中不用volatile会导致什么问题？volatile特性是什么？为什么android中不推荐使用枚举。
[参考：你知道有多少种方式实现单例模式？](https://blog.csdn.net/Rain_9155/article/details/103318029)

[参考：Android中是否推荐使用枚举Enum](https://www.shuzhiduo.com/A/E35p4BAK5v/)

1、饿汉方式

2、静态内部类方式

3、懒汉模式（线程安全）

4、Double Check Lock（DCL）双重检查锁定

5、枚举模式	

6、容器方式 

针对DCL的错误，有两种解决办法，第一种办法是使用Volatile关键字，因为Volatile会禁止指令重排序，保证对象实例化过程按1、2、3步骤进行；第二种办法是再加一个局部变量做一层缓冲

Android官网不建议使用enums，占用内存多


### Glide中怎么实现图片的加载进度条，Glide的缓存是怎么设计的？为什么要用弱引用。
[参考：Android图片加载框架最全解析（七），实现带进度的Glide图片加载功能](https://blog.csdn.net/guolin_blog/article/details/78357251)

[参考：Glide 缓存机制解析(为啥使用弱引用)](https://blog.csdn.net/u011418943/article/details/107026881)

我们知道，glide 是用弱引用缓存当前的活跃资源的；为啥不直接从 LruCache 取呢？原因猜测如下：

这样可以保护当前使用的资源不会被 LruCache 算法回收

使用弱引用，即可以缓存正在使用的强引用资源，又不阻碍系统需要回收的无引用资源。


### implementation 和 api的区别是什么？
[参考：implementation和api的区别](https://blog.csdn.net/geyuecang/article/details/105270669)

implementation：

Gradle 会将依赖项添加到编译类路径，并将依赖项打包到构建输出。不过，当您的模块配置 implementation 依赖项时，会让 
Gradle 了解您不希望该模块在编译时将该依赖项泄露给其他模块。也就是说，其他模块只有在运行时才能使用该依赖项。
使用此依赖项配置代替 api 或 compile（已弃用）可以显著缩短构建时间，因为这样可以减少构建系统需要重新编译的模块数。
例如，如果 implementation 依赖项更改了其 API，Gradle 只会重新编译该依赖项以及直接依赖于它的模块。大多数应用和测试模块都应使用此配置。

api：

Gradle 会将依赖项添加到编译类路径和构建输出。当一个模块包含 api 依赖项时，会让 Gradle 了解该模块要以传递方式将该
依赖项导出到其他模块，以便这些模块在运行时和编译时都可以使用该依赖项。
此配置的行为类似于 compile（现已弃用），但使用它时应格外小心，只能对您需要以传递方式导出到其他上游消费者的依赖项使
用它。 这是因为，如果 api 依赖项更改了其外部 API，Gradle 会在编译时重新编译所有有权访问该依赖项的模块。 因此，拥
有大量的 api 依赖项会显著增加构建时间。除非要将依赖项的 API 公开给单独的模块，否则库模块应改用 implementation 依赖项。


### 事件分发的流程，以及怎么解决滑动冲突？
[参考：Android事件分发机制及滑动冲突解决方案](https://www.jianshu.com/p/d82f426ba8f7)


### 自定义View需要经历哪几个过程？
[参考：自定义View基础之View的绘制流程](https://www.jianshu.com/p/783b5dde1f41)

View的绘制流程是从ViewRoot的performTraversals开始的，它经过measure，layout，draw三个过程最终将View绘制出来。


### A 跳转到 B页面，两个页面的生命周期怎么走？什么情况下A的stop()不会执行。
[参考：android从A页面跳转到B页面生命周期方法执行顺序](https://blog.csdn.net/qq_15744297/article/details/79606167)

[参考：Activity A 调到B 两者的生命周期(二)](https://blog.csdn.net/u010904027/article/details/52470281)

从A页面跳转到B页面首先执行A页面的onPause方法，然后是B页面的onCreate方法、onStart方法、onResume方法，此时B页面可见了并且有了焦点，此时A页面的onStop方法执行。
 
如果B页面没有完全覆盖A页面（是弹窗的情况下）则A的onStop不会执行。


### Rxjava中map和flatMap有什么区别，都用过什么操作符。
[参考：Rxjava操作符之辩解map和flatmap的区别，以及应用场景](https://www.jianshu.com/p/c820afafd94b)

[参考：RxJava操作符大全](https://blog.csdn.net/maplejaw_/article/details/52396175)

共同点：

都是依赖FuncX(入参，返回值)进行转换（将一个类型依据程序逻辑转换成另一种类型，根据入参和返回值）

都能在转换后直接被subscribe

区别：

map返回的是结果集，flatmap返回的是包含结果集的Observable（返回结果不同）

map被订阅时每传递一个事件执行一次onNext方法，flatmap多用于多对多，一对多，再被转化为多个时，一般利用from/just进行
一一分发，被订阅时将所有数据传递完毕汇总到一个Observable然后一一执行onNext方法（执行顺序不同）>>>>(如单纯用于一对
一转换则和map相同)

map只能单一转换，单一只的是只能一对一进行转换，指一个对象可以转化为另一个对象但是不能转换成对象数组（map返回结果集
不能直接使用from/just再次进行事件分发，一旦转换成对象数组的话，再处理集合/数组的结果时需要利用for一一遍历取出，而
使用RxJava就是为了剔除这样的嵌套结构，使得整体的逻辑性更强。）

flatmap既可以单一转换也可以一对多/多对多转换，flatmap要求返回Observable，因此可以再内部进行from/just的再次事件
分发，一一取出单一对象（转换对象的能力不同）


### 哪个场景会发生内存泄露，内存泄露怎么检测，怎么解决。以及leak cannery内部原理是什么？为什么新版本的不需要在Application中注册了。
[参考：7种内存泄露场景和13种解决方案！](https://blog.csdn.net/sufu1065/article/details/116178604)

[参考：Android内存泄漏检测和定位](https://www.jianshu.com/p/1972a6d1f0fc)

[参考：LeakCanary原理解析，理解起来超简单！](https://blog.csdn.net/braintt/article/details/99685243)

[参考：LeakCanary 2 源码解析（一）为什么2.0不再需要在Application中手动初始化？](https://www.jianshu.com/p/dd89270c16b4)


### 手机适配问题怎么处理，都有什么方案。
比率rate=像素密度/160dpi

像素密度=根号下（像素宽的平方+像素高的平方）/手机尺寸（例如：手机宽1080px,高1920px,尺寸6.0英寸；像素密度=√1080²+1920²/6=367）

dp=px/比率rate

[参考：Android UI 设计规范—— px 转 dp](https://blog.csdn.net/pjingying/article/details/80045140)

[参考：Android手机的像素密度（dpi）计算](https://blog.csdn.net/dayexiaofan/article/details/84335288)

[参考：Android 屏幕适配：最全面的解决方案](https://www.jianshu.com/p/ec5a1a30694b)

[参考：骚年你的屏幕适配方式该升级了!-今日头条适配方案](https://www.jianshu.com/p/55e0fca23b4f)


### Android9 10 11 都更新了什么新特性，新版本中无法获取IMEI怎么处理。


数据序列化有那俩种方式，Serialization和Parcelable区别，如果持久化需要用哪一个？

组件化怎么分层，各个组件之间怎么通信。

怎防止程序崩溃，如果已经到了Thread.UncaughtExceptionHandler是否可以让程序继续运行。

Handler Looper mesaageQueue message 之间的关系。

子线程一定不能更新ui么？什么时候可以？什么时候不可以。检测逻辑是在什么阶段初始化的。

ANR发生的原理是什么， 怎么排查。

程序怎么保活。

说下路由ARoute的实现原理，怎么处理页面过多内存占用过大问题。

线程池都什么时候用，怎么创建，构造函数中的参数分别代表什么意思？

进程优先级。

反向输出字符串。

两个有序链表合并。

字符串移除多余空格，且技术单词首字符大写。

二叉树中和为某一值的路径。

本地广播和正常广播的区别。

二进制低位转高位。

字符串数组判重。

二叉树 判断是否为搜索二叉树。

Activity启动流程，Launcher启动流程。

1.thread wait sleep join 有什么区别，主要考察wait sleep相关区别。

2.mvvp 和mvp的区别，细节里怎么实现的双向绑定。

3.打渠道包 怎么写入渠道数据（app签名在哪）这里涉及V1,V2,V3签名差异。

4.rgb565和rgb888有什么区别，主要考察数据存储位。

5.自定义view，实现一个自定义View，且支持按下放大，松开缩小动画。

post get区别，三次握手，jvm的架构，各种viewgroup分别测量几次

Glide存储方式，每一个Activity缓存图片是否分开缓存，算法相关考察了按层遍历二叉树，并输出每层的最后一个节点，并且进行了4 -5 种变化，每种变化实现方式。

如果想统一项目的线程池，包括三方引入包的线程池，怎么处理。

如果想监控某一线程的耗时超过300毫秒的任务需要怎么操作。

如果项目出现未捕获的异常，怎么预操作可以防止Crash。

如果设计一个App的启动框架，需要考虑什么问题，怎么处理同步异步的加载任务调度。

glide 加载原理，怎么感知加载和暂停。

okhttp加载原理，怎么控制同步和异步任务。

mvp mvvm都在什么场景下使用。

一个int数组怎么判断是搜索二叉树的后续遍历。

1.Activity启动模式

2.Activity的启动过程

3.进程通讯

4.Android Binder之应用层总结与分析

5.进程保活方法

6.从源码了解handler looper ,messageQueue思路

7.handler如何实现延时发消息postdelay()

8.Android中为什么主线程不会因为Looper.loop()里的死循环卡死？

9.RxJava原理及如何封装使用

10.okhttp源码分析

11.retrofit源码分析

12.LeakCanary核心原理源码浅析

13.LruCache 使用及原理

14.ARouter原理

15.注解框架实现原理

16.Android 如何编写基于编译时注解的项目

17.RxJava2+Retrofit2+OkHttp3的基础、封装和项目中的使用

18.Rxjava2.0+Retrofit+Okhttp(封装使用)+MVP框架搭建

19.Android 插件化和热修复知识梳理

20.Android开发中比较常见的内存泄漏问题及解决办法

21.如何检测和定位Android内存泄漏

22.图片占据的内存算法

23.为什么图片需要用软引用，MVP模式中的view接口用弱引用

24.基于DataBinding与LiveData的MVVM实践

25.App稳定性优化

26.App启动速度优化

27.App内存优化

28.App绘制优化

29.App瘦身

30.网络优化

31.App电量优化

32.安卓的安全优化

33.为什么WebView加载会慢呢？

34.如何优化自定义View

### 加分项：
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