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
public class MainActivity extends Activity implements CoverFlowView.CoverFlowItemListener{

    private CoverFlowView coverFlowView;
    private LinearLayoutManager layoutManager;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyAdatper adatper = new MyAdatper();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        coverFlowView = (CoverFlowView) this.findViewById(R.id.listView);
        coverFlowView.addItemDecoration(new DividerItemDecoration(-50));
        coverFlowView.setLayoutManager(layoutManager);
        coverFlowView.setAdapter(adatper);
        coverFlowView.setCoverFlowListener(this);
        //You have to call scrollToPosition method at last.
        layoutManager.scrollToPosition(adatper.getItemCount() / 2);
    }

    @Override
    public void onItemChanged(int position) {
        //do something you want
//        Log.i(TAG, "onItemChanged" + position);
    }

    @Override
    public void onItemSelected(int position) {
        //do something you want
        Log.i(TAG, "onItemSelected" + position);
    }
}
