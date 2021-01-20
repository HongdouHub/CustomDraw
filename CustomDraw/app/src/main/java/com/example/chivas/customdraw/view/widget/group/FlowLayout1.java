package com.example.chivas.customdraw.view.widget.group;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.example.chivas.customdraw.constants.LogConstants.DRAW_GROUP_TAG;

/**
 *
 * 流程：
 * 1、自定义属性：声明，设置，解析获取自定义值
 * 2、测量：在onMeasure MeasureSpec.AT_MOST/EXACTLY,自身的宽高/child的宽高
 * 3、布局：在onLayout方法里面根据自己规划来确定children的位置
 * 5、处理LayoutParams
 */
public class FlowLayout1 extends ViewGroup {
    private List<View> lineViews;   //每一行的子View
    private List<List<View>> views; //所有的行 一行一行的存储
    private List<Integer> heights;  //每一行的高度

    public FlowLayout1(Context context) {
        super(context);
    }

    public FlowLayout1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1. 测量自己的尺寸
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 2. 为每个子view计算测量的限制信息 Mode/size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

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

        // 遍历所有的子View，对子View进行测量，分配到具体的行
        int childCount = getChildCount();
        int count = 0;
        for (int i = 0; i < childCount; i++) {
            Log.i(DRAW_GROUP_TAG, "i =========================== " + i);
            View childView = getChildAt(i);
            // 3. 测量子View的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            // 4. 获取到当前子View的测量的宽度/高度
            int childWight = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            // 当前的行剩余宽度是否可以容纳下一个子View，
            //   a. 如果放的下，不换行；
            //   b. 如果放不下，换行，保存当前的所有子view，累加行高，当前行的宽度和高度置零
            if ((currentLineWidth + childWight) > widthSize - paddingRight - paddingLeft) { // 换行处理
                count++;
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
            currentLineHeight = Math.max(currentLineHeight, childHeight);
            Log.i(DRAW_GROUP_TAG, "currentLineHeight = " + currentLineHeight);

            // 最后一行
            if (i == childCount - 1) {
                maxWidth = Math.max(maxWidth, currentLineWidth);
                maxHeight += currentLineHeight;
                Log.i(DRAW_GROUP_TAG, "最后一个 maxHeight = " + maxHeight);
                heights.add(currentLineHeight);
                views.add(lineViews);
            }
        }

        // 5. 最终宽高
        int realWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth;
        int realHeight = heightMode == MeasureSpec.EXACTLY ? heightSize + paddingTop + paddingBottom : maxHeight;

        Log.i(DRAW_GROUP_TAG, "最后结果:"
                .concat("maxWidth = " + maxWidth + ", maxHeight = " + maxHeight)
                .concat("\nrealWidth = " + realWidth + ", realHeight = " + realHeight)
        );
        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = views.size(); // 获取行数
        int currX = 0;
        int currY = 0;

        // 大循环，所有的子View 一行一行的布局
        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = views.get(i);    // 取出一行
            int lineHeight = heights.get(i);        // 取出这一行的高度值

            // 遍历当前行的子View
            int size = lineViews.size();
            for (int j = 0; j < size; j++) {
                View child = lineViews.get(j);
                int left = currX;
                int top = currY;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                // 布局当前行的每一个view,确定子view的位置
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
        return p instanceof MyFlowLayoutParams;
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
        public MyFlowLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
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
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
