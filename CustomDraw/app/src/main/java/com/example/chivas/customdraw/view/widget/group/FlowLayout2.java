package com.example.chivas.customdraw.view.widget.group;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.OverScroller;

import com.example.chivas.customdraw.R;
import com.example.chivas.customdraw.utils.DpUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.chivas.customdraw.constants.LogConstants.DRAW_GROUP_TAG;

public class FlowLayout2 extends ViewGroup {
    private static final int SCROLL_BACK_TIME = 1000;

    private List<View> lineViews;   // 每一行的子View
    private List<List<View>> views; // 所有的行 一行一行的存储
    private List<Integer> heights;  // 每一行的高度

    private int mMeasureWidth;       // 代表本身的测量宽度
    private int mMeasureHeight;      // 代表本身的测量高度
    private int mRealWidth;          // 表示内容的宽度
    private int mRealHeight;         // 表示内容的高度

    private boolean mScrollable = false;    // 是否能滑动
    private boolean mIsBeingDragged = false;// 正在滑动中
    private int mTouchSlop;                 // 最小滑动距离，默认48，用来判断是不是一次滑动
    private float mLastInterceptX = 0;
    private float mLastInterceptY = 0;
    private float mLastY = 0;

    //  private Scroller mScroller;             // 让滑动变顺畅，辅助类（里面保存了目标对象要移动的距离，时间等属性）
    private OverScroller mScroller;             // 比Scroller多了一个功能：超出部分会往回滚
    private VelocityTracker mVelocityTracker;   // 速度跟踪器（用于追踪手指滑动过程中的瞬时速度），补充滑动过猛时的惯性效果

    private int mMinimumVelocity;               // 允许执行fling （抛）的最小速度值
    private int mMaximumVelocity;               // 允许执行fling （抛）的最大速度值

    public FlowLayout2(Context context) {
        this(context, null);
    }

    public FlowLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 系统中关于视图的各种特性的常量记录对象，像超时、尺寸、距离等
        ViewConfiguration configuration = ViewConfiguration.get(context);

        // 获取系统滑动距离的最小值，大于该值可以认为滑动
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        Log.i(DRAW_GROUP_TAG, "FlowLayout2: mTouchSlop= " + mTouchSlop);

        mScroller = new OverScroller(context);

        // 获得允许执行fling （抛）的最小速度值
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        //  获得允许执行fling （抛）的最大速度值
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        // 即判断设备是否有返回、主页、菜单键等实体按键（非虚拟按键）
//        configuration.hasPermanentMenuKey();

        // 常用静态方法
        // 获得敲击超时时间，如果在此时间内没有移动，则认为是一次点击
//        ViewConfiguration.getTapTimeout();

        // 双击间隔时间，在该时间内被认为是双击
//        ViewConfiguration.getDoubleTapTimeout();

        // 长按时间，超过此时间就认为是长按
//        ViewConfiguration.getLongPressTimeout();

        // 重复按键间隔时间
//        ViewConfiguration.getKeyRepeatTimeout();
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            // 享元模式，缓存池大小默认2个
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            // 释放追踪器
            mVelocityTracker.recycle();
            // 重置并回收内存
            mVelocityTracker = null;
        }
    }

    private void init() {
        views = new ArrayList<>();
        lineViews = new ArrayList<>();
        heights = new ArrayList<>();
    }

    private String getModeString(int mode) {
        String result = "Unknown";
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                result = "UNSPECIFIED";
                break;
            case MeasureSpec.EXACTLY:
                result = "EXACTLY";
                break;
            case MeasureSpec.AT_MOST:
                result = "AT_MOST";
                break;
            default:
        }
        return result;
    }

    public void fling(int velocityY) {
        if (getChildCount() > 0) {
            int height = mMeasureHeight;
            int bottom = mRealHeight;

            mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0,
                    Math.max(0, bottom - height), 0, height / 2);

            postInvalidateOnAnimation();
        }
    }

    /**
     * 调用invalidate()（UI线程）或者postInvalidate（）使View（ViewGroup）树重绘
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        // 用于判断移动过程是否完成
        if (mScroller.computeScrollOffset()) {
            // scrollTo vs scrollBy

            //mCurrY = oldScrollY + dy*scale;
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        float xInterceptX = ev.getX();
        float yInterceptY = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastInterceptX = xInterceptX;
                mLastInterceptY = yInterceptY;
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = xInterceptX - mLastInterceptX;
                float dy = yInterceptY - mLastInterceptY;
                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > mTouchSlop) {
                    intercepted = true;//表示本身需要拦截处理
                    mIsBeingDragged = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        mLastInterceptX = xInterceptX;
        mLastInterceptY = yInterceptY;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//处理滑动
        if (!mScrollable) {
            return super.onTouchEvent(event);
        }

        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        float currY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastY = currY;
                mIsBeingDragged = !mScroller.isFinished();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY - currY;//本次手势滑动了多大距离
//                int oldScrollY = getScrollY();//已经偏移了的距离
//                int scrollY = oldScrollY + (int)dy;//这是本次需要偏移的距离 = 之前已经偏移了的距离 + 本次手势滑动的距离
//                if(scrollY < 0){
//                    scrollY = 0;
//                }
//                if(scrollY > mRealHeight - mMeasureHeight){
//                    scrollY = mRealHeight - mMeasureHeight;
//                }
//                scrollTo(0,scrollY);
                if(mIsBeingDragged){
                    if (dy > 0) {
                        dy += mTouchSlop;
                    } else {
                        dy -= mTouchSlop;
                    }
                }

                Log.i(DRAW_GROUP_TAG, "onTouchEvent: dy= " + dy);
                mScroller.startScroll(0, mScroller.getFinalY(), 0, (int) dy);//mCurrY = oldScrollY + dy*scale;

                /**
                 * ViewGroup： onDraw（）-->dispatchDraw()-->drawChild()-->child.computeScroll()
                 * View：      onDraw（）-->dispatchDraw()【空方法】
                 *       解释：invalidate是重绘整个View树或者ViewGroup树，所以当View重绘时其所在父容器也会重绘
                 */
                invalidate(); // 触发onDraw，最终触发View的computeScroll
                mLastY = currY;
                break;
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(SCROLL_BACK_TIME, mMaximumVelocity);

                // 分别获取x轴和y轴方向的1s时间内的平均速度
//                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();

                if ((Math.abs(yVelocity) > mMinimumVelocity)) {
                    fling(-yVelocity);
                } else if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0,
                        (mRealHeight - mMeasureHeight))) {
                    postInvalidateOnAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1. 测量自己的尺寸
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 2. 为每个子view计算测量的限制信息 Mode/size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(DRAW_GROUP_TAG, "onMeasure: widthSize = " + widthSize + ", heightSize = " + heightSize);

        mMeasureWidth = widthSize;
        mMeasureHeight = heightSize;

        Log.i(DRAW_GROUP_TAG, "onMeasure: "
                .concat("widthMode= " + getModeString(widthMode))
                .concat("heightMode= " + getModeString(heightMode))
        );

        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingRight = getPaddingRight();
        int paddingLeft = getPaddingLeft();

        // 初始化参数列表
        init();

        int currentLineWidth = 0;   // 当前行宽度是当前行子view的宽度之和
        int currentLineHeight = 0;  // 当前行高度是当前行所有子View中高度的最大值

        int maxWidth = 0;                                  // 所有行中宽度的最大值
        int maxHeight = paddingTop + getPaddingBottom();   // 所有行中高度的累加

        // 3. 遍历所有的子View，对子View进行测量，分配到具体的行
        int childCount = getChildCount();
        int count = 0;
        for (int i = 0; i < childCount; i++) {
            Log.i(DRAW_GROUP_TAG, "i =========================== " + i);
            View childView = getChildAt(i);
            // 4. 测量子View的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            // 5. 获取到当前子View的测量的宽度/高度
            int childWight = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            LayoutParams lp = (LayoutParams) childView.getLayoutParams();

            // 当前的行剩余宽度是否可以容纳下一个子View，
            //   a. 如果放的下，不换行；
            //   b. 如果放不下，换行，保存当前的所有子view，累加行高，当前行的宽度和高度置零
            if ((currentLineWidth + childWight) > widthSize - paddingRight - paddingLeft) { // 换行处理
                count++;

                // 如果一行只有一个元素
                if (lineViews.size() == 1 &&
                        lineViews.get(0).getLayoutParams().height == WindowManager.LayoutParams.MATCH_PARENT) {
                    currentLineHeight = DpUtils.dp2px(150); // 设置最大值
                }

                views.add(lineViews);
                lineViews = new ArrayList<>(); // 创建新的一行
                maxWidth = Math.max(maxWidth, currentLineWidth);
                maxHeight += currentLineHeight; // 累加高度

                Log.i(DRAW_GROUP_TAG, "换行 第： " + count + "行\n"
                        .concat("currentLineWidth = " + currentLineWidth + ", currentLineHeight = " + currentLineHeight)
                        .concat("\nmaxWidth = " + maxWidth + ", maxHeight = " + maxHeight)
                );

                heights.add(currentLineHeight);
                currentLineHeight = 0;
                currentLineWidth = 0;
            }

            // 不换行
            lineViews.add(childView);
            currentLineWidth += childWight;

            // 暂时先不处理 LayoutParams_height == match_parent
            if (lp.height != LayoutParams.MATCH_PARENT) {
                currentLineHeight = Math.max(currentLineHeight, childHeight);
                Log.i(DRAW_GROUP_TAG, "currentLineHeight = " + currentLineHeight);
            } else {
                Log.w(DRAW_GROUP_TAG, "currentLineHeight skip because it is match_parent");
            }

            // 最后一行
            if (i == childCount - 1) {
                maxWidth = Math.max(maxWidth, currentLineWidth);
                maxHeight += currentLineHeight;
                Log.i(DRAW_GROUP_TAG, "最后一个 maxHeight = " + maxHeight);
                heights.add(currentLineHeight);
                views.add(lineViews);
            }
        }

        // 6. 重新测量一次 LayoutParams_height == match_parent
        reMeasureChild(widthMeasureSpec, heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            maxHeight = Math.max(maxHeight, heightSize + paddingTop + paddingBottom);
        }

        // 7. 最终宽高
        mRealWidth = (widthMode == MeasureSpec.EXACTLY) ? widthSize : maxWidth;
        mRealHeight = maxHeight;

        mScrollable = (mRealHeight > heightSize); // 1080 * 1776

        Log.i(DRAW_GROUP_TAG, "最后结果:"
                .concat("mScrollable = " + mScrollable) // false
                .concat("\nmaxWidth = " + maxWidth + ", maxHeight = " + maxHeight) // 1080 * 8112
                .concat("\nrealWidth = " + mRealWidth + ", mRealHeight = " + mRealHeight) // 1080 * 1848
        );

        // 8. 设置最终值
        setMeasuredDimension(mRealWidth, mRealHeight);
    }

    // 针对子View测量时，发现的 match_parent 的情况，需要重新测量
    private void reMeasureChild(int widthMeasureSpec, int heightMeasureSpec) {
        int viewSize = views.size();
        for (int i = 0; i < viewSize; i++) {
            // 每一行的子View
            List<View> lineView = views.get(i);
            // 每一行的子View
            int lineSize = lineView.size();
            // 每一行行高
            int lineHeight = heights.get(i);

            for (int j = 0; j < lineSize; j++) {
                View childView = lineView.get(j);
                LayoutParams lp = (LayoutParams) childView.getLayoutParams();

                if (lp.height == LayoutParams.MATCH_PARENT) {

                    int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
                    int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lineHeight);
                    childView.measure(childWidthSpec, childHeightSpec);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 1. 获取行数
        int lineCount = views.size();
        int currX = 0;
        int currY = 0;

        // 2. 大循环，所有的子View 一行一行的布局
        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = views.get(i);    // 取出一行
            int lineHeight = heights.get(i);        // 取出这一行的高度值

            // 3. 遍历当前行的子View
            int size = lineViews.size();
            for (int j = 0; j < size; j++) {
                View child = lineViews.get(j);
                int left = currX;
                int top = currY;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();

                // 4. 布局当前行的每一个view,确定子view的位置
                child.layout(left, top, right, bottom);
                // 确定下一个View的left
                currX += child.getMeasuredWidth();
            }

            // 重置另外一行的高度和left
            currY += lineHeight;
            currX = 0;
        }
    }

    /**
     * 检查layoutParams参数是否合法
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof MyFlowLayoutParams;
    }

    /**
     * 对传入的LayoutParams进行转化
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyFlowLayoutParams(getContext(), attrs);
    }

    /**
     * 对传入的LayoutParams进行转化
     */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MyFlowLayoutParams(p);
    }

    /**
     * 生成默认的LayoutParams
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MyFlowLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public static class MyFlowLayoutParams extends MarginLayoutParams {
        int layoutGravity = -1;

        public MyFlowLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.FlowLayout_Layout);
            try {
                layoutGravity = a.getInt(R.styleable.FlowLayout_Layout_android_layout_gravity, -1);
            } finally {
                a.recycle();
            }
        }

        public MyFlowLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyFlowLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public MyFlowLayoutParams(LayoutParams source) {
            super(source);
        }

        @Override
        public String toString() {
            return "MyFlowLayoutParams{" +
                    "gravity=" + layoutGravity +
                    ", bottomMargin=" + bottomMargin +
                    ", leftMargin=" + leftMargin +
                    ", rightMargin=" + rightMargin +
                    ", topMargin=" + topMargin +
                    ", height=" + height +
                    ", width=" + width +
                    "} ";
        }
    }
}
