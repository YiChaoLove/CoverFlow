package cn.wxhyi.coverflow;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by yichao on 16/2/25.
 */
public class MyAdatper extends RecyclerView.Adapter<MyAdatper.ViewHolder> {

    public int border_position = 0;

    @Override
    public MyAdatper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.card_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.card_layout.setVisibility(View.VISIBLE);
        if (position < border_position || position > getItemCount() - border_position - 1){
            holder.card_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout card_layout;

        public ViewHolder(View v) {
            super(v);
            card_layout = (LinearLayout) v.findViewById(R.id.card_layout);
        }
    }
}
