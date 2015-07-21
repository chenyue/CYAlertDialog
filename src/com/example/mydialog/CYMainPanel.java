package com.example.mydialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class CYMainPanel extends ViewGroup {

    public CYMainPanel(Context context) {
        super(context);
        init();
    }
    public CYMainPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CYMainPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                int x = 
                break;
            case MotionEvent.ACTION_MOVE:
//                setTranslationY();
                break;
                
        }
        return super.onTouchEvent(event);
    }
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        setTranslationY(t-oldt);
    }
}
