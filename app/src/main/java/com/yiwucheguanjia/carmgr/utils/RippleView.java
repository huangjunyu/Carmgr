package com.yiwucheguanjia.carmgr.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;


/**
 * Created by RMMF on 2015/9/5.
 */
public class RippleView extends RelativeLayout {

    /**
     * RippleView的宽度
     */
    private int WIDTH;
    /**
     * RippleView的高度
     */
    private int HEIGHT;

    /**
     * Ripple动画是否正在显示
     */
    private boolean animationRunning = false;
    /**
     * Ripple动画的时长
     */
    private int rippleDuration = 100;
    /**
     * 记录Ripple动画显示了多少帧
     */
    private int timer = 0;
    /**
     * 每一帧Ripple动画的时长
     */
    private int frameRate = 10;

    /**
     * Ripple圆心的x坐标
     */
    private float x = -1.0F;
    /**
     * Ripple圆心的y坐标
     */
    private float y = -1.0F;
    /**
     * Ripple的最大半径
     */
    private float radiusMax = 0.0F;
    /**
     * 是否强制Ripple的圆心为RippleView的中心
     */
    private Boolean isCentered;

    /**
     * Paint对象
     */
    private Paint paint;
    /**
     * Ripple的颜色
     */
    private int rippleColor;
    /**
     * Ripple的透明度
     */
    private int rippleAlpha = 90;

    /**
     * 在draw()中调用，用Handler的postDelay()方法实现每frameRate绘制一帧Ripple
     */
    private Handler canvasHandler;
    /**
     * 传给canvasHandler执行，递归调用draw()方法
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            RippleView.this.invalidate();
        }
    };

    public RippleView(Context context) {
        super(context);
    }

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    /**
     * 解析属性、初始化paint
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if (!this.isInEditMode()) {

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
            this.rippleColor =
                    typedArray.getColor(R.styleable.RippleView_rv_color, this.getResources().getColor(R.color.gray_default));
            this.rippleAlpha =
                    typedArray.getInteger(R.styleable.RippleView_rv_alpha, this.rippleAlpha);
            this.frameRate =
                    typedArray.getInteger(R.styleable.RippleView_rv_frameRate, this.frameRate);
            this.rippleDuration =
                    typedArray.getInteger(R.styleable.RippleView_rv_rippleDuration, this.rippleDuration);
            this.isCentered =
                    typedArray.getBoolean(R.styleable.RippleView_rv_centered, false);
            typedArray.recycle();

            this.paint = new Paint();
            this.paint.setAntiAlias(true);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.rippleColor);
            this.paint.setAlpha(this.rippleAlpha);

            this.setWillNotDraw(false);

            this.canvasHandler = new Handler();

            this.setDrawingCacheEnabled(true);
            this.setClickable(true);
        }
    }

    /**
     * 获取RippleView的实际尺寸
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.WIDTH = w;
        this.HEIGHT = h;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //如果当前没有运行Ripple动画，那么什么也不做
        if (this.animationRunning) {
            //如果Ripple动画运行了rippleDuration的时长，那么Ripple动画结束
            if (this.rippleDuration <= this.timer * this.frameRate) {
                //重置各个标志位
                this.animationRunning = false;
                this.timer = 0;
                //恢复Ripple动画前的状态
                canvas.restore();
                this.invalidate();
                return;
            }
            //延时递归调用draw(),形成动画效果
            this.canvasHandler.postDelayed(this.runnable,(long)this.frameRate);
            //如果是Ripple动画刚开始那么保存动画前的状态
            if (this.timer == 0) {
                canvas.save();
            }
            //开始绘制每一帧Ripple动画
            canvas.drawCircle(this.x, this.y, this.radiusMax * ((float) this.timer * (float) this.frameRate) / (float) this.rippleDuration, this.paint);
            ++this.timer;
        }
    }

    /**
     * 启动Ripple,相当于创建一个Ripple动画并开始运行
     * @param x
     * @param y
     */
    private void createAnimation(float x, float y) {
        //只有当前没有Ripple运行的时候才创建新的Ripple,也就是指呈现给用户一个Ripple
        if (this.isEnabled() && !this.animationRunning) {
            //给radiusMax设置值为直角三角形的斜边长
            this.radiusMax = (float)Math.sqrt(Math.pow(this.WIDTH,2.0F) + Math.pow(this.HEIGHT, 2.0F));
            //根据isCentered确定Ripple的圆心位置
            if (!this.isCentered.booleanValue()) {
                this.x = x;
                this.y = y;
            } else {
                this.x = (float)(getMeasuredWidth() / 2);
                this.y = (float)(getMeasuredHeight() / 2);
            }
            //启动Ripple
            this.animationRunning = true;
            this.invalidate();
        }
    }

    /**
     * 启动Ripple
     * @param event
     */
    public void animateRipple(MotionEvent event) {
        this.createAnimation(event.getX(), event.getY());
    }

    /**
     * 处理用户的点击事件，如果用户按下，那么将会启动Ripple
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            this.animateRipple(event);
        return super.onTouchEvent(event);
    }

    /**
     * 因为是继承了RelativeLayout，所以在此处拦截点击事件
     * 这里拦截并不是真的拦截，只是先交给自己的onTouchEvent处理
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        this.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }
}
