package com.android.DrawLineSample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.util.AttributeSet;


class TestView extends View {

	/*
	public Canvas canvas;
	public Paint p;
	private Bitmap bitmap;
	float x,y;
	int bgColor;

	public TestView(Context context) {
		super(context);
		bgColor = Color.WHITE;               //设置背景颜色
		bitmap = Bitmap.createBitmap(480, 854, Bitmap.Config.ARGB_8888);    //设置位图，线就画在位图上面，第一二个参数是位图宽和高
		canvas=new Canvas();         
		canvas.setBitmap(bitmap);       
		p = new Paint(Paint.DITHER_FLAG);
		p.setAntiAlias(true);                //设置抗锯齿，一般设为true
		p.setColor(Color.RED);              //设置线的颜色
		p.setStrokeCap(Paint.Cap.ROUND);     //设置线的类型
		p.setStrokeWidth(8);                //设置线的宽度
		
	}
    
	
	//触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_MOVE) {    //拖动屏幕
			canvas.drawLine(x, y, event.getX(), event.getY(), p);   //画线，x，y是上次的坐标，event.getX(), event.getY()是当前坐标
			invalidate();
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {    //按下屏幕
			x = event.getX();				
			y = event.getY();
			canvas.drawPoint(x, y, p);                //画点
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {    //松开屏幕
		
		}
		x = event.getX();   //记录坐标
		y = event.getY();
		return true;
	}
	
	@Override
	public void onDraw(Canvas c) {			    		
		c.drawBitmap(bitmap, 0, 0, null);	      
	}*/
	Paint paint = null;
	Bitmap originalBitmap = null;
	Bitmap new1_Bitmap = null;
	Bitmap new2_Bitmap = null;
	float startX = 0,startY = 0;
	float clickX = 0,clickY = 0;
	boolean isMove = true;
	//boolean isLine=false,isDot=false;
	boolean isClear = false;
	int color=Color.RED;
	float strokeWidth=10.0f;
	public TestView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		originalBitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.iv1).copy(Bitmap.Config.ARGB_8888,true);
		new1_Bitmap = Bitmap.createBitmap(originalBitmap);
	}

	public void clear(){
		isClear = true;
		new2_Bitmap = Bitmap.createBitmap(originalBitmap);
		invalidate();
	}
	/*public void brush(){
		strokeWidth=20.0f;
	}*/
	public void eraser(){
		color=Color.WHITE;
		strokeWidth=80.0f;
	}
	/*public void colors(){
		isClear=false;
		color=Color.RED;
	}*/
	public void colors(int paintColor) {
		/*switch (paintColor){
			case 0:
				color=SelectActivity.mPathColors[0];
				break;
			case 1:
				color=SelectActivity.mPathColors[1];
				break;
			case 2:
				color=SelectActivity.mPathColors[2];
				break;
			case 3:
				color=SelectActivity.mPathColors[3];
				break;
			case 4:
				color=SelectActivity.mPathColors[4];
				break;
			case 5:
				color=SelectActivity.mPathColors[5];
				break;
			case 6:
				color=SelectActivity.mPathColors[6];
				break;
			case 7:
				color=SelectActivity.mPathColors[7];
				break;
			default:
				color=SelectActivity.mPathColors[0];
				break;
		}*/
        color=SelectActivity.mPathColors[paintColor];
	}
    public void width(int position) {
        strokeWidth=  SelectActivity.mPenStrock[position];
    }


	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(HandWriting(new1_Bitmap), 0, 0,null);
	}
	public Bitmap HandWriting(Bitmap o_Bitmap)
	{
		Canvas canvas = null;
		if(isClear) {
			canvas = new Canvas(new2_Bitmap);
		}
		else{
			canvas = new Canvas(o_Bitmap);
		}
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setColor(color);
		paint.setStrokeWidth(strokeWidth);
		paint.setStrokeJoin(Paint.Join.ROUND);
		if(isMove)
		{
			canvas.drawLine(startX, startY, clickX, clickY, paint);
		}
		/*else if(isDot)
		{
			canvas.drawPoint(startX, startY, paint);
		}*/
		startX = clickX;
		startY = clickY;
		if(isClear)
		{
			return new2_Bitmap;
		}
		return o_Bitmap;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		clickX = event.getX();
		clickY = event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			isMove = false;
			//isLine=true;
			invalidate();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			isMove = true;
			//isDot=true;
			invalidate();
			return true;
		}

		return super.onTouchEvent(event);
	}

}
