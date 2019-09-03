使用：导入Gauge插件后重启android studio使用以下符号编写README.md文档

1、标题写法：关于标题还有等级表示法，分为六个等级，显示的文本大小依次减小。不同等级之间是以井号  #  的个数来标识的。
(**内容写在#号后，#号与内容之间要有1个及以上空格。** 1-6个#代表1-6级字体的大小)

#  一级标题
##  二级标题
###  三级标题
####  四级标题
#####  五级标题
######  六级标题

2、字体格式强调:我们可以使用下面的方式给我们的文本添加强调的效果(**内容写在*号之间**，1个*代表斜体，2个代表粗体，3个代表粗斜体)

*强调*  (示例：斜体)

**加重强调**  (示例：粗体)

***特别强调*** (示例：粗斜体)

3、代码(**内容写在`之间**)

`<hello world>`

4、代码块高亮(**内容写在```之间**)

```
@Override
protected void onDestroy() {
    EventBus.getDefault().unregister(this);
    super.onDestroy();
}
```

5、表格 （建议在表格前空一行，否则可能影响表格无法显示。表头用|分开，表头下用----|----|表示，内容用|分开）

表头|表头 |表头
----|-----|------
单元格内容|单元格内容|单元格内容
单元格内容|单元格内容|单元格内容

6、其他引用

图片：
![百度](https://www.baidu.com/img/bd_logo1.png)

链接：
[百度一下，你就知道](https://www.baidu.com/)

7、列表(**用1.2.3.表示，.后面要有1个及以上空格。加空心圆点的用    *表示，空格要3格或3格以上，要不然会是实心圆点**)

1. 项目1
2. 项目2
3. 项目3
   * 项目1 （一个*号会显示为一个黑点，注意⚠️有空格，否则直接显示为*项目1）
   * 项目2

8、引用(**用>号表示**)

> 第一行引用文字

> 第二行引用文字