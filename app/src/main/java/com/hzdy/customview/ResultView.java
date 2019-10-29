package com.hzdy.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Talon on 2019-10-26.
 * Desc: 检测结果试纸View
 */
public class ResultView extends View {
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mCirclePaint;
    private Paint mCircleWhitePaint;
    private static final int PADDINGLEFT = 30; //留给左边画文字
    private static final int PADDINGLRIGHT = 30; //留给右边画文字
    private int PADDINGTOP = 50; //建议布局文件高度 120dp
    private int[] lineColor = new int[]{0xFFAE6800, 0xFFA7B130, 0xFF13A55A, 0xFF0E80B9}; //渐变颜色
    private float[] positions = new float[]{0, 0.3F, 0.6F, 1.0F}; // 颜色位置
    private double[] lineText = new double[]{10, 20, 30, 50, 90, 100}; // 值
    private double data = 95.0f; // 结果
    private String dataRes = "偏高"; // 结果判断
    private String[] lineAdd = new String[]{"-", "+", "+", "±", "++", "+++"};
    private float radio = 0; // 获取圆圈颜色位置百分比 取值[0,1]  getRadio()

    public ResultView(Context context) {
        super(context);
        init();
    }

    public ResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(18);
        mTextPaint.setColor(0xff1a1a1a);
        mCirclePaint.setTextSize(24);
        mCirclePaint.setColor(Color.GREEN);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCircleWhitePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mCirclePaint.setStrokeWidth(3);
        mCirclePaint.setTextSize(22);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(14);
        mPaint.setStyle(Paint.Style.STROKE);


    }

    /**
     * 重写onMeasure方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 150);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 150);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PADDINGTOP = getHeight() / 4;
        drawLine(canvas);
        drawText(canvas);
        drawAdd(canvas);
        drawResLine(canvas);
        Log.d("Talon", "width:    " + getWidth());
        Log.d("Talon", "height:    " + getHeight());
//        canvas.drawLine(0, 0, getWidth(), getHeight(), mPaint);
    }

    private void drawText(Canvas canvas) {
        String text;
        for (int i = 0; i < lineText.length; i++) {
            text = String.valueOf(lineText[i]);
            if (i == 0) {
                canvas.drawText(text, PADDINGLEFT - mTextPaint.measureText(text) / 2, (getHeight() - PADDINGTOP) / 4 + PADDINGTOP, mTextPaint);
            } else if (i == lineText.length - 1) {
                canvas.drawText(text, getWidth() - PADDINGLRIGHT - mTextPaint.measureText(text) / 2, (getHeight() - PADDINGTOP) / 4 + PADDINGTOP, mTextPaint);
            } else {
                canvas.drawText(text, i * (getWidth() - (PADDINGLEFT + PADDINGLRIGHT)) / (lineText.length - 1) - mTextPaint.measureText(text) / 2 + PADDINGLEFT, (getHeight() - PADDINGTOP) / 4 + PADDINGTOP, mTextPaint);
            }
        }

    }

    private void drawLine(Canvas canvas) {
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        LinearGradient linearGradient = new LinearGradient(0, 0, getWidth(), 0, lineColor, positions, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawLine(PADDINGLEFT, (getHeight() - PADDINGTOP) / 2 + PADDINGTOP - 7, getWidth() - PADDINGLRIGHT, (getHeight() - PADDINGTOP) / 2 + PADDINGTOP - 7, mPaint);
    }

    private void drawAdd(Canvas canvas) {
        String text;
        for (int i = 0; i < lineAdd.length; i++) {
            text = lineAdd[i];
            if (i == 0) {
                canvas.drawText(text, PADDINGLEFT - mTextPaint.measureText(text) / 2, (getHeight() - PADDINGTOP) * 3 / 4 + PADDINGTOP, mTextPaint);
            } else if (i == lineAdd.length - 1) {
                canvas.drawText(text, getWidth() - PADDINGLRIGHT - mTextPaint.measureText(text) / 2, (getHeight() - PADDINGTOP) * 3 / 4 + PADDINGTOP, mTextPaint);
            } else {
                canvas.drawText(text, i * (getWidth() - (PADDINGLEFT + PADDINGLRIGHT)) / (lineAdd.length - 1) - mTextPaint.measureText(text) / 2 + PADDINGLEFT, (getHeight() - PADDINGTOP) * 3 / 4 + PADDINGTOP, mTextPaint);
            }
        }
    }

    private void drawResLine(Canvas canvas) {
        String text = data + " " + dataRes;
        mCirclePaint.setColor(getColor(getRadio()));
        canvas.drawText(text, getDataX() - mCirclePaint.measureText(text) / 2, PADDINGTOP, mCirclePaint);
        canvas.drawCircle(getDataX(), (getHeight() - PADDINGTOP) / 2 + PADDINGTOP - 7, 13, mCirclePaint);
        canvas.drawCircle(getDataX(), (getHeight() - PADDINGTOP) / 2 + PADDINGTOP - 7, 10, mCircleWhitePaint);
    }

    // 获取圆圈颜色位置百分比 取值[0,1]
    private float getRadio() {
        float res = 1;
        for (int i = 0; i < lineText.length; i++) {
            if (data < lineText[i]) {
                if (i == 0) {
                    return 0;
                } else {
                    double percent = (data - lineText[i - 1]) / (lineText[i] - lineText[i - 1]);
                    res = (float) ((i - 1 + percent) / (lineText.length - 1));
                    return res;
                }
            }
        }
        return res;
    }

    private float getDataX() {
        float x = getWidth() - PADDINGLRIGHT;
        for (int i = 0; i < lineText.length; i++) {
            if (data < lineText[i]) {
                if (i == 0) {
                    return PADDINGLEFT;
                } else {
                    double percent = (data - lineText[i - 1]) / (lineText[i] - lineText[i - 1]);
                    x = (float) ((getWidth() - PADDINGLEFT - PADDINGLRIGHT) * (i - 1 + percent) / (lineText.length - 1) + PADDINGLEFT);
                    return x;
                }
            }
        }
        return x;
    }

    /**
     * 获取某个百分比位置的颜色
     *
     * @param radio 取值[0,1]
     * @return
     */
    public int getColor(float radio) {
        int startColor;
        int endColor;
        if (radio >= 1) {
            return lineColor[lineColor.length - 1];
        }
        for (int i = 0; i < positions.length; i++) {
            if (radio <= positions[i]) {
                if (i == 0) {
                    return lineColor[0];
                }
                startColor = lineColor[i - 1];
                endColor = lineColor[i];
                float areaRadio = getAreaRadio(radio, positions[i - 1], positions[i]);
                return getColorFrom(startColor, endColor, areaRadio);
            }
        }
        return -1;
    }

    public float getAreaRadio(float radio, float startPosition, float endPosition) {
        return (radio - startPosition) / (endPosition - startPosition);
    }

    /**
     * 取两个颜色间的渐变区间 中的某一点的颜色
     *
     * @param startColor
     * @param endColor
     * @param radio
     * @return
     */
    public int getColorFrom(int startColor, int endColor, float radio) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
        return Color.argb(255, red, greed, blue);
    }


}
