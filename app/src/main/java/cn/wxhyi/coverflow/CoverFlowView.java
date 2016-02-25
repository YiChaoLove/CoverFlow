package cn.wxhyi.coverflow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yichao on 16/2/25.
 */
public class CoverFlowView extends RecyclerView {

    private static final String TAG = "CoverFlowView";
    private int centerChild = 0;

    /** Ambient light intensity */
    private static final int AMBIENT_LIGHT = 55;
    /** Diffuse light intensity */
    private static final int DIFFUSE_LIGHT = 200;
    /** Specular light intensity */
    private static final float SPECULAR_LIGHT = 70;
    /** Shininess constant */
    private static final float SHININESS = 200;
    /** The max intensity of the light */
    private static final int MAX_INTENSITY = 0xFF;

    private final Camera mCamera = new Camera();
    private final Matrix mMatrix = new Matrix();
    /** Paint object to draw with */
    private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);


    public CoverFlowView(Context context) {
        super(context);
        mPaint.setAntiAlias(true);
        this.setChildrenDrawingOrderEnabled(true);
    }

    public CoverFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);
        this.setChildrenDrawingOrderEnabled(true);
    }

    public CoverFlowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint.setAntiAlias(true);
        this.setChildrenDrawingOrderEnabled(true);
    }

    @Override
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Bitmap bitmap = getChildDrawingCache(child);
        // (top,left) is the pixel position of the child inside the list
        final int top = child.getTop();
        final int left = child.getLeft();
        // center point of child
        final int childCenterY = child.getHeight() / 2;
        final int childCenterX = child.getWidth() / 2;
        //center of list
        final int parentCenterY = getHeight() / 2;
        final int parentCenterX = getWidth() / 2;
        //center point of child relative to list
        final int absChildCenterY = child.getTop() + childCenterY;
        final int absChildCenterX = child.getLeft() + childCenterX;
        //distance of child center to the list center
        final int distanceY = parentCenterY - absChildCenterY;
        //radius of imaginary cirlce
        final int r = getHeight() / 2;

        final int distancX = parentCenterX - absChildCenterX;

//        prepareMatrix(mMatrix, distanceY, r, child.getId());
        prepareMatrix(mMatrix, distancX, getWidth() / 2, child.getId());

        mMatrix.preTranslate(-childCenterX, -childCenterY);
        mMatrix.postTranslate(childCenterX, childCenterY);
        mMatrix.postTranslate(left, top);

        canvas.drawBitmap(bitmap, mMatrix, mPaint);
        return false;
    }

    private void prepareMatrix(final Matrix outMatrix, int distanceY, int r, int current_position){
        //clip the distance
        final int d = Math.min(r, Math.abs(distanceY));
        //use circle formula
        final float translateZ = (float) Math.sqrt((r * r) - (d * d));

        //solve for t: d = r*cos(t)
        double radians = Math.acos((float) d / r);
        double degree = 90 - (180 / Math.PI) * radians;

        mCamera.save();

//        if (current_position == getFirstVisiblePosition()){
//            mCamera.translate(0, 0, r - translateZ);
//        }

        mCamera.translate(0, 0, r-translateZ);
//		mCamera.rotateX((float) degree);
        if (distanceY < 0) {
            degree = 360 - degree;
        }
//		mCamera.rotateY((float) degree);
        mCamera.getMatrix(outMatrix);
        mCamera.restore();

        // highlight elements in the middle
//		mPaint.setColorFilter(calculateLight((float) degree));
    }

    private Bitmap getChildDrawingCache(final View child){
        Bitmap bitmap = child.getDrawingCache();
        if (bitmap == null) {
            child.setDrawingCacheEnabled(true);
            child.buildDrawingCache();
            bitmap = child.getDrawingCache();
        }
        return bitmap;
    }

    private LightingColorFilter calculateLight(final float rotation) {
        final double cosRotation = Math.cos(Math.PI * rotation / 180);
        int intensity = AMBIENT_LIGHT + (int) (DIFFUSE_LIGHT * cosRotation);
        int highlightIntensity = (int) (SPECULAR_LIGHT * Math.pow(cosRotation, SHININESS));
        if (intensity > MAX_INTENSITY) {
            intensity = MAX_INTENSITY;
        }
        if (highlightIntensity > MAX_INTENSITY) {
            highlightIntensity = MAX_INTENSITY;
        }
        final int light = Color.rgb(intensity, intensity, intensity);
        final int highlight = Color.rgb(highlightIntensity, highlightIntensity, highlightIntensity);
        return new LightingColorFilter(light, highlight);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
//        Log.i("childCount", childCount + "");
        //find center row
        if ((childCount % 2) == 0) { //even childCount number
            centerChild = childCount / 2; // if childCount 8 (actualy 0 - 7), then 4 and 4-1 = 3 is in centre.
//            int otherCenterChild = centerChild - 1;
//            //Which more in center?
//            View child = this.getChildAt(centerChild);
//            final int top = child.getTop();
//            final int bottom = child.getBottom();
//            //if this row goes through center then this
//            final int absParentCenterY = getTop() + getHeight() / 2;
//            //Log.i("even", i + " from " + (childCount - 1) + ", while centerChild = " + centerChild);
//            if ((top < absParentCenterY) && (bottom > absParentCenterY)) {
//                //this child is in center line, so it is last
//                //centerChild is in center, no need to change
//            } else {
//                centerChild = otherCenterChild;
//            }
        }
        else {//not even - done
            centerChild = childCount / 2;
            //Log.i("not even", i + " from " + (childCount - 1) + ", while centerChild = " + centerChild);
        }

//        Log.i("centerchild", centerChild + "");

//        centerChild++ ;
        int rez = i;
        //find drawIndex by centerChild
        if (i > centerChild) {
            //below center
            rez = (childCount - 1) - i + centerChild;
        } else if (i == centerChild) {
            //center row
            //draw it last
            rez = childCount - 1;
        } else {
            //above center - draw as always
            // i < centerChild
            rez = i;
        }
        //Log.i("return", "" + rez);
        return rez;

    }

    public int getCenterChild() {
        return centerChild;
    }
}
