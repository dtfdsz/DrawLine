package com.android.DrawLineSample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;

import com.android.DrawLineSample.R;

public class SelectActivity extends RelativeLayout {
    private static final int CANCEL_BUTTON_ID = 0x0020;
    public static final int COLOR_TYPE = 0x0021;
    public static final int STROCK_TYPE = 0x0022;
    private int CURRENT_TYPE = COLOR_TYPE;

    private int m_penPosition = 0;
    private int m_penPositionTemp = 0;
    private ColorSelectorCallback m_callback = null;

    public void setCURRENT_TYPE(int CURRENT_TYPE) {
        this.CURRENT_TYPE = CURRENT_TYPE;
    }
    private OnClickListener m_clickListener = new OnClickListener() {
        //@Override
        public void onClick(View v) {
            switch (v.getId()) {

                case CANCEL_BUTTON_ID:
                    SelectActivity.this.setVisibility(View.GONE);
                    m_penPosition = m_penPositionTemp;
                    if (null != m_callback) {
                        m_callback.onColorSelectCancel(SelectActivity.this);
                    }
                    break;

                default:
                    Object tag = v.getTag();
                    if (null != tag && tag instanceof Integer) {
                        m_penPosition = ((Integer) tag).intValue();
                    }

                    if (null != m_callback) {
                        m_callback.onColorSelectChange(SelectActivity.this);
                    }
                    break;
            }
        }
    };

    public SelectActivity(Context context) {
        this(context, null);
    }

    public SelectActivity(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectActivity(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }
    public static final int[] mPathColors =
            {
                    //Color.argb(128, 32, 79, 140),1
                    //Color.argb(128, 48, 115, 170),2
                    //Color.argb(128, 139, 26, 99),3
                    //Color.argb(128, 112, 101, 89),4
                    //Color.argb(128, 40, 36, 37),5
                    //Color.argb(128, 226, 226, 226),6
                    //Color.argb(128, 219, 88, 50),7
                    //Color.argb(128, 129, 184, 69)8
                    Color.argb(128, 255, 182, 193),//1
                    Color.argb(128, 255, 69, 0),//2
                    Color.argb(128, 255, 165, 0),//3
                    Color.argb(128, 46, 139, 87),//4
                    Color.argb(128, 95, 158, 160),//5
                    Color.argb(128, 65, 105, 255),//6
                    Color.argb(128, 186, 85, 211),//7
                    Color.argb(128, 0, 0, 0)

            };
    public static final int[] mPenStrock =
            {
                    10,
                    20,
                    30,
                    35,
                    45,
                    55,
                    65,
                    80

            };
    public void initialize(Context context) {
        this.setBackgroundColor(Color.WHITE);

        final int width = 90;
        final int height = 90;
        final int margin = 13;
        final int length = mPathColors.length;
        int left = 10;

        for (int i = 0; i < length; ++i) {
            int color = mPathColors[i];

            ImageView imgBtn = new ImageView(context);
            if (CURRENT_TYPE == STROCK_TYPE) {
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(mPenStrock[i]);
                canvas.drawLine(0, height, width, 0, paint);
                imgBtn.setImageBitmap(bitmap);
                imgBtn.setBackgroundColor(Color.WHITE);

            } else {
                imgBtn.setBackgroundColor(color);

            }


            imgBtn.setOnClickListener(m_clickListener);
            imgBtn.setTag(i);

            LayoutParams params = new LayoutParams(width, height);
            params.setMargins(left, 30, 0, 0);
            params.addRule(Gravity.CENTER_VERTICAL);
            left += (margin + width);

            this.addView(imgBtn, params);
        }
        // Cancel button.
        Button btnCancel = new Button(context);
        btnCancel.setText("cancel");
        btnCancel.setId(CANCEL_BUTTON_ID);
        btnCancel.setTextSize(8);
        btnCancel.setOnClickListener(m_clickListener);
        LayoutParams btnCancelparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        btnCancelparams.addRule(Gravity.CENTER);
        left += 10;
        btnCancelparams.setMargins(left, 30, 0, 0);
        this.addView(btnCancel, btnCancelparams);
    }
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void setCallback(ColorSelectorCallback callback) {
        m_callback = callback;
    }

    public int getPenPosition() {
        return m_penPosition;
    }

    public int getCURRENT_TYPE() {
        return CURRENT_TYPE;
    }
    public interface ColorSelectorCallback {
        public void onColorSelectCancel(SelectActivity sender);

        public void onColorSelectChange(SelectActivity sender);
    }
    public void setStrockORColor(int type) throws Exception {
        try {
            CURRENT_TYPE = type;
            postInvalidateDelayed(200);
        } catch (Exception e) {
            throw new Exception("need COLOR_TYPE or STROCK_TYPE");
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initialize(getContext());
    }
}
