package fengjie.baway.com.fenjgie20170213;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by FengJIe on 2017/2/13.
 */

public class CustomView extends View {

    private String text_circle;
    private int circle_colour;

    private Path top_path;
    private Path inner_path;
    private Region top_region;
    private Region inner_region;
    private Paint top_paint;
    private Paint inner_paint;
    private Paint textPaint;
    private Paint max_paint;
    private Region max_region;
    private Path max_path;
    private int top_circle_r;
    private int inner_circle;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获得自定义属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        text_circle = typedArray.getString(R.styleable.CustomView_circle_text);
        circle_colour = typedArray.getColor(R.styleable.CustomView_circle_colour, Color.BLACK);
        top_circle_r = typedArray.getInteger(R.styleable.CustomView_top_circle_r,200);
        inner_circle = typedArray.getInteger(R.styleable.CustomView_inner_circle_r, 100);

        //初始化画笔
        ininPaints();
        //初始化路径
        ininPaths();
        //初始化Region
        initRegion();
    }

    private void initRegion() {
        top_region = new Region();
        inner_region = new Region();
        max_region = new Region();
    }

    private void ininPaths() {
        top_path = new Path();
        inner_path = new Path();
        max_path = new Path();
    }

    private void ininPaints() {
        max_paint = new Paint();
        max_paint.setColor(Color.BLACK);
        max_paint.setStyle(Paint.Style.FILL);
        max_paint.setStrokeWidth(10f);
        //外圆画笔
        top_paint = new Paint();
        top_paint.setColor(circle_colour);
        top_paint.setStyle(Paint.Style.FILL);
        top_paint.setStrokeWidth(10f);

        //内圆画笔
        inner_paint = new Paint();
        inner_paint.setStyle(Paint.Style.FILL);
        inner_paint.setColor(Color.RED);
        inner_paint.setStrokeWidth(10f);


        // 创建画笔
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(50);              // 设置字体大小


    }

    //确定尺寸
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        top_path.addCircle(getWidth()/2,getHeight()/2,top_circle_r, Path.Direction.CW);
        inner_path.addCircle(getWidth()/2,getHeight()/2,inner_circle, Path.Direction.CW);
        max_path.addCircle(getWidth()/2,getHeight()/2,300, Path.Direction.CW);

        Region region=new Region(-getWidth(),-getHeight(),getWidth(),getHeight());

        top_region.setPath(top_path,region);
        inner_region.setPath(inner_path,region);
        max_region.setPath(max_path,region);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path2=max_path;
        canvas.drawPath(path2,max_paint);
        Path path=top_path;
        canvas.drawPath(path,top_paint);


        Path path1=inner_path;
        canvas.drawPath(path1,inner_paint);




        //涉足设置随机数
        int a=0 ;
        for(int i=0;i<20;i++){
            a = (int) (Math.random()*9000+1000);
        }

        //设置字体在小圆的中心
        float v = textPaint.measureText(a + "");
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float ceil = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);

        Log.d(TAG, "onDraw: "+v);

        canvas.drawText(String.valueOf(a),getWidth()/2-v/2,getHeight()/2+ceil/4,textPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (max_region.contains(x,y)){
                    invalidate();
                }
        }
        return true;
    }
}
