package com.android.DrawLineSample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DrawLineSample extends Activity {
    /** Called when the activity is first created. */

    private TestView testView = null;
    Button colors,clear,brush,eraser;
    private LinearLayout linearLayout=null;
    private SelectActivity selectActivity=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new TestView(this));
        setContentView(R.layout.main);
        //RelativeLayout rootView= (RelativeLayout) findViewById(R.id.mainRootView);
        testView = (TestView) findViewById(R.id.testviewview);
        linearLayout  = (LinearLayout) findViewById(R.id.ll_btn_group);
        selectActivity = (SelectActivity) findViewById(R.id.strock_color_select);

        colors = (Button) findViewById(R.id.colors);
        clear = (Button) findViewById(R.id.clear);
        brush = (Button) findViewById(R.id.brush);
        eraser = (Button) findViewById(R.id.eraser);

        clear.setOnClickListener(new cClick());
        colors.setOnClickListener(new coClick());
        brush.setOnClickListener(new brClick());
        eraser.setOnClickListener(new eClick());
    }

        class cClick implements OnClickListener {
            public void onClick(View v) {
                testView.clear();
            }
        }
        class coClick implements OnClickListener {
            public void onClick(View v)
            {
                linearLayout.setVisibility(View.GONE);
                //testView.colors();
                selectActivity.setVisibility(View.VISIBLE);
                selectActivity.setCURRENT_TYPE(SelectActivity.COLOR_TYPE);
                selectActivity.setCallback(new SelectActivity.ColorSelectorCallback() {
                    //@Override
                    public void onColorSelectCancel(SelectActivity sender) {
                        selectActivity.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                    //@Override
                    public void onColorSelectChange(SelectActivity sender) {
                        int position=sender.getPenPosition();
                        selectActivity.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        testView.colors(position);
                    }
                });
            }
        }

        class brClick implements OnClickListener {
            public void onClick(View v)
            {
                //testView.brush();
                linearLayout.setVisibility(View.GONE);
                selectActivity.setVisibility(View.VISIBLE);
                selectActivity.setCURRENT_TYPE(SelectActivity.STROCK_TYPE);
                selectActivity.setCallback(new SelectActivity.ColorSelectorCallback() {
                    //@Override
                    public void onColorSelectCancel(SelectActivity sender) {
                        selectActivity.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                    //@Override
                    public void onColorSelectChange(SelectActivity sender) {
                        int position=sender.getPenPosition();
                        selectActivity.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        //selectActivity.setVisibility(View.VISIBLE);
                        //linearLayout.setVisibility(View.GONE);
                        testView.width(position);
                    }
                });
            }
        }
        class eClick implements OnClickListener {
            public void onClick(View v) {
                testView.eraser();
            }
        }

}