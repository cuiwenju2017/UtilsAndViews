[GitHub持续更新：（声明：本答案为个人收集与总结并非标准答案，仅供参考，如有错误还望指出，谢谢！如有重复可能是常问问题）](https://github.com/cuiwenju2017/UtilsAndViews/blob/master/Android%E5%BC%80%E5%8F%91%E9%9D%A2%E8%AF%95%E9%97%AE%E9%A2%98%E6%94%B6%E9%9B%86.md)


### ArrayList的使用，ArrayList使用过程中有没有遇到过坑。[参考：读了这一篇，让你少踩 ArrayList 的那些坑](https://www.cnblogs.com/fengzheng/p/12986513.html)
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


### GC算法[参考：几种常见GC算法介绍](https://blog.csdn.net/iva_brother/article/details/87870576)
常用的GC算法（引用计数法、标记-清除法、复制算法、标记-清除算法）


### 泛型的边际[参考：java泛型之泛型边界](https://blog.csdn.net/renwuqiangg/article/details/51296621)


### Handler中loop方法为什么不会导致线程卡死。[参考：为什么Looper中的Loop()方法不能导致主线程卡死?](https://blog.csdn.net/weixin_33738578/article/details/91412781)
1.耗时操作本身并不会导致主线程卡死, 导致主线程卡死的真正原因是耗时操作之后的触屏操作, 没有在规定的时间内被分发。

2.Looper 中的 loop()方法, 他的作用就是从消息队列MessageQueue 中不断地取消息, 然后将事件分发出去。


### 动画的原理[参考：Android三种动画实现原理及使用](https://blog.csdn.net/weixin_39001306/article/details/80614286)
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


### View的事件分发机制？[参考：一文读懂Android View事件分发机制](https://www.jianshu.com/p/238d1b753e64)


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
 一、原理
 
内存溢出（Out of memory）:系统会给每个APP分配内存也就是Heap size值，当APP所需要的内存大于了系统分配的内存，
就会造成内存溢出；通俗点就是10L桶只能装10L水，但是你却用来装11L的水，那就有1L的水就会溢出。

内存泄漏（Memory leak）:当一个对象不在使用了，本应该被垃圾回收器（JVM）回收，但是这个对象由于被其他正在使用
的对象所持有，造成无法被回收的结果，通俗点就是系统把一定的内存值A借给程序，但是系统却收不回完整的A值，那就是
内存泄漏。

二、两者的关系

内存泄漏是造成内存溢出（OOM）的主要原因，因为系统分配给每个程序的内存也就是Heap size的值都是有限的，当内存
泄漏到一定值的时候，最终会发生程序所需要的内存值加上泄漏值大于了系统所分配的内存额度，就是触发内存溢出。

三、危害

内存溢出：会触发Java.lang.OutOfMemoryError，造成程序崩溃

内存泄漏：过多的内存泄漏会造成OOM的发送，同样也会造成相关UI的卡顿现象

四、造成的原因以及处理

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

StringBuffer:

是一个可变对象,当对他进行修改的时候不会像String那样重新建立对象它只能通过构造函数来建立,

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

3. Handler

将Handler放入单独的类或者将Handler放入到静态内部类中（静态内部类不会持有外部类的引用）。如果想要在handler
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


### MeasureSpec的意义，怎样计算MeasureSpec；[参考：自定义View：Measure过程说明之MeasureSpec类详细讲解](https://blog.csdn.net/carson_ho/article/details/94545178)
意义：MeasureSpec是View的一个内部类，真正的身份就是帮助View完成测量功能。

计算：子View的MeasureSpec值根据子View的布局参数（LayoutParams）和父容器的MeasureSpec值计算得来的，具体计
算逻辑封装在getChildMeasureSpec()里。即：子view的大小由父view的MeasureSpec值 和 子view的LayoutParams属性共同决定。


### LayoutParams是什么？[参考：自定义控件知识储备-LayoutParams的那些事](https://blog.csdn.net/yisizhu/article/details/51582622)
LayoutParams，顾名思义，就是Layout Parameters :布局参数。


### 自定义View和自定义ViewGroup的区别。[参考：自定义View和自定义ViewGroup一步到位](https://blog.csdn.net/zxl1173558248/article/details/82901254)
ViewGroup是个View容器，它装纳child View并且负责把child View放入指定的位置。

自定义ViewGroup时必须要重写onLayout()方法（依次排列子view）,而自定义View没有子View，所以不需要onLayout()。


### View的绘制流程？ViewGroup的绘制流程？[参考：View和ViewGroup的基本绘制流程](https://blog.csdn.net/u011155781/article/details/52584044)
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


### 自定义View的measure时机；[参考：自定义控件View之onMeasure调用时机源码分析](https://blog.csdn.net/hty1053240123/article/details/76516426/)


### Scroller是怎么实现View的弹性滑动的？[参考：Android Scroller实现View弹性滑动完全解析](https://www.jianshu.com/p/9419262a342a)
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


### Glide使用过程中的坑[参考：Android glide使用过程中遇到的坑(进阶篇)](https://www.jianshu.com/p/deccde405e04)


### EventBus使用过程中的坑。[参考：EventBus使用过程中，遇到的问题点](https://blog.csdn.net/wolfking0608/article/details/70239105)


### 网络协议okhttp中的缓存机制[参考：OKHttp全解析系列（五） --OKHttp的缓存机制](https://www.jianshu.com/p/fb81207af121)


### dex加载流程[参考：Android动态加载Dex过程](https://blog.csdn.net/a2923790861/article/details/80539862)


### 组件化的原理。[参考：“终于懂了” 系列：Android组件化，全面掌握！ | 掘金技术征文-双节特别篇](https://juejin.cn/post/6881116198889586701)


### Fragment的生命周期管理过程中遇到的坑和解决办法。[参考：踩坑，Fragment使用遇到那些坑](https://blog.csdn.net/xiaoxiaocaizi123/article/details/79074501)


### 抽象类和接口的关系。[参考：Java 接口和抽象类区别](https://blog.csdn.net/xw13106209/article/details/6923556)


### databinding原理[参考：Android的DataBinding原理介绍](https://blog.csdn.net/xiangzhihong8/article/details/52688943)


### binder原理。[参考：简单理解Binder机制的原理](https://blog.csdn.net/augfun/article/details/82343249)


### 子线程中维护的looper，消息队列无消息时候的处理节省性能的处理方案。[参考：子线程中：new Handler需要做哪些准备？消息队列中无消息的时候，Looper的处理方案是什么？](https://blog.csdn.net/yichen97/article/details/106107874/)


### viewBinding的原理[参考：Android ViewBinding使用及原理](https://www.jianshu.com/p/431c040b6af8)
原理就是Google在那个用来编译的gradle插件中增加了新功能，当某个module开启ViewBinding功能后，编译的时候就去
扫描此模块下的layout文件，生成对应的binding类。


### Recycleview滑动怎么优化[参考：RecyclerView性能优化](https://www.jianshu.com/p/1853ff1e8de6?utm_campaign=maleskine)


### 一级缓存和二级缓存的区别[参考：一级缓存，二级缓存，分布式缓存和页面缓存](https://blog.csdn.net/androidxiaogang/article/details/52915905)


### LayoutManager原理[参考：自定义LayoutManager的详解及其使用](https://blog.csdn.net/lylodyf/article/details/52846602)


### 协程原理[参考：Kotlin 协程实现原理](https://blog.csdn.net/suyimin2010/article/details/91125803)


### rxjava原理[参考：RxJava原理解析一](https://www.jianshu.com/p/53b79866f58a)


### leakcanary[参考：LeakCanary 内存泄漏原理完全解析](https://www.jianshu.com/p/59106802b62c)


### 线程池[参考：深入理解线程和线程池（图文详解）](https://blog.csdn.net/weixin_40271838/article/details/79998327)


### 事件分发[参考：图解 Android 事件分发机制](https://www.jianshu.com/p/e99b5e8bd67b)


### 责任链模式[参考：一篇文章搞懂Java设计模式之责任链模式](https://blog.csdn.net/u012810020/article/details/71194853)


### 汽车加油问题[参考：汽车加油问题 贪心算法 Java（详细注释）](https://blog.csdn.net/qq_37294163/article/details/103277358)


### 如何实现快手和抖音的整屏滑动效果[参考：Android中模仿抖音的滑动RecycleView的实现](https://blog.csdn.net/weixin_36495794/article/details/80845103)


### 状态模式和策略模式的区别[参考：状态模式和策略模式的区别](https://blog.csdn.net/ruangong1203/article/details/52514919)


### sleep和wait的区别[参考：sleep和wait的区别](https://blog.csdn.net/qq_20009015/article/details/89980966)
1、sleep是Thread的静态方法，wait是Object的方法，任何对象实例都能调用。

2、sleep不会释放锁，它也不需要占用锁。wait会释放锁，但调用它的前提是当前线程占有锁(即代码要在synchronized中)。

3、它们都可以被interrupted方法中断。


### Parcelable与Serializable[参考：序列化Serializable和Parcelable的理解和区别](https://www.jianshu.com/p/a60b609ec7e7)
Serializable（Java自带）：
Serializable是序列化的意思，表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。

Parcelable（android 专用）：
除了Serializable之外，使用Parcelable也可以实现相同的效果，
不过不同于将对象进行序列化，Parcelable方式的实现原理是将一个完整的对象进行分解，
而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了。


### 深拷贝与浅拷贝[参考：彻底讲明白浅拷贝与深拷贝](https://www.jianshu.com/p/35d69cf24f1f)


### sync和Reenterlock遇到异常的区别
synchronized是关键字，ReentrantLock是类

ReentrantLock可以对获取锁的等待时间进行设置，避免死锁

ReentrantLock可以获取各种锁的信息

ReentrantLock可以灵活地实现多路通知

机制: sync操作Mark Word , lock调用Unsafe类的park()方法


### 可重入锁和不可重入锁[参考：可重入锁和不可重入锁](https://blog.csdn.net/u014473112/article/details/82998477)


### postInvalidate和Invalidate的区别[参考：简单讲下postInvalidate和Invalidate的区别](https://blog.csdn.net/codeyanbao/article/details/82694281)


### anr分类及原理[参考：安卓ANR问题1_ANR问题类型及产生原理](https://www.jianshu.com/p/ddfc4678067d)
ANR问题类型及产生原理

ANR(Application Not Responding):即应用无响应. 在日常使用安卓手机的过程中, 对最anr最直接的印象就是手机弹框
显示应用未响应. 选择继续等待或者关闭.如果应用程序的主线程在规定的时间内, 没有完成特定操作和事件, 就会发生ANR.

四种ANR类型

KeyDispatchTimeout : input事件在5S内没有处理完成发生ANR

ServiceTimeout : bind,create,start,unbind等操作,前台Service在20s内,后台Service在200s内没有处理完成发生ANR

BroadcastTimeout : BroadcastReceiver onReceiver处理事务时前台广播在10S内,后台广播在60s内 (应用程序应该避
免在BroadcastReceiver里做耗时的操作或计算。如果响应Intent广播需要执行一个耗时的动作的话，应用程序应该启动一个 Service).
没有处理完成发生ANR

ProcessContentProviderPublishTimedOutLocked : ContentProvider publish在10s内没有处理完成发生ANR其中第四种ANR发生的概率最小.

ANR产生的常见原因

主线程耗时操作,如复杂的layout,庞大的for循环,IO等. (实际APP开发时开发者会避开这种, 没有见到过这种问题产生ANR);
主线程被子线程同步锁block. (当子线程先拿着锁, 主线程等待这把锁的时候, 子线程太耗时. 导致主线程一直被阻塞, 从而ANR)
主线程被Binder对端阻塞
Binder被占满导致主线程无法和SystemServer通信
得不到系统资源(CPU/RAM/IO) (耗时的动画需要大量的计算工作，可能导致CPU负载过重.)


### viewmodel原理[参考：ViewModel 使用及原理解析](https://blog.csdn.net/xfhy_/article/details/88703853)


### livedata为何具有生命周期感知能力[参考：Android LiveData我的理解](https://blog.csdn.net/alcoholdi/article/details/97259805)


### viewmodel是如何解决内存泄漏问题的？能解决么[参考：使用ViewModel+Data Binding解决内存泄漏问题](https://www.jianshu.com/p/c8b3ce047de4?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)


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


### 一张图片占用的内存大小[参考：Android中一张图片占用的内存大小是如何计算的](https://www.cnblogs.com/dasusu/p/9789389.html)


### MVC,MMVM,MVP[参考：MVC、MVP、MVVM，我到底该怎么选？](https://blog.csdn.net/singwhatiwanna/article/details/80904132)


### 手写单例模式，并分析[参考：手写单例模式](https://blog.csdn.net/wand1995/article/details/97760451)
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


### 找到两个数组中的两个元素的和等于某个值[参考：快速找出一个数组中的两个数字，让这两个数字之和等于一个给定的值](https://blog.csdn.net/mimi9919/article/details/51335337/)


### StartService和BindService的生命周期[参考：startService和bindService的区别，生命周期以及使用场景](https://www.jianshu.com/p/73f10b6730c6)


### 求浮点数的平方根[参考：求一个浮点数的平方根——牛顿迭代法](https://blog.csdn.net/HuanCaoO/article/details/79860213)


### 工厂模式[参考：工厂模式](https://blog.csdn.net/qq_38238296/article/details/79841395)


### 建造者模式[参考：一篇文章就彻底弄懂建造者模式(Builder Pattern)](https://www.jianshu.com/p/3d1c9ffb0a28)


### 共享内存原理[参考：共享内存实现原理](https://blog.csdn.net/mw_nice/article/details/82888091)


### kotlin协程的四个dispatcher及区别[参考：Kotlin协程核心库分析-1 Dispatchers](https://blog.csdn.net/qfanmingyiq/article/details/105184822)


### window，decorview，ViewRootImpl的关系,面试官 想让你回答如何window和windowManager如何联系的。viewRootImpl是联系window和decorview的纽带
Activity
不负责控制视图，只是控制生命周期和处理事件，真正控制视图的是Window,Activity中含有一个Window，Window才是真
正代表一个窗口

Window
视图的承载器，内部持有DecorView,而DecorView是View的根布局，Window是一个抽象类，真正的实现类是PhoneWindow，
PhoneWindow有个内部类DecorView,通过其来加载R.layout.activity_main。Window通过WindowManager将DecorView加
载其中，并将DecorView交给ViewRoot，进行视图的绘制及其他交互

DecorView
是FrameLayout的子类，是android的根视图，相当于顶级View，一般来说内部包含竖直方向LinearLayout,在linearlayout
中含有三部分，上面是ViewStub,延迟加载的视图，中间是标题栏，下面是内容栏，就是我们熟悉的android.R.id.content

ViewRoot
所有View的绘制及事件分发交互都是通过它来进行的，有个真正的实现类ViewRootImpl,它是链接WindowManagerService
和DecorView的纽带，View的测量，布局，绘制都是通过它来实现的，所以我们常见的事件分发真正的过程是


### 进程间的通信方式[参考：Android进程间通信 - 几种方式的对比总结](https://blog.csdn.net/hzw2017/article/details/81275438)
常用有如下几种：

Bundle （四大组件间）

文件共享：
可参考Android进程通信 - 序列化Serialzable与Parcelable中的示例

AIDL （基于Binder）：
能自动生成Binder文件的工具，相当于工具。
Android进程通信 - AIDL的使用方法

Messenger（基于Binder）：
类似于Hnadler发消息用法
Android进程间通信 - Messenger的使用和理解

ContentProvider（基于Binder）：
Android进程间通信 - ContentProvider内容提供者

Socket（网络）：
Android进程间通信 - Socket使用（TCP、UDP）


### AIDL的用法[参考：Android AIDL使用详解](https://www.jianshu.com/p/29999c1a93cd)


### 使屏幕常亮[参考：Android让屏幕保持常亮的三种方法](https://blog.csdn.net/llljjlj/article/details/80631664)


### canvas的save和restore的作用[参考：canvas的save与restore方法的作用](https://blog.csdn.net/u014788227/article/details/52250208)
save：用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。 

restore：用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。


### vsync是如何生成的[参考：理解 VSync](https://blog.csdn.net/zhaizu/article/details/51882768)


### android中异步一般怎么实现[参考：Android实现异步的几种方法](https://blog.csdn.net/u011803341/article/details/52774867)


### android中内存泄漏发生的情况[参考：Android 中内存泄漏的原因和解决方案](https://www.jianshu.com/p/abee7c186bfa)


### 如何去获取view的宽高[参考：在activity中如何正确获取View的宽高](https://blog.csdn.net/zgh0711/article/details/70336354)


### 如何实现一个悬浮窗[参考：Android 悬浮窗功能的实现](https://blog.csdn.net/huangliniqng/article/details/95372212/)


### Android M之前与之后的权限变化[参考：Android 6.0 的权限管理变化](https://www.jianshu.com/p/f60260e98418)
运行时权限：Android 6.0 中不仅要在 AndroidManifest.xml 中声明权限，还在运行的时候增加了权限动态判断

涉及到的以下权限都会在运行时被判断：传感器、日历、摄像头、通讯录、地理位置、麦克风、电话、短信、存储空间、兼容方式

在 Android 6.0 中默认对 targetSdkVersion 小于 23 的应用申请的权限进行允许，但是在 targetSdkVersion 大于等
于 23 的应用中 ，就需要在代码上去进行动态的判断


### RxJava中map和flatmap的区别[参考：Rxjava map和flatMap区别](https://blog.csdn.net/new_abc/article/details/84318464)


### viewgroup是如何刷新的[参考：ViewGroup和View的理解和当子视图发生更新时通知viewgroup更新](https://blog.csdn.net/utilc/article/details/9838341)


### onMeasure,onlayout,onDraw分别起什么作用[参考：Android onMeasure，onLayout，onDraw的理解](https://blog.csdn.net/JimTrency/article/details/52837776)
测量——onMeasure()：决定View的大小

布局——onLayout()：决定View在ViewGroup中的位置

绘制——onDraw()：如何绘制这个View。


### 洗牌问题[参考：面试题之洗牌问题（java实现）](https://blog.csdn.net/qq_22993855/article/details/106932204)
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


### 面向对象原则[参考：面向对象的六大原则](https://blog.csdn.net/xiao_nian/article/details/87097110)
三大特性指的是封装、继承和多态；

六大原则指的是单一职责原则、开闭式原则、迪米特原则、里氏替换原则、依赖倒置原则以及接口隔离原则，其中，单一职
责原则是指一个类应该是一组相关性很高的函数和数据的封装，这是为了提高程序的内聚性，而其他五个原则是通过抽象来
实现的，目的是为了降低程序的耦合性以及提高可扩展性。


### 典型情况下的Activity生命周期？[参考：Activity的生命周期（典型和异常生命周期）](https://blog.csdn.net/daxiong25/article/details/80745697)


### Activity的启动模式 & 使用场景[参考：android activity 四大启动模式及使用场景](https://blog.csdn.net/u011337574/article/details/79979573)
android activity的启动模式有4种：分别是standard,singleTop,singleTask和singleInstance。在AndroidManifest.xml中，
通过标签的android:launchMode属性可以设置启动模式。


### 如何在任意位置关掉应用所有Activity？[参考：随时随地退出应用（结束之前所有的Activity）](https://blog.csdn.net/juer2017/article/details/78728634?spm=1001.2014.3001.5506)


### 如何在任意位置关掉指定的Activity？[参考：你知道吗？Android里如何关闭某个指定activity](https://blog.csdn.net/androidokk/article/details/96477182)


### Activity的启动流程(从源码角度解析)？[参考：Android源码分析-Activity的启动过程](https://blog.csdn.net/singwhatiwanna/article/details/18154335)


### Activity任务栈是什么？在项目中有用到它吗？说给我听听[参考：Activity启动模式与任务栈(Task)全面深入记录（上）](https://blog.csdn.net/javazejian/article/details/52071885)


### 广播的注册方式有哪些？[参考：Android中广播的使用](https://blog.csdn.net/daluyang/article/details/79702321)


### 广播的分类 & 特性 & 使用场景？[参考：Android：BroadcastRecevicer广播类型汇总](https://blog.csdn.net/carson_ho/article/details/53160580)
普通广播（Normal Broadcast）

系统广播（System Broadcast）

有序广播（Ordered Broadcast）

粘性广播（Sticky Broadcast）

App应用内广播（Local Broadcast）

使用场景：充电电池电量监听、时间变化监听等。


### 什么是内容提供者？[参考：Android开发之内容提供者——创建自己的ContentProvider(详解)](https://blog.csdn.net/dmk877/article/details/50387741)
首先我们必须要明白的是ContentProvider(内容提供者)是android中的四大组件之一，但是在一般的开发中，可能使用比
较少。ContentProvider为不同的软件之间数据共享，提供统一的接口。


### ContentProvider,ContentResolver,ContentObserver之间的关系[参考：Android ContentProvider、ContentResolver和ContentObserver的使用](https://blog.csdn.net/heqiangflytosky/article/details/31777363)


### 说说ContentProvider的权限管理[参考：ContentProvider权限设置](https://blog.csdn.net/robertcpp/article/details/51337891)


### 什么是Service?[参考：Android Service介绍和使用](https://blog.csdn.net/yh18668197127/article/details/86213380)
Service服务是Android四大组件之一,是一种程序后台运行的方案,用于不需要用户交互,长期运行的任务

Service并不是在单独进程中运行,也是运行在应用程序进程的主线程中,在执行具体耗时任务过程中要手动开启子线程,应用
程序进程被杀死,所有依赖该进程的服务也会停止运行


### 说说Service的生命周期[参考：Android Service生命周期浅析](https://www.jianshu.com/p/cc25fbb5c0b3)


### Service和Thread的区别？[参考：Android中Service和Thread的区别](https://blog.csdn.net/mynameishuangshuai/article/details/51821662)
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


### Android 5.0以上的隐式启动问题及其解决方案。[参考：Android 5.0之后隐式声明Intent 启动Service引发的问题](https://blog.csdn.net/l2show/article/details/47421961)


### Service保活方案[参考：Android进程保活（最新）带你浅析这几种可行性的保活方案](https://blog.csdn.net/qq_37199105/article/details/81224842)


### IntentService是什么 & 原理 & 使用场景 & 和Service的区别。
[参考：Android面试一天一题（1Day）IntentService作用是什么](https://blog.csdn.net/zxccxzzxz/article/details/52377191)
[参考：IntentService和Service区别](https://www.jianshu.com/p/5a32226d2ce0)


### 创建一个独立进程的Service应该怎样做？[参考：Android 通过Service 单独进程模仿离线推送](https://blog.csdn.net/yan8024/article/details/48790339)


### 子线程一定不能更新UI吗？[参考：Android：为什么子线程不能更新UI](https://www.jianshu.com/p/58c999d3ada7)
子线程可以在ViewRootImpl还没有被创建之前更新UI；

访问UI是没有加对象锁的，在子线程环境下更新UI，会造成不可预期的风险；

ViewRootImpl对象是在onResume方法回调之后才创建，那么就说明了为什么在生命周期的onCreate方法里，甚至是onResume
方法里都可以实现子线程更新UI，因为此时还没有创建ViewRootImpl对象，并不会进行是否为主线程的判断；


### Handler的原理[参考：android Handler机制原理解析（一篇就够，包你形象而深刻）](https://blog.csdn.net/luoyingxing/article/details/86500542)


### Handler导致的内存泄露是如何解决的？[参考：handler导致内存泄露的真正原因](https://blog.csdn.net/alex01550/article/details/82744191)
1.使用static 修饰的handler，但是一般会弱引用activity对象，因为要使用activity对象中的成员

2.单独定义handler，同样可以弱引用activity

3.使用内部类的handler，在onDestroy方法中removeCallbacksAndMessages


### 如何使用Handler让子线程和子线程通信？[参考：Handler实现子线程与子线程、主线程之间通信](https://blog.csdn.net/androidsj/article/details/79816866)


### HandlerThread是什么 & 原理 & 使用场景？[参考：handlerThread使用场景分析及源码解析](https://blog.csdn.net/nightcurtis/article/details/77676349)


### 一个线程能否创建多个Handler,Handler和Looper之间的对应关系？[参考：【面试】一个线程能否创建多个Handler，Handler跟Looper之间的对应关系 ？](https://blog.csdn.net/u013293125/article/details/105411971/)
一个线程能够创建多个Handler

Handler跟Looper没有对应关系，线程才跟Looper有对应关系，一个线程对应着一个Looper


### 为什么Android系统不建议子线程访问UI？[参考：Android：为什么子线程不能更新UI](https://www.jianshu.com/p/58c999d3ada7)
谷歌提出：“一定要在主线程更新UI”，实际是为了提高界面的效率和安全性，带来更好的流畅性；反推一下，假如允许多线
程更新UI，但是访问UI是没有加锁的，一旦多线程抢占了资源，那么界面将会乱套更新了，体验效果就不言而喻了；
所以在Android中规定必须在主线程更新UI。


### AsyncTask是什么？能解决什么问题[参考：AsyncTask](https://www.jianshu.com/p/6751aa65fcb6)
AsyncTask是什么：

AsyncTask是Android封装的一个轻量级的异步类，可以在线程池中执行异步任务，并可以将执行进度和结果传递给UI线程。

AsyncTask的内部封装了两个线程池(SerialExecutor、THREAD_POOL_EXECUTOR)和一个Handler。

其中SerialExecutor线程池用于任务的排队，让需要执行的多个耗时任务，按顺序排列，THREAD_POOL_EXECUTOR线程池
才真正地执行任务，Handler用于从工作线程切换到主线程。

AsyncTask出现的契机：

线程的创建和销毁都会有开销，如果在进程中频繁的创建和销毁线程，是不可取的。应该采用线程池，可以避免因为频繁创
建和销毁线程所带来的系统开销。


### 给我谈谈AsyncTask的三个泛型参数作用 & 它的一些方法作用。[参考：AsyncTask](https://www.jianshu.com/p/6751aa65fcb6)
``` 
public abstract class AsyncTask<Params, Progress, Result>{}
```
Params：开始异步任务执行时传入的参数类型；

Progress：异步任务执行过程中，返回下载进度值的类型；

Result：异步任务执行完成后，返回的结果类型；

如果AsyncTask确定不需要传递具体参数，那么这三个泛型参数可以用Void来代替。


### AsyncTask的原理[参考：AsyncTask的原理和缺点](https://blog.csdn.net/wjinhhua/article/details/60578133)


### Android中v4包下Fragment和app包下Fragment的区别是什么？
1.app包中的fragment在3.0以上才可以使用，最好使用兼容低版本的。

2.v4包下的可以兼容到 1.6版本。

3.两者都可以使用<fragment>标签，但是app包下的fragment所在的activity继承Activity即可。v4包下的fragment所在
的activity必须继承FragmentActivity，否则会报错。

4.getSupportFragmentManager（）对应的是 v4 ；getFragmentManager（）对应的是 app；


### Fragment的生命周期 & 请结合Activity的生命周期再一起说说。[参考：Activity与Fragment的生命周期](https://blog.csdn.net/zjclugger/article/details/10442335)


### Fragment如何进行懒加载。[参考：Androidx 下 Fragment 懒加载的新实现](https://www.jianshu.com/p/2201a107d5b5?utm_campaign=hugo)


### ViewPager + Fragment结合使用会出现内存泄漏吗 & 如何解决？[参考：记录ViewPager+fragment 内存泄露问题](https://blog.csdn.net/qq_32536991/article/details/88837924)


### Fragment如何和Activity进行通信 & Fragment之间如何进行通信？
[参考：Android：手把手教你 实现Activity 与 Fragment 相互通信（含Demo）](https://www.jianshu.com/p/825eb1f98c19)
[参考：【Android】Fragment之间数据传递的三种方式](https://www.jianshu.com/p/f87baad32662)


### 给我谈谈Fragment3种切换的方式以及区别 & 使用场景。[参考：Fragment生命周期-3种不同的切换方式生命周期变化](https://blog.csdn.net/luoxianli2011/article/details/106899777)
1.通过add、hide、show方式来切换fragment

当以这种方式进行Fragment1和Fragment2切换时，Fragment隐藏的时候并不走onDestroyView()，所有的现实也不会走onCreateView()，所有的view都会保存在内存。

2.使用replace()方法进行切换Fragment

通过replace方法进行替换的方式，Fragment都是进行了销毁、重建新Fragment的过程，相当于走了一整套的生命周期

3.使用ViewPager方式切换Fragment

当使用ViewPager进行Fragment切换时，所有的Fragment都会进行预加载。


### 说说Android中数据持久化的方式 & 使用场景
第一种： 使用SharedPreferences存储数据：

适用范围：保存少量的数据，且这些数据的格式非常简单：字符串型、基本类型的值。比如应用程序的各种配置信息
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

第二种： 文件存储数据：

可以在设备本身的存储设备或者外接的存储设备中创建用于保存数据的文件。同样在默认的状态下，文件是不能在不同的程序间共享。

写文件：调用Context.openFileOutput()方法根据指定的路径和文件名来创建文件，这个方法会返回一个FileOutputStream对象。

读取文件：调用Context.openFileInput()方法通过制定的路径和文件名来返回一个标准的Java FileInputStream对象。

第三种：SQLite存储数据：

SQLite Database数据库。Android对数据库的支持很好，它本身集成了SQLite数据库，每个应用都可以方便的使用它，或
者更确切的说，Android完全依赖于SQLite数据库，它所有的系统数据和用到的结构化数据都存储在数据库中。 它具有以
下优点： 

a. 效率出众，这是无可否认的 

b. 十分适合存储结构化数据 

c. 方便在不同的Activity，甚至不同的应用之间传递数据。　　

第四种：ContentProvider：

Android系统中能实现所有应用程序共享的一种数据存储方式，由于数据通常在各应用间的是互相私密的，所以此存储方式
较少使用，但是其又是必不可少的一种存储方式。例如音频，视频，图片和通讯录，一般都可以采用此种方式进行存储。
每个ContentProvider都会对外提供一个公共的URI（包装成Uri对象），如果应用程序有数据需要共享时，就需要使用ContentProvider
为这些数据定义一个URI，然后其他的应用程序就通过Content Provider传入这个URI来对数据进行操作。


### SharedPreference和MMKV的区别[参考：MMKV组件实现原理以及和SharedPreferences的比较（一）](https://blog.csdn.net/qq_39424143/article/details/95103783)
MMKV是基于mmap内存映射关系的key-value组件，底层序列化/反序列化使用protobuf实现。性能高，稳定性强。

SharedPreferences是Android提供的一种使用XML文件保存内容的机制，内部通过XML写入文件


### SQLite是线程安全的吗 & SharedPreference是线程安全的吗？
[参考：SQLite线程安全相关整理](https://blog.csdn.net/u013427969/article/details/90209917)


### 什么是三级缓存？[参考：android三级缓存详解](https://blog.csdn.net/u012138137/article/details/50921209?locationNum=3&fps=1)


### SharedPreference的apply和commit的区别[参考：SharedPreferences中的commit和apply方法](https://www.jianshu.com/p/c8d10357c939)
commit和apply虽然都是原子性操作，但是原子的操作不同，commit是原子提交到数据库，所以从提交数据到存在Disk中都是同步过程，中间不可打断。

而apply方法的原子操作是原子提交的内存中，而非数据库，所以在提交到内存中时不可打断，之后再异步提交数据到数据库中，因此也不会有相应的返回值。

所有commit提交是同步过程，效率会比apply异步提交的速度慢，但是apply没有返回值，永远无法知道存储是否失败。

在不关心提交结果是否成功的情况下，优先考虑apply方法。


### 谈谈你对SQLite事务的认识[参考：Sqlite事务理解](https://blog.csdn.net/qq_40111789/article/details/82670810)


### ListView和RecycyclerView的区别是什么？[参考：ListView和RecyclerView的使用和区别](https://blog.csdn.net/qq_39899087/article/details/86513378)
1）ListView布局单一，RecycleView可以根据LayoutManger有横向，瀑布和表格布局

2）自定义适配器中，ListView的适配器继承ArrayAdapter;RecycleView的适配器继承RecyclerAdapter,并将范类指定为子项对象类.ViewHolder(内部类)。

3）ListView优化需要自定义ViewHolder和判断convertView是否为null。 而RecyclerView是存在规定好的ViewHolder。

4）绑定事件的方式不同，ListView是在主方法中ListView对象的setOnItemClickListener方法；RecyclerView则是在子项具体的View中去注册事件。


### 分别讲讲你对ListView & RecyclerView的优化经验。
[参考：ListView的优化](https://www.jianshu.com/p/f0408a0f0610)
[参考：RecyclerView性能优化及高级使用](https://blog.csdn.net/smileiam/article/details/88396546)


### RecyclerView的回收复用机制[参考：基于滑动场景解析RecyclerView的回收复用机制原理](https://www.jianshu.com/p/9306b365da57)


### 说说你是如何给ListView & RecyclerView加上拉刷新 & 下拉加载更多机制
[参考：ListView增加下拉刷新，上拉加载更多](https://www.jianshu.com/p/adb2f8aa863c)
[参考：让RecyclerView上拉刷新下拉加载更多更简便易用](https://www.jianshu.com/)


### 谈谈你是如何对ListView & RecycleView进行局部刷新的？


### 你对Bitmap了解吗？它在内存中如何存在？


### 有关Bitmap导致OOM的原因知道吗？如何优化？


### 给我谈谈图片压缩。


### LruCache & DiskLruCache原理。


### 说说你平常会使用的一些第三方图片加载库,最好给我谈谈它的原理。


### 如果让你设计一个图片加载库，你会如何设计？


### 你知道Android中处理图片的一些库吗(OpenCv & GPUImage …)？


### WebView会导致内存泄露吗？原因是什么？解决方式有哪些？


### 说说WebSettings & WebViewClient & WebChromeClient这三个类的作用 & 用法。


### 如何提高原生的WebView加载速度？


### 谈谈你对webView工作机制的认识,你在项目中优化过它吗？说说是从哪些方面着手的？


### 什么是ViewPager?说说它的那些适配器。(校招&实习)


### 你了解ViewPager2吗？和ViewPager 1有哪些区别？


### ViewPager + Fragment结合使用存在的内存泄漏的原因是什么？如何解决？


### 如果我在一个设置了点击事件的TextView中dispatchTouchEvent方法强制返回ture或者false会发生什么？


### viewGroup 怎么知道view有没有消费事件


### 内存泄漏有哪些？怎么排查


### android的handler机制


### android的anr机制


### android冷启动优化


### android弱网优化


### android长图片加载怎么实现


### 一个app发布一个版本后，发现变卡了，你如何复现？如何得知某个地方变卡，如何得知用户在某行代码变卡


### android 动画机制有哪些？


### lottie的原理


### 直播中的动画要怎么做？要做成动态的，比如礼物是可以配置的？


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