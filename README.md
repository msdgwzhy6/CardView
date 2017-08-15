# CardView

自定义view————卡券

最近修改公司商城项目，需要加入优惠券／卡券，今天就给大家带来自定义view————卡券，效果图如下

![效果图](https://user-gold-cdn.xitu.io/2017/8/15/8c1f216ad27b4c1cdcaf169108ea9b1e)

分析一下过程：因为除了背景以外，里面还要放其他的控件，这里采取继承RelativeLayout去画锯齿背景，虽然也可以继承LinearLayout，但是考虑到布局的嵌套问题还是最终选取了RelativeLayout，然后根据宽度计算出能放多少个圆，然后剩余多少宽度，然后两边对半分

### 计算圆数量

view的宽度=圆+间距+多余宽度，因此我们先写固定的圆半径、圆与圆之间的间距、然后计算出能放多少个圆

```
        //圆半径
        int radius = 10;
        //圆间距
        int gap = 8;
        //圆数量
        int circleNum;

    
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //计算圆的数量
            circleNum = (w - gap) / (radius * 2 + gap);
        }
```

但是又考虑到平分的时候宽度还有剩余的，为了视觉更佳，将剩余宽度一分为二放首尾
```
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        remain = ((w - gap) % (radius * 2 + gap)) / 2;
        circleNum = (w - gap) / (radius * 2 + gap);
        mheight = h;
    }
```

### 画锯齿背景

计算出view的宽度和高度，根据计算的圆数量进行循环画圆，y轴分别为0，mheight
```
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            float x = gap + radius + remain + ((gap + radius * 2) * i);
            canvas.drawCircle(x, 0, radius, mPaint);
            canvas.drawCircle(x, mheight, radius, mPaint);
        }
    }
```

大致效果已经出来了，现在将自定义属性写成xml中可自定义的

### 属性设置

attrs.xml
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="CardView">
        <attr name="cirlceColor" format="color"/>
        <attr name="radius" format="integer"/>
        <attr name="gap" format="integer"/>
    </declare-styleable>
</resources>
```
自定义view中获取属性进行设置
```
    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardView);
        cirlceColor = typedArray.getColor(R.styleable.CardView_cirlceColor, Color.WHITE);
        radius = typedArray.getInteger(R.styleable.CardView_radius, 10);
        gap = typedArray.getInteger(R.styleable.CardView_gap, 8);
        typedArray.recycle();
    }
```

xml中应用
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/white"
    tools:context="com.cardview.MainActivity">

    <com.cardview.CardView
        android:layout_width="320dp"
        android:layout_height="200dp"
        android:layout_centerInParent="true"
        android:background="@color/colorAccent"
        app:cirlceColor="@android:color/white"
        app:gap="10"
        app:radius="20">

        <ImageView
            android:id="@+id/img"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_centerVertical="true"
            android:layout_marginLeft="20dp"
            android:layout_marginStart="20dp"
            android:background="@mipmap/ic_launcher_round"
            android:contentDescription="@null" />

        <TextView
            android:id="@+id/txt1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_toEndOf="@+id/img"
            android:layout_toRightOf="@+id/img"
            android:padding="20dp"
            android:text="优惠券"
            android:textSize="36sp" />

        <TextView
            android:id="@+id/txt2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@+id/txt1"
            android:layout_toEndOf="@+id/img"
            android:layout_toRightOf="@+id/img"
            android:padding="10dp"
            android:text="编号：1234567890"
            android:textSize="16sp" />

        <TextView
            android:id="@+id/txt3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@+id/txt2"
            android:layout_toEndOf="@+id/img"
            android:layout_toRightOf="@+id/img"
            android:padding="10dp"
            android:text="日期：2017年8月25日"
            android:textSize="16sp" />


    </com.cardview.CardView>

</RelativeLayout>

```
然后效果图就是上面的了

[个人博客](https://madreain.github.io/)


