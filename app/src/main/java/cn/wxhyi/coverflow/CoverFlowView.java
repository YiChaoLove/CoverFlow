package cn.wxhyi.coverflow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yichao on 16/2/25.
 */
public class CoverFlowView extends RecyclerView {

    private static final String TAG = "CoverFlowView";

    private int last_position = 0;
    private int current_position = 0;

    private boolean flag = false;

    private CoverFlowItemListener coverFlowListener;

    private final Camera mCamera = new Camera();
    private final Matrix mMatrix = new Matrix();
    /**
     * Paint object to draw with
     */
    private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);


    public CoverFlowView(Context context) {
        super(context);
        init();
    }

    public CoverFlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverFlowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);
        this.setChildrenDrawingOrderEnabled(true);
        this.addOnScrollListener(new CoverFlowScrollListener());
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

//      prepareMatrix(mMatrix, distanceY, r);
        prepareMatrix(mMatrix, distancX, getWidth() / 2);

        mMatrix.preTranslate(-childCenterX, -childCenterY);
        mMatrix.postTranslate(childCenterX, childCenterY);
        mMatrix.postTranslate(left, top);

        canvas.drawBitmap(bitmap, mMatrix, mPaint);
        return false;
    }

    private void prepareMatrix(final Matrix outMatrix, int distanceY, int r) {
        //clip the distance
        final int d = Math.min(r, Math.abs(distanceY));
        //use circle formula
        final float translateZ = (float) Math.sqrt((r * r) - (d * d));
        mCamera.save();
        mCamera.translate(0, 0, r - translateZ);
        mCamera.getMatrix(outMatrix);
        mCamera.restore();
    }

    private Bitmap getChildDrawingCache(final View child) {
        Bitmap bitmap = child.getDrawingCache();
        if (bitmap == null) {
            child.setDrawingCacheEnabled(true);
            child.buildDrawingCache();
            bitmap = child.getDrawingCache();
        }
        return bitmap;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int centerChild = childCount / 2;
        if (!flag) {
            Log.i(TAG, "center_positon: " + centerChild);
            ((MyAdatper) getAdapter()).border_position = centerChild;
            flag = true;
        }
        current_position = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition() + centerChild;
        if (last_position != current_position) {
            last_position = current_position;
            coverFlowListener.onItemChanged(current_position);
        }

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
        return rez;

    }

    interface CoverFlowItemListener {
        void onItemChanged(int position);
        void onItemSelected(int position);
    }

    public void setCoverFlowListener(CoverFlowItemListener coverFlowListener) {
        this.coverFlowListener = coverFlowListener;
    }

    public class CoverFlowScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                coverFlowListener.onItemSelected(current_position);
            }
        }
    }

}
