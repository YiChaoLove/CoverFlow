package cn.wxhyi.coverflow;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yichao on 16/2/25.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int padding;

    public DividerItemDecoration(int padding){
        this.padding = padding;
    }

    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        Log.i("view.getId", view.getId() + "");
//        Log.i("parent:getChildItemId", parent.getChildItemId(view) + "");

        if (view.getId() == 0){
            return;
        }
        outRect.left = padding;
//        outRect.right += padding;
    }
}
