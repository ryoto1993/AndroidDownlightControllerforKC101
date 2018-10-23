package jp.ac.doshisha.mikilab.androiddownlightcontrollerforkc101;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class LightView extends View {

    private float mStartAngle;
    private float mDrawAngle;
    private final Paint mFirstPaint = new Paint();
    private final Paint mSecondPaint = new Paint();
    private final RectF mOval = new RectF();

    public LightView(Context context) {
        super(context);
        initView(context);
    }

    public LightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mOval.set(getStrokeWidth(),
                getStrokeWidth(),
                canvas.getWidth() - getStrokeWidth(),
                canvas.getHeight() - getStrokeWidth());
        canvas.drawArc(mOval, mStartAngle, 360, false, mSecondPaint);
        canvas.drawArc(mOval, mStartAngle, mDrawAngle, false, mFirstPaint);
    }

    private void initView(Context context) {
        mFirstPaint.setAntiAlias(true);
        mFirstPaint.setStyle(Paint.Style.STROKE);

        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStyle(Paint.Style.STROKE);

        setStartAngle(-90.0f);
        setDrawAngle(270.0f);
        setPrimaryLineColor(Color.BLUE);
        setSecondaryLineColor(Color.LTGRAY);
        setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()));

        setSize();
    }

    private void initView(Context context, AttributeSet attrs) {
        mFirstPaint.setAntiAlias(true);
        mFirstPaint.setStyle(Paint.Style.STROKE);

        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setStyle(Paint.Style.STROKE);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);
        setStartAngle(typedArray.getFloat(R.styleable.CircleView_start_angle, -90.0f));
        setDrawAngle(typedArray.getFloat(R.styleable.CircleView_draw_angle, 270.0f));
        setPrimaryLineColor(typedArray.getColor(R.styleable.CircleView_primary_line_color, Color.BLACK));
        setSecondaryLineColor(typedArray.getColor(R.styleable.CircleView_secondary_line_color, Color.LTGRAY));
        setStrokeWidth(typedArray.getDimension(R.styleable.CircleView_stroke_width,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0f, getResources().getDisplayMetrics())));
        typedArray.recycle();

        setSize();
    }

    public void setSize() {
        final GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec =  GridLayout.spec(GridLayout.UNDEFINED, 1,1f);
        params.columnSpec =  GridLayout.spec(GridLayout.UNDEFINED, 1,1f);
        params.height = 0;
        params.width = 0;
        setLayoutParams(params);
    }

    public float getStartAngle() { return mStartAngle; }

    public void setStartAngle(float startAngle) { mStartAngle = startAngle; }

    public float getDrawAngle() { return mDrawAngle; }

    public void setDrawAngle(float drawAngle) { mDrawAngle = drawAngle; }

    public int getPrimaryLineColor() { return mFirstPaint.getColor(); }

    public void setPrimaryLineColor(int color) { mFirstPaint.setColor(color); }

    public int getSecondaryLineColor() { return mSecondPaint.getColor(); }

    public void setSecondaryLineColor(int color) { mSecondPaint.setColor(color); }

    public float getStrokeWidth() { return mFirstPaint.getStrokeWidth(); }

    public void setStrokeWidth(float width) { mFirstPaint.setStrokeWidth(width); mSecondPaint.setStrokeWidth(width); }

    /**
     * This section is for screen rotation.
     **/
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parent = super.onSaveInstanceState();
        SavedState saved = new SavedState(parent);
        saved.startAngle = getStartAngle();
        saved.drawAngle = getDrawAngle();
        saved.primaryLineColor = getPrimaryLineColor();
        saved.secondaryLineColor = getSecondaryLineColor();
        saved.strokeWidth = getStrokeWidth();
        return saved;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState saved = (SavedState) state;
        super.onRestoreInstanceState(saved.getSuperState());
        setStartAngle(saved.startAngle);
        setDrawAngle(saved.drawAngle);
        setPrimaryLineColor(saved.primaryLineColor);
        setSecondaryLineColor(saved.secondaryLineColor);
        setStrokeWidth(saved.strokeWidth);
    }

    private static class SavedState extends BaseSavedState {
        public float startAngle;
        public float drawAngle;
        public int primaryLineColor;
        public int secondaryLineColor;
        public float strokeWidth;

        public SavedState(Parcel in) {
            super(in);
            startAngle = in.readFloat();
            drawAngle = in.readFloat();
            primaryLineColor = in.readInt();
            secondaryLineColor = in.readInt();
            strokeWidth = in.readFloat();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(final Parcel out, final int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(startAngle);
            out.writeFloat(drawAngle);
            out.writeInt(primaryLineColor);
            out.writeInt(secondaryLineColor);
            out.writeFloat(strokeWidth);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }
}
