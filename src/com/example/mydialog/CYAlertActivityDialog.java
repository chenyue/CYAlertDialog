package com.example.mydialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

public class CYAlertActivityDialog extends Activity implements DialogInterface, CYDialogDismissListener {
    /** 主对话框 */
    private CYWorkspace mWorkspace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        mWorkspace = (CYWorkspace) findViewById(R.id.workspace);
        mWorkspace.setCYListener(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "onResume+++++++++++++++++++++++++++++");
    }
    public static class Builder {
        /** context */
        private Context mContext;
        /** dialogClass */
        private Class<? extends Activity> mDialogClass;
        /** 标题 */
        private String mTitle;
        /** builder */
        private static Builder mBuilder;
        
        public Builder() {
            this(CYAlertActivityDialog.class);
        }

        public Builder(Class<? extends Activity> dialogClass) {
            mContext = CYUtils.getInstance().getApplicationContext();
            mDialogClass = dialogClass;
        }
        
        public Builder setTitle(int resId) {
            return setTitle(mContext.getString(resId));
        }
        
        public Builder setTitle(String text) {
            this.mTitle = text;
            return this;
        }
        
        public void show() {
            Runnable runnable = new Runnable() {
                
                @Override
                public void run() {
                    if (mDialogClass == null) {
                        mDialogClass = CYAlertActivityDialog.class;
                    }
                    Context context = CYUtils.getInstance().getApplicationContext();
                    Intent intent = new Intent(context, mDialogClass);
//                    String key = String.valueOf(intent.hashCode());
//                    setBuilder(key, Builder.this);
                    setBuilder(Builder.this);
                    if (!(context instanceof Activity)) {
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                }
            };
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(runnable);
            } else {
                runnable.run();
            }
        }
        /** 省略大多数的set方法 */

        protected static void setBuilder(Builder builder) {
            /**
             * 暂时不知道有什么用
             */
//            synchronized (mBuilder) {
//                mBuilder = builder;
//            }
            mBuilder = builder;
        }
        
        public static Builder getBuilder() {
//            synchronized (mBuilder) {
//                return mBuilder;
//            }
            return mBuilder;
        }
    }

    public void close() {
        finish();
    }
    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dismiss() {
        
    }

    @Override
    public void onClose() {
        this.finish();
    }
}
