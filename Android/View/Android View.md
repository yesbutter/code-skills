# Table of Contents

* [Android View](#android-view)
  * [1. View](#1-view)
    * [onDraw](#ondraw)
    * [进阶](#进阶)
    * [动画](#动画)
  * [2. ViewGroup](#2-viewgroup)
    * [测量过程](#测量过程)
    * [布局阶段](#布局阶段)
  * [3.触摸反馈](#3触摸反馈)


# Android View

## 内容是从[hencoder](https://hencoder.com/)的阅读学习笔记。

## 1. View

### onDraw

- Canvas

	- drawColor

	  绘制内容的颜色

	- drawCircle

	  参数是x,y坐标，r和画笔。

	- drawRect

	  参数是left,top,right,bottom 和画笔

	- drawPoint

	  参数是绘制点的坐标和画笔。

	- drawOval

	  参数是椭圆left,top,right,bottom和画笔。

	- drawLine

	  参数是起始点，和终点的坐标、和画笔。

	- drawRounRect

	  参数是圆角矩形的left,top,right,bottom,x方向的圆角弧度，和y方向的圆角弧度，和画笔。

	- drawArc

	  参数是弧线(扇形)的原型。首先描述椭圆的坐标，left,top,right,bottom。然后是开始角度，结束角度、是否连接到圆心(是否扇形),画笔。

	- drawPath

	  参数 path、画笔。

		- path

		  路径

			- 添加图形

			  图形可以选择填充方式。FillType

	- drawText

	  参数 字符串、x、y、画笔

	- drawBitmap

	  参数 bitmap、left、top、right、bottom、画笔。
	  和一些重载方法

- Paint

	- 颜色

		- setColor

		  设置线条的颜色

		- setShader

		  设置着色器，颜色渐变。

			- LinearGradient

			  线性渐变。设置俩个点和俩个颜色。以俩个点作为端点，使用颜色渐变来绘制颜色。

			- RedialGradient

			  辐射渐变。从中心向周围辐射状的渐变。

			- SweepGradient

			  扫描渐变。

			- BitmapShader

			  用bitmap来着色。效果相当于裁切。

			- ComposeShader

			  混合着色器。构造俩个shader并且选定组合模式。PorterDuff.Mode

		- setColorFilter

		  为绘制设置颜色过滤

			- LightingColorFilter

			  用来模拟简单的光照效果

			- PorterDuffColorFilter
			- ColorMatrixColorFilter

		- setXfermode

		  canvas和目标位置的内容选取对应的porter duff mode混合得出最终的图像。

			- 离屏缓冲

			  混合是和整个图形混合，离屏缓存把内容绘制在额外的层上，
			  短暂的离屏缓冲
			  int status = Canvas.saveLayer();
			  
			  ...
			  
			  
			  canvas.restoreToCount(status);

	- 效果

		- setAntiAlias

		  设置抗锯齿

		- setStyle

		  设置图形线条风格还是填充风格

		- 线条形状

			- setStrokeWidth

			  设置线条宽度。默认值为0.(不会因为放大或者缩小而改变的一种特殊宽度)

			- setStrokeCap

			  设置线头的形状。线头形状有三种。BUFF平头、ROUND圆头、SQUAARE方头。默认为BUTT。

			- setStrokeJoin

			  设置拐角的形状。有三个值可以选择MITER尖角、BEVEL平角、ROUND圆角。默认为MITER。

			- setStrokeMiter

			  设置MITER型拐角的延长线的最大值。

		- 色彩优化

			- setDither

			  设置图像的抖动。

			- setFilterBitmap

			  是否使用双线性过滤来绘制Bitmap

		- setPathEffect

		  使用PathEffect来给图形的轮廓设置效果。对canvas的所有图形绘制有效。

			- CornerPathEffect

			  把所有拐角变成圆角。

			- DiscretePathEffect

			  把线条进行随机的偏离，让轮廓变得乱七八糟。

			- DashPathEffect

			  使用虚线来绘制线条。

			- PathDashPathEffect

			  用path来绘制虚线。

			- SumPathEffect

			  组合效果，按照俩种效果进行绘制

			- ComposePathEffect

			  组合效果，先后绘制。

		- setShadowLayer

		  绘制内容阴影。

		- setMaskFilter

		  在绘制的上层添加效果。

			- BlurMaskFilter

			  模糊效果的MaskFilter

			- EmbossMaskFilter

			  浮雕效果

		- PorterDuff.Mode

		  指定俩个图像的共同绘制时的策略。分为SRC和DST俩个部分。

			- Alpha合成

				- SRC
				- SRC_OVER
				- SRC_IN
				- SRC_ATOP
				- DST
				- DST_OVER
				- DST_IN
				- DST_ATOP
				- CLEAR
				- SRC_OUT
				- DST_OUT
				- XOR

			- 混合

				- DARKEN
				- LIGHTEN
				- MULTIPLY
				- SCREEN
				- OVERLAY

		- 获取绘制的Path

	- drawText相关

		- 文字大小
		- 文字间隔
		- 文字效果

			- 加粗
			- 下划线
			- 斜体
			- 下横线

	- 初始化类

		- reset
		- set
		- setFlags

### 进阶 

- 绘制辅助

	- 范围裁切

	  裁切之后的绘制代码，都会被限制在裁切范围内。

		- clipRect()
		- clipPath()

	- 几何变换

		- Canvas

		  变化是对坐标系的变化。

			- 平移 translate
			- 旋转rotate
			- 缩放scale
			- 错切skew

		- Matrix

		  可以定义插入的位置，pre/post

			- 平移 translate

			  可以定义插入的位置，pre/post

			- 旋转rotate

			  可以定义插入的位置，pre/post

			- 缩放scale

			  可以定义插入的位置，pre/post

			- 错切skew

			  可以定义插入的位置，pre/post

		- Camera

		  实现三维变换。

			- 三维旋转 rotete*()
			- 移动 translate
			- 设置虚拟相机位置，单位是英寸

			  可以提高Z点坐标，来解决旋转之后图片过大的问题。

- 绘制顺序

	- super.onDraw

	  判断你绘制的内容需要盖住空间原有的内容还是需要被控件原有内容盖住，从而确定你的绘制代码是应该写在onDraw的上面还是下面。

		- 上面

		  会在原内容绘制之前进行。比如可以通过在文字下层绘制纯色矩阵作为强调色。

		- 下面

		  会覆盖绘制的内容

	- super.dispatchDraw

	  在viewGroup中，会先调用自己的onDraw方法，绘制完成之后绘制子view。子view绘制完成之后会覆盖viewGroup里面的ondraw内容。
	  具体来讲，绘制子view是通过另一个绘制方法的调用来发生的，这个绘制方法叫做：dispatchDraw()。在绘制过程中，每个view和viewGroup都会先调用onDraw()方法来绘制主体，再调用dispatchDraw()方法来绘制子view。

		- 上面

		  发生在自身和子view的ondraw方法之前。

		- 下面

		  该viewGroup的绘制代码就会在

	- super.draw

	  绘制的总调度方法。

		- 上面

		  如果在前面就会在其他所有绘制之前被执行。背景也会盖住它。

		- 下面

		  绘制完成之后执行。效果和重写onDrawForeground()下面相同。

- 绘制流程 draw

	- 1. 背景 drawBackGround
	- 2. 主体 onDraw
	- 3.  子view dispatchDraw
	- 4. 滑动边缘、滑动条、前景

### 动画

- 属性动画

	- ViewPropertyAnimator

	  使用view.animate()后面根动画的方法，动画就会自动执行。有偏移、旋转、缩放、设置透明度的变化。

	- ObjectAnimator

	  1.如果是自定义控件，需要添加setter/getter方法。并且在setter的时候进行画面重绘。
	  2.用ObjectAnimator.ofXXX()创建ObjectAnimator对象;
	  3.用start()方法执行动画。

		- setDuration

		  设置动画时长

		- setInterpolator

		  设置速度调节器。

	- ObjectAnimator进阶

		- TypeEvaluator

			- ArgbEvaluator

			  Argb颜色过滤器

			- 自定义Evaluator
			- ofObject

		- PropertyValuesHolder

		  可以在同一个动画中改变多个属性。属性值批量存放地。所以你如果有多个属性需要修改，可以把它们放在不同propertyvaluesHolder中，然后使用统一放进animator。不用为每个属性单独创建animator执行。

			- ofKeyframe()

			  通过对同一个动画拆分成多个阶段。

	- 监听器

	  可以在咋子关键时刻得到反馈，从而及时做出合适的操作。

## 2. ViewGroup

### 测量过程

测量阶段，measure() 方法被父 View 调用，在 measure() 中做一些准备和优化工作后，调用 onMeasure() 来进行实际的自我测量。 onMeasure() 做的事，View 和 ViewGroup 不一样：
View：View 在 onMeasure() 中会计算出自己的尺寸然后保存；
ViewGroup：ViewGroup 在 onMeasure() 中会调用所有子 View 的 measure() 让它们进行自我测量，并根据子 View 计算出的期望尺寸来计算出它们的实际尺寸和位置（实际上 99.99% 的父 View 都会使用子 View 给出的期望尺寸来作为实际尺寸，原因在下期或下下期会讲到）然后保存。同时，它也会根据子 View 的尺寸和位置来计算出自己的尺寸然后保存；


全新定制尺寸和修改尺寸的最重要区别
需要在计算的同时，保证计算结果满足父 View 给出的的尺寸限制
父 View 的尺寸限制
由来：开发者的要求（布局文件中 layout_ 打头的属性）经过父 View 处理计算后的更精确的要求；
限制的分类：
UNSPECIFIED：不限制
AT_MOST：限制上限
EXACTLY：限制固定值


调用每个子 View 的 measure() 来计算子 View 的尺寸
计算子 View 的位置并保存子 View 的位置和尺寸
计算自己的尺寸并用 setMeasuredDimension() 保存

### 布局阶段

布局阶段，layout() 方法被父 View 调用，在 layout() 中它会保存父 View 传进来的自己的位置和尺寸，并且调用 onLayout() 来进行实际的内部布局。onLayout() 做的事， View 和 ViewGroup 也不一样：
View：由于没有子 View，所以 View 的 onLayout() 什么也不做。
ViewGroup：ViewGroup 在 onLayout() 中会调用自己的所有子 View 的 layout() 方法，把它们的尺寸和位置传给它们，让它们完成自我的内部布局。


在 onLayout() 里调用每个子 View 的 layout() ，让它们保存自己的位置和尺寸。

## 3.触摸反馈

自定义触摸反馈的关键：
重写 onTouchEvent()，在里面写上你的触摸反馈算法，并返回 true（关键是 ACTION_DOWN 事件时返回 true）。
如果是会发生触摸冲突的 ViewGroup，还需要重写 onInterceptTouchEvent()，在事件流开始时返回 false，并在确认接管事件流时返回一次 true，以实现对事件的拦截。
当子 View 临时需要阻止父 View 拦截事件流时，可以调用父 View 的 requestDisallowInterceptTouchEvent() ，通知父 View 在当前事件流中不再尝试通过 onInterceptTouchEvent() 来拦截。

