package me.brucezz.viewpagerdotindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by bruce on 15-12-10.
 */
public class DotIndicator extends View {

    private static final String TAG = "DotIndicator";

    /**
     * Dot的大小
     */
    private float dotSize;

    /**
     * Dot的数量
     */
    private int dotCount;

    /**
     * Dot间隔
     */
    private float dotInterval;

    /**
     * 未选中Dot的颜色
     */
    private int dotColor;

    /**
     * 选中Dot的颜色
     */
    private int dotSelectedColor;

    /**
     * 选中Dot的位置
     */
    private int current;
    /**
     * 距current的偏移量
     */
    private float offset = 0;

    /**
     * 画笔
     */
    private Paint dotPaint;

    /**
     * 实际绘制位置
     */
    private int realDrawPoint = 0;


    /**
     * 滑动动画时长
     */
    private int duration = 500;


    private Scroller scroller;

    /**
     * 默认属性值
     */
    public final float defaultSize = dp2px(4f);
    public final int defaultCount = 3;
    public final float defaultInterval = dp2px(4f);
    public final int defaultUnselectedColor = Color.BLACK;
    public final int defaultSelectedColor = Color.GRAY;
    public final int defaultCurrent = 0;

    public DotIndicator(Context context) {
        this(context, null);
    }

    public DotIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray ta = getContext().getTheme()
                .obtainStyledAttributes(attrs, R.styleable.DotIndicator, defStyleAttr, 0);

        try {
            dotSize = ta.getDimension(R.styleable.DotIndicator_dot_size, defaultSize);
            dotCount = ta.getInt(R.styleable.DotIndicator_dot_count, defaultCount);
            dotInterval = ta.getDimension(R.styleable.DotIndicator_dot_interval, defaultInterval);
            dotColor = ta.getColor(R.styleable.DotIndicator_dot_color, defaultUnselectedColor);
            dotSelectedColor = ta.getColor(R.styleable.DotIndicator_dot_selected_color, defaultSelectedColor);
            current = ta.getInt(R.styleable.DotIndicator_dot_current, defaultCurrent);

        } finally {
            ta.recycle();
        }

        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);

        scroller = new Scroller(getContext());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //MeasureSpec.EXACTLY
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //MeasureSpec.AT_MOST 即 wrap_content
        int width = ((int) (dotSize * dotCount + dotInterval * (dotCount - 1)));
        int height = ((int) dotSize);

        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST
                        ? width + getPaddingLeft() + getPaddingRight() : widthSize
                , heightMode == MeasureSpec.AT_MOST
                        ? height + getPaddingTop() + getPaddingBottom() : heightSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int radius = (int) (dotSize / 2);

        //绘制所有未选中Dot
        dotPaint.setColor(dotColor);
        for (int i = 0; i < dotCount; i++) {

            canvas.drawCircle(left + radius, top + radius, radius, dotPaint);

            left += dotSize + dotInterval;
        }

        //绘制选中的Dot
        dotPaint.setColor(dotSelectedColor);
        canvas.drawCircle(realDrawPoint + radius, top + radius, radius, dotPaint);

    }

    /**
     * 切换到下一个Dot
     */
    public void next() {
        if (current < dotCount - 1)
            setCurrent(current + 1);
    }

    /**
     * 切换到上一个Dot
     */
    public void previous() {
        if (current > 0)
            setCurrent(current - 1);
    }

    @Override
    public void computeScroll() {
        if (scroller != null && scroller.computeScrollOffset()) {
            realDrawPoint = getPaddingLeft() + scroller.getCurrX();
            postInvalidate();
        }
    }

    /**
     * 计算实际绘制位置
     */
    private void updateRealDrawPoint() {
        realDrawPoint = getPaddingLeft() + (int) ((current + offset) * (dotSize + dotInterval));
    }

    /***************************************************
     * 　　　　　　　　　　Getter/Setter　　　　　　　　　 *
     ***************************************************/

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int dest) {
        if (dest < 0 || dest >= dotCount) return;

        scroller.startScroll(((int) (current * (dotSize + dotInterval))), 0,
                ((int) ((dest - current) * (dotSize + dotInterval))), 0, duration);
        current = dest;
        invalidate();
    }

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
        invalidate();
    }

    public int getDotCount() {
        return dotCount;
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
        if (current >= dotCount)
            current = dotCount - 1;

        updateRealDrawPoint();
        requestLayout();
        invalidate();
    }

    public float getDotInterval() {
        return dotInterval;
    }

    /**
     * @param dotInterval 　单位 dp
     */
    public void setDotInterval(float dotInterval) {
        this.dotInterval = dp2px(dotInterval);
        updateRealDrawPoint();
        requestLayout();
        invalidate();
    }

    public int getDotSelectedColor() {
        return dotSelectedColor;
    }

    public void setDotSelectedColor(int dotSelectedColor) {
        this.dotSelectedColor = dotSelectedColor;
        invalidate();
    }

    public float getDotSize() {
        return dotSize;
    }

    /**
     * @param dotSize 　单位 dp
     */
    public void setDotSize(float dotSize) {
        this.dotSize = dp2px(dotSize);
        updateRealDrawPoint();
        requestLayout();
        invalidate();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 跟随ViewPager移动
     * 传入ViewPager.OnPageChangeListener类中
     * onPageScrolled(int position, float positionOffset, int positionOffsetPixels)方法的前两个参数
     */
    public void moveWithViewPager(int position, float positionOffset) {
        current = position;
        offset = positionOffset;
        updateRealDrawPoint();
        invalidate();
    }

    /**
     * dp转化为px
     */
    public float dp2px(float dp) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
