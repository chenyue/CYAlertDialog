package com.example.mydialog;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;
import com.CY.CYLogTool.*;

/**
 * workspace 对话框可以在上面移动的
 * 
 * @author chenyue06
 * 
 */
public class CYWorkspace extends FrameLayout {

    private int mLastY;
    private Context mContext;
    private Scroller mScroller;
    private int detalY = 0;
    private VelocityTracker mVelocity;
    private int mMaximumVelocity;
    private CYDialogDismissListener mListener;
    private CYMainPanel mMainPanel;
    private View mRoot;
    private int sum = 0;
    /**  */
    private static final int TOUCH_STATE_SCROLLING_DOWN = 0;

    /**  */
    private static final int TOUCH_STATE_SCROLLING_UP = 1;

    /**  */
    private int mTouchState = TOUCH_STATE_SCROLLING_DOWN;

    /**
     * 1、先画出了一个panel 动画略搓 2、解决随手势问题 (先实现能随手势动) 在Y方向上的随手势即可 ding 3、灵活 添加 里面的样式 4、消息处理等
     * 
     * @param context
     */
    public CYWorkspace(Context context) {
        super(context);
        init(context);
    }

    public CYWorkspace(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CYWorkspace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mScroller = new Scroller(mContext);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        mMainPanel = (CYMainPanel) LayoutInflater.from(mContext).inflate(R.layout.alert_panel, null, false);
        // read width from xml
        LayoutParams params = new LayoutParams(800, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(mMainPanel, params);
        doEnterAnimation(mMainPanel);
        // invalidate();
    }

    public void setCYListener(CYDialogDismissListener listener) {
        mListener = listener;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            scrollTo(x, y);
            postInvalidate();
        }
    }

    private void doEnterAnimation(View targetView) {
        int start = getResources().getDisplayMetrics().heightPixels;
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator anim = ObjectAnimator.ofFloat(targetView, "translationY", -(start + 800) / 2, 0);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(500);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(targetView, "translationX", -50, 0);
        anim1.setInterpolator(new AccelerateInterpolator());
        anim1.setDuration(500);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(targetView, "translationY", 0, -20);
        anim2.setInterpolator(new DecelerateInterpolator());
        anim2.setDuration(50);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(targetView, "translationY", -20, 0);
        anim3.setInterpolator(new AccelerateInterpolator());
        anim3.setDuration(50);
        set.play(anim).with(anim1).before(anim2);
        set.play(anim2).before(anim3);
        set.start();
    }

    // @Override
    // protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // measureChildren(widthMeasureSpec, heightMeasureSpec);
    // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // }
    //
    // @Override
    // protected void onLayout(boolean changed, int l, int t, int r, int b) {
    // int count = getChildCount();
    // for (int i = 0; i < count; i++) {
    // View childView = getChildAt(i);
    // int height = childView.getMeasuredHeight();
    // int width = childView.getMeasuredWidth();
    // int middleX = (r - l) / 2;
    // int middleY = (b - t) / 2;
    // getChildAt(i).layout(middleX - width / 2, middleY - height / 2, middleX + width / 2, middleY + height / 2);
    // }
    // }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int y = (int) event.getY();
        if (mVelocity == null) {
            mVelocity = VelocityTracker.obtain();
        }
        mVelocity.addMovement(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.forceFinished(true);
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                detalY = (int) (y - mLastY);
                if (detalY > 0) {
                    int diff = -detalY;
                    scrollBy(0, (int) (0.1 * diff)); // down
                    mTouchState = TOUCH_STATE_SCROLLING_DOWN;
                } else {
                    int diff = -detalY;
                    scrollBy(0, (int) (0.005 * diff)); // up
                    mTouchState = TOUCH_STATE_SCROLLING_UP;
                    sum += 0.005 * diff;
                }
                // int diff = Math.abs(detalY);
                // if (detalY > 0) {
                // diff = -diff;
                // }
                // if (Math.abs(diff) >= 1) {
                // scrollBy(0, (int) (0.1 * diff));
                Log.e("upupup", "up distance:" + sum + " " + (y - mLastY));
                CYLogUtils.fullLogE(this.getClass(), "up distance", sum, "start-end", (y - mLastY));
                // }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                final VelocityTracker velocityTracker = mVelocity;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                if (mVelocity != null) {
                    mVelocity.recycle();
                    mVelocity = null;
                }
                if (mTouchState == TOUCH_STATE_SCROLLING_UP) {
                    doAnimation(mMainPanel, sum);
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void doAnimation(CYMainPanel targetView, float detal) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(targetView, "translationY", 0, -detal);
        anim1.setInterpolator(new AccelerateInterpolator());
        anim1.setDuration(500);
        anim1.start();
        anim1.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                sum = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
    }

    // private boolean isCanMoveUp(int detalY2) {
    // if (detalY2 > 30) {
    // return false;
    // } else {
    // return true;
    // }
    // }

}
