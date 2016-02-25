package cn.wxhyi.coverflow;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by yichao on 16/2/20.
 */
public class MainActivity extends Activity {

    private CoverFlowView listView;
    private LinearLayoutManager layoutManager;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyAdatper adatper = new MyAdatper();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public void smoothScrollToPosition(final RecyclerView recyclerView, RecyclerView.State state, final int position) {
                if (position >= getItemCount()) {
                    Log.e(TAG, "Cannot scroll to " + position + ", item count is " + getItemCount());
                    return;
                }

                /*
                 * LinearSmoothScroller's default behavior is to scroll the contents until
                 * the child is fully visible. It will snap to the top-left or bottom-right
                 * of the parent depending on whether the direction of travel was positive
                 * or negative.
                 */
                LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext()) {
                    /*
                     * LinearSmoothScroller, at a minimum, just need to know the vector
                     * (x/y distance) to travel in order to get from the current positioning
                     * to the target.
                     */
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        Log.i(TAG, "listview.getW:" + listView.getWidth());
                        return new PointF(listView.getWidth() / 2, 0);
                    }
                };
                scroller.setTargetPosition(position);
                startSmoothScroll(scroller);
            }
        };


        listView = (CoverFlowView) this.findViewById(R.id.listView);
        listView.addItemDecoration(new DividerItemDecoration(-50));
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adatper);
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i(TAG, "SCROLL_STATE_IDLE");
                    int scrollPosition = listView.getCenterChild();
//                    listView.smoothScrollToPosition(18);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
