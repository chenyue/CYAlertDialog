package com.example.mydialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CYMainPanel extends RelativeLayout {

    private TextView mTextView;

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
//        mTextView = (TextView) findViewById(R.id.title);
    }

}
