package com.sty.ne.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 参考：show/paint.png
 * Created by tian on 2019/10/8.
 */

public class GradientLayout extends View {
    private Paint mPaint;
    private Shader mShader;
    private Bitmap mBitmap;

    public GradientLayout(Context context) {
        this(context, null);
    }

    public GradientLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(); // 初始化
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setStyle(Paint.Style.FILL); //描边效果 参考show/stroke.png

//        initApi();
    }

    private void initApi() {
        mPaint.setColor(Color.RED); //设置颜色
        mPaint.setARGB(255, 255, 2555, 0); //设置Paint对象颜色，范围0~255
        mPaint.setAlpha(200); //设置alpha不透明度，范围：0~255
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setStyle(Paint.Style.STROKE); //描边效果 参考show/stroke.png
        mPaint.setStrokeWidth(4); //描边宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); //拐角风格 参考show/stroke_cap.png
        mPaint.setStrokeJoin(Paint.Join.MITER); //拐角风格 参考show/stroke_join.png
        mPaint.setShader(new SweepGradient(200, 200, Color.BLUE, Color.RED)); //设置环形渲染器
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN)); //设置图层混合模式
        mPaint.setColorFilter(new LightingColorFilter(0x00ffff, 0x000000)); //设置颜色过滤器
        mPaint.setFilterBitmap(true); //设置双线性过滤 参考show/filter_bitmap.png
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL)); //设置画笔遮罩滤镜，传入度数和样式
        mPaint.setTextScaleX(2); //设置文本缩放倍数
        mPaint.setTextSize(38); //设置文字大小
        mPaint.setTextAlign(Paint.Align.LEFT); //设置对齐方式
        mPaint.setUnderlineText(true); //设置下划线

        String str = "Android 高级工程师";
        Rect rect = new Rect();
        mPaint.getTextBounds(str, 0, str.length(), rect); //测量文本大小，将文本信息存在rect中
        mPaint.measureText(str); //获取文本的宽
        mPaint.getFontMetrics(); //获取字体度量对象 参考show/font_metrics.png
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawLinearGradient(canvas);
//        drawRadialGradient(canvas);
//        drawSweepGradient(canvas);
//        drawBitmapGradient(canvas);
        drawMultiplyGradient(canvas);
    }

    /**
     * 线性渲染
     * @param canvas
     */
    private void drawLinearGradient(Canvas canvas) {
        /**
         * x0,y0: 渐变起始点坐标
         * x1,y1: 渐变结束点坐标
         * color0: 渐变开始颜色，16进制的颜色表示，必须带有透明度
         * color_n: 渐变结点颜色
         * colors: 渐变色数组
         * positions: 位置数组，position的取值范围为[0,1],作用是指定某个位置的颜色，如果传null，渐变就线性变化
         * title: 用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
         */
        mShader = new LinearGradient(0, 0, 500, 500, new int[]{Color.RED, Color.BLUE, Color.GREEN},
                new float[]{0.f, 0.7f, 1}, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
//        canvas.drawCircle(250, 250, 250, mPaint);  //参考show/linear_gradient_circle.png
        canvas.drawRect(0, 0, 500, 500, mPaint);  //参考show/linear_gradient_rect.png
    }

    /**
     * 环形渲染
     * @param canvas
     */
    private void drawRadialGradient(Canvas canvas) {
        /**
         * centerX,centerY: shader的中心坐标，边界的渐变色
         * radius: 渐变的半径
         * centerColor,edgeColor: 中心点渐变色，边界的渐变色
         * colors: 渐变颜色数组
         * stoops: 渐变位置数组，类似扫描渐变的positions数组，取值[0,1], 中心点位0，半径到达位置为1.0f
         * titleMode: shader未覆盖以外的填充模式
         */
        mShader = new RadialGradient(250, 250, 250, new int[]{Color.GREEN, Color.YELLOW, Color.RED},
                null, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        canvas.drawCircle(250, 250, 250, mPaint);  //参考show/radial_gradient_circle.png
    }

    /**
     * 扫描渲染
     * @param canvas
     */
    private void drawSweepGradient(Canvas canvas) {
        /**
         * cx,cy: 渐变中心坐标
         * radius: 渐变的半径
         * color0,color1: 渐变开始颜色、结束颜色
         * colors: 渐变颜色数组
         * positions: 类似LinearGradient,用于多颜色渐变，positions为null时，根据颜色线性渐变
         * titleMode: shader未覆盖以外的填充模式
         */
        mShader = new SweepGradient(250, 250, Color.RED, Color.GREEN);
        mPaint.setShader(mShader);
        canvas.drawCircle(250, 250, 250, mPaint);  //参考show/sweep_gradient_circle.png
    }

    /**
     * 位图渲染
     * @param canvas
     */
    private void drawBitmapGradient(Canvas canvas) {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
        /**
         * bitmap: 构造shader使用的bitmap
         * tileX: x轴方向的TileMode
         * tileY: Y轴方向的TileMode
         *      REPEAT: 绘制区域超过渲染区域的部分，重复排版
         *      CLAMP: 绘制区域超过渲染区域的部分，会以最后一个像素拉伸排版
         *      MIRROR: 绘制区域超过渲染区域的部分，镜像翻转排版
         */
        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP); //当bitmap不够大时水平和竖直方向最后一个像素填充
        mPaint.setShader(mShader);
//        canvas.drawCircle(250, 250, 250, mPaint);
        canvas.drawRect(0, 0, 1000, 1000, mPaint); //参考show/bitmap_gradient_rect.png
    }

    /**
     * 组合渲染
     * @param canvas
     */
    private void drawMultiplyGradient(Canvas canvas) {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);

        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearGradient linearGradient = new LinearGradient(0, 0, 1000, 1000,
                new int[]{Color.RED, Color.GREEN, Color.BLUE}, null, Shader.TileMode.CLAMP);

        /**
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, Xfermode mode)
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, PorterDuff.Mode mode)
         * shaderA,shaderB:要混合的两种shader
         * Xfermode mode： 组合两种shader颜色的模式
         * PorterDuff.Mode mode: 组合两种shader颜色的模式
         */
        mShader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);
        mPaint.setShader(mShader);
        canvas.drawCircle(500, 500, 500, mPaint);
    }
}
