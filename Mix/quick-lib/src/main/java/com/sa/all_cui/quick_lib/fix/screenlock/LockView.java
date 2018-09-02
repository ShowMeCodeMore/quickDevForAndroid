package com.sa.all_cui.quick_lib.fix.screenlock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.sa.all_cui.quick_lib.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.spacing;

/**
 * Created by all-cui on 2017/9/18.
 */

public class LockView extends ViewGroup {
    /**
     * 节点定义
     **/
    private final List<PointView> pointList = new ArrayList<>();
    private float x;//当前手指所在位置的横坐标
    private float y;//当前手指所在位置的纵坐标

    private float mPointSize;//节点大小，如果不为0，则忽略内边距和间距属性
    private float mPointAreaExpand;//对节点触摸区进行扩展
    private float mPadding;//内边距
    private float mSpacing;//节点间隔距离

    //自动连接中间点
    private boolean isAutoLinkForMid;



    //连线的画笔
    private Paint mPaint;
    //监听事件回调
    private IGestureCallback mCallback;

    //java代码中创建会调用
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LockView(Context context) {
        this(context, null);
    }

    //xml中使用view会调用
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //自行调用
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    //自行调用
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化view
     *
     * @param context
     * @param attrSet      属性集合
     * @param defStyleAttr 属性
     * @param defStyleRes  样式资源
     */
    private void init(@NonNull Context context,
                      @Nullable AttributeSet attrSet,
                      @AttrRes int defStyleAttr,
                      @StyleRes int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrSet, R.styleable.LockView, defStyleAttr, defStyleRes);
        Drawable mPointSrc = typedArray.getDrawable(R.styleable.LockView_lock_pointSrc);
        Drawable mPointHighlightSrc = typedArray.getDrawable(R.styleable.LockView_lock_pointLightSrc);
        mPointAreaExpand = typedArray.getDimension(R.styleable.LockView_lock_pointAreaExpand, 0);
        int mPointOnAnim = typedArray.getResourceId(R.styleable.LockView_lock_pointOnAnim, 0);
        mPointSize = typedArray.getDimension(R.styleable.LockView_lock_pointSize, 0);
        int mLineColor = typedArray.getInt(R.styleable.LockView_lock_lineColor, Color.argb(0, 0, 0, 0));
        float mLineWidth = typedArray.getDimension(R.styleable.LockView_lock_lineWidth, 0);
        mPadding = typedArray.getDimension(R.styleable.LockView_lock_padding, 0);
        mSpacing = typedArray.getDimension(R.styleable.LockView_lock_spacing, 0);
        isAutoLinkForMid = typedArray.getBoolean(R.styleable.LockView_lock_autoLink, false);

        boolean mEnableVibrator = typedArray.getBoolean(R.styleable.LockView_lock_enableVibrate, false);
        int mVibratorTime = typedArray.getInt(R.styleable.LockView_lock_vibrateTime, 20);


        typedArray.recycle();
        //震动管理器
        Vibrator vibrator = null;
        //初始化震动器
        if (mEnableVibrator && !isInEditMode()) {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }

        //初始化画笔 DITHER_FLAG 抖动模式
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(mLineColor);
        mPaint.setAntiAlias(true);

        for (int i = 0; i < 9; i++) {
            PointView point = new PointView(context,
                    mPointSrc,
                    mPointHighlightSrc,
                    mPointOnAnim,
                    i + 1,
                    vibrator,
                    true,
                    mVibratorTime);
            addView(point);
        }

        //清除Flag   ViewGroup默认透明背景不会调用draw方法
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int size = measureSize(widthMeasureSpec);
        setMeasuredDimension(size, size);
    }

    private int measureSize(int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.UNSPECIFIED:
            default:
                return 0;
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            if (mPointSize > 0) { // 如果设置nodeSize值，则将节点绘制在九等分区域中心
                float areaWidth = (right - left) / 3;
                for (int n = 0; n < 9; n++) {
                    PointView node = (PointView) getChildAt(n);
                    // 获取3*3宫格内坐标
                    int row = n / 3;
                    int col = n % 3;
                    // 计算实际的坐标
                    int l = (int) (col * areaWidth + (areaWidth - mPointSize) / 2);
                    int t = (int) (row * areaWidth + (areaWidth - mPointSize) / 2);
                    int r = (int) (l + mPointSize);
                    int b = (int) (t + mPointSize);
                    node.layout(l, t, r, b);

//                    LogUtils.i("第"+n+"个"+"left:"+left+"\r\n"+l+"right:"+right+"\r\n"+r+"top:"+top+"\r\n"+t+"bottom:"+bottom+"\r\n"+b+
//                            "areaWidth"+areaWidth);
                }
            } else { // 否则按照分割边距布局，手动计算节点大小
                float nodeSize = (right - left - mPadding * 2 - spacing * 2) / 3;
                for (int n = 0; n < 9; n++) {
                    PointView node = (PointView) getChildAt(n);
                    // 获取3*3宫格内坐标
                    int row = n / 3;
                    int col = n % 3;
                    // 计算实际的坐标，要包括内边距和分割边距
                    int l = (int) (mPadding + col * (nodeSize + spacing));
                    int t = (int) (mPadding + row * (nodeSize + spacing));
                    int r = (int) (l + nodeSize);
                    int b = (int) (t + nodeSize);
                    node.layout(l, t, r, b);
                }
            }
        }
    }

    /**
     * 处理手势事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //实时记录用户坐标
                x = event.getX();
                y = event.getY();
                PointView currentNode = getPointAt(x, y);

                if (currentNode != null && !currentNode.isHighLighted()) { // 碰触了新的未点亮节点
                    if (pointList.size()> 0) { // 之前有点亮的节点
                        if (isAutoLinkForMid) { // 开启了中间节点自动连接
                            PointView lastPoint = pointList.get(pointList.size() - 1);
                            PointView middlePoint = getPointBetween(lastPoint, currentNode);
                            if (middlePoint != null && !middlePoint.isHighLighted()) { // 存在中间节点没点亮
                                // 点亮中间节点
                                middlePoint.setHighLighted(true, true);
                                pointList.add(middlePoint);
                                handleOnPointConnectedCallback();
                            }
                        }
                    }
                    // 点亮当前触摸节点
                    currentNode.setHighLighted(true, false);
                    pointList.add(currentNode);
//                    LogUtils.i("x:"+x+"\r\ny:"+y+"\r\nPointView:"
//                            +currentNode.getNumber());

//                    for (int i=0;i<pointList.size();i++){
//                        LogUtils.i("list:"+pointList.get(i).getNumber());
//                    }
                    handleOnPointConnectedCallback();
                }
                // 有点亮的节点才重绘
                if (pointList.size() > 0) {
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (pointList.size() > 0) {
                    //绘制完成
                    handleOnPointFinishedCallback();
                    //清除状态
                    pointList.clear();
                    for (int i = 0; i < getChildCount(); i++) {
                        PointView point = (PointView) getChildAt(i);
                        point.setHighLighted(false, false);
                    }

                    //通知重绘
                    invalidate();
                }
                break;
            default:
                break;
        }

        return true;
    }

    /*
    * 绘制控件
    * */
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制已有连线
        final int size = pointList.size();

        for (int i = 1; i < size; i++) {
            PointView firstPoint = pointList.get(i - 1);
            PointView secondPoint = pointList.get(i);
            canvas.drawLine(firstPoint.getCenterX(), firstPoint.getCenterY(),
                    secondPoint.getCenterX(), secondPoint.getCenterY(), mPaint);
        }
        //如果已经有亮的点，则在亮点和手指之间绘制连线
        if (size > 0) {
            PointView lastPoint = pointList.get(size - 1);
            canvas.drawLine(lastPoint.getCenterX(), lastPoint.getCenterY(), x, y, mPaint);
        }
    }

    //生成节点数字列表
    private int[] generateNumbers() {
        final int size = pointList.size();
        final int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            PointView point = pointList.get(i);
            numbers[i] = point.getNumber();
        }

        return numbers;
    }

    /**
     * 每连接一个点
     */
    private void handleOnPointConnectedCallback() {
        if (mCallback != null) {
            mCallback.onPointConnected(generateNumbers());
        }
    }

    /**
     * 完成手势
     */
    private void handleOnPointFinishedCallback() {
        if (mCallback != null) {
            mCallback.onGestureFinished(generateNumbers());
        }
    }

    /**
     * 获取给定坐标点的Node，返回null表示当前手指在两个Node之间
     */
    private PointView getPointAt(float x, float y) {
        for (int n = 0; n < getChildCount(); n++) {
            final PointView point = (PointView) getChildAt(n);
            if (!(x >= point.getLeft() - mPointAreaExpand && x < point.getRight() + mPointAreaExpand)) {
                continue;
            }
            if (!(y >= point.getTop() - mPointAreaExpand && y < point.getBottom() + mPointAreaExpand)) {
                continue;
            }
            return point;
        }
        return null;
    }

    /**
     * 获取两个Point中间的Point，返回null表示没有中间Point
     */
    @Nullable
    private PointView getPointBetween(@NonNull PointView na, @NonNull PointView nb) {
        if (na.getNumber() > nb.getNumber()) { // 保证 na 小于 nb
            PointView nc = na;
            na = nb;
            nb = nc;
        }
        if (na.getNumber() % 3 == 1 && nb.getNumber() - na.getNumber() == 2) { // 水平的情况
            return (PointView) getChildAt(na.getNumber());
        } else if (na.getNumber() <= 3 && nb.getNumber() - na.getNumber() == 6) { // 垂直的情况
            return (PointView) getChildAt(na.getNumber() + 2);
        } else if ((na.getNumber() == 1 && nb.getNumber() == 9) || (na.getNumber() == 3 && nb.getNumber() == 7)) { // 倾斜的情况
            return (PointView) getChildAt(4);
        } else {
            return null;
        }
    }


    //设置监听事件
    public void setCallback(IGestureCallback callback) {
        if (callback != null) {
            this.mCallback = callback;
        }
    }
}
