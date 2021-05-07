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


### livedata为何具有生命周期感知能力


### shareperference如何保证线程安全，为什么不是进程安全的，如何实现进程安全的


### viewmodel是如何解决内存泄漏问题的？能解决么


### mmkv共享内存和binder的区别


### 有做过多线程开发么


### room如何实现orm的


### kotlin和java产物一样么


### 各种图片格式的区别


### 如何保证内存与文件的同步


### 开一个线程commit，commit不用加锁么？


### 怎么确定bitmap被复用了


### 一张图片占用的内存大小


### MVC,MMVM,MVP


### MMVM如何解决MVP中存在的问题


### 内存泄漏


### 内存泄漏的几种情况


### LeakCancary分析内存泄漏的原理


### 手写单例模式，并分析


### 找到两个数组中的两个元素的和等于某个值


### StartService和BindService的生命周期


### 求浮点数的平方根


### 工厂模式


### 抽象工厂模式和普通工厂模式


### 建造者模式


### 启动模式有几种


### 共享内存原理


### java能实现共享内存么


### kotlin协程的四个dispatcher及区别


### window，decorview，ViewRootImpl的关系,面试官 想让你回答如何window和windowManager如何联系的。viewRootImpl是联系window和decorview的纽带


### 进程间的通信方式


### AIDL的用法


### onIntercept在哪用


### 事件分发底层的产生


### RecycleView的用法里面的各种Manager作用


### wake lock：使屏幕常亮


### Activity是如何被限制到状态栏和导航栏之下的


### canvas的save和restore的作用


### canvas的density有什么作用


### vsync是如何生成的


### android中异步一般怎么实现


### android中内存泄漏发生的情况


### 如何去获取view的宽高


### 如何实现一个悬浮窗


### 一个悬浮窗悬浮在顶层，覆盖了底层的app的启动图标，如果让事件启动图标接收到click事件


### Android M之前与之后的权限变化


### RxJava中map和flatmap的区别


### view的绘制


### viewgroup是如何刷新的


### onMeasure,onlayout,onDraw分别起什么作用


### onLayout的时候可以layout自己么？


### 洗牌问题


### 接雨水


### 两道设计


### 面向对象原则


### 用代码描述你觉得最有设计感的项目


### 典型情况下的Activity生命周期？


### 异常情况下的Activity的生命周期 & 数据如何保存和恢复？


### 从Activity A跳转到Activity B之后，然后再点击back建之后，它们的生命周期调用流程是什么？


### 如何统计Activity的工作时间？


### Activity的启动模式 & 使用场景


### 如何在任意位置关掉应用所有Activity & 如何在任意位置关掉指定的Activity？


### Activity的启动流程(从源码角度解析)？


### Activity任务栈是什么？在项目中有用到它吗？说给我听听


### 广播是什么？


### 广播的注册方式有哪些？


### 广播的分类 & 特性 & 使用场景？


### 什么是内容提供者？


### 说说如何创建自己应用的内容提供者 & 使用场景


### 说说ContentProvider的原理


### ContentProvider,ContentResolver,ContentObserver之间的关系


### 说说ContentProvider的权限管理


### 什么是Service?


### 说说Service的生命周期


### Service和Thread的区别？


### Android 5.0以上的隐式启动问题及其解决方案。


### Service保活方案


### IntentService是什么 & 原理 & 使用场景 & 和Service的区别。


### 创建一个独立进程的Service应该怎样做？


### 子线程一定不能更新UI吗？


### 给我说说Handler的原理


### Handler导致的内存泄露你是如何解决的？


### 如何使用Handler让子线程和子线程通信？


### HandlerThread是什么 & 原理 & 使用场景？


### 一个线程能否创建多个Handler,Handler和Looper之间的对应关系？


### 为什么Android系统不建议子线程访问UI？


### AsyncTask是什么？能解决什么问题


### 给我谈谈AsyncTask的三个泛型参数作用 & 它的一些方法作用。


### 给我说说AsyncTask的原理


### 你觉得AsyncTask有不足之处吗？


### Android中v4包下Fragment和app包下Fragment的区别是什么？


### Fragment的生命周期 & 请结合Activity的生命周期再一起说说。


### 说说Fragment如何进行懒加载。


### ViewPager + Fragment结合使用会出现内存泄漏吗 & 如何解决？


### Fragment如何和Activity进行通信 & Fragment之间如何进行通信？


### 给我谈谈Fragment3种切换的方式以及区别 & 使用场景。


### 文件存储


### 说说Android中数据持久化的方式 & 使用场景


### 接触过MMKV吗？说说SharedPreference和它的区别


### 第三方数据库框架用过哪些？有没有自己封装过一个SQLite的库


### SQLite是线程安全的吗 & SharedPreference是线程安全的吗？


### 请简单的给我说说什么是三级缓存？


### SharedPreference的apply和commit的区别


### 谈谈你对SQLite事务的认识


### ListView是什么？如何使用？


### RecyclerView是什么？如何使用？如何返回不一样的Item。


### ListView和RecycyclerView的区别是什么？


### 分别讲讲你对ListView & RecyclerView的优化经验。


### 给我说说RecyclerView的回收复用机制


### 说说你是如何给ListView & RecyclerView加上拉刷新 & 下拉加载更多机制


### 谈谈你是如何对ListView & RecycleView进行局部刷新的？


### 你对Bitmap了解吗？它在内存中如何存在？


### 有关Bitmap导致OOM的原因知道吗？如何优化？


### 给我谈谈图片压缩。


### LruCache & DiskLruCache原理。


### 说说你平常会使用的一些第三方图片加载库,最好给我谈谈它的原理。


### 如果让你设计一个图片加载库，你会如何设计？


### 你知道Android中处理图片的一些库吗(OpenCv & GPUImage …)？


### WebView会导致内存泄露吗？原因是什么？解决方式有哪些？


### 你知道Hybrid开发吗？说说你的相关经验


### 说说WebSettings & WebViewClient & WebChromeClient这三个类的作用 & 用法。


### 说说你了解的Hybrid框架。


### 如何提高原生的WebView加载速度？


### 谈谈你对webView工作机制的认识,你在项目中优化过它吗？说说是从哪些方面着手的？


### 什么是ViewPager?说说它的那些适配器。(校招&实习)


### 你了解ViewPager2吗？和ViewPager 1有哪些区别？


### ViewPager + Fragment结合使用存在的内存泄漏的原因是什么？如何解决？


### 什么是事件分发机制？主要用来解决什么问题？(校招&实习)


### 给我说说事件分发的流程 & 你项目解决事件冲突的一些案例。


### 分别讲讲有关事件分发的三个方法的作用及关系。


### 如果我在一个设置了点击事件的TextView中dispatchTouchEvent方法强制返回ture或者false会发生什么？


### 谈谈你对MotionEvent的认识？Cancel事件是什么情况下触发的？


### requestLayout(),onLayout(),onDraw(),drawChild()区别和联系？


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