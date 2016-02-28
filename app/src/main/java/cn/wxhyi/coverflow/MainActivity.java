package cn.wxhyi.coverflow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by yichao on 16/2/20.
 */
public class MainActivity extends Activity implements CoverFlowView.CoverFlowItemListener{

    private static final String TAG = "MainActivity";

    private CoverFlowView coverFlowView;
    private CoverFlowAdapter coverFlowAdapter;
    private TextView text;
    private LinkedList<CardModel> cardModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //vertical layout
//        setContentView(R.layout.main_vertical);
        setContentView(R.layout.main);


        coverFlowView = (CoverFlowView) this.findViewById(R.id.cover_flow);
        text = (TextView) this.findViewById(R.id.text);
        cardModels = new LinkedList<>();
        cardModels.add(new CardModel("1.Alligator", "" + R.drawable.alligator));
        cardModels.add(new CardModel("2.Beaver", "" + R.drawable.beaver));
        cardModels.add(new CardModel("3.Frog", "" + R.drawable.frog));
        cardModels.add(new CardModel("4.Kangaroo", "" + R.drawable.kangaroo));
        cardModels.add(new CardModel("5.Leopard", "" + R.drawable.leopard));
        cardModels.add(new CardModel("6.Snail", "" + R.drawable.snail));
        cardModels.add(new CardModel("7.Wolf", "" + R.drawable.wolf));
        cardModels.add(new CardModel("8.Monkey", "" + R.drawable.monkey));
        cardModels.add(new CardModel("9.Tiger", "" + R.drawable.tiger));

        //vertical overlap list iew
//        coverFlowView.setOrientation(CoverFlowView.VERTICAL);
        coverFlowView.setOrientation(CoverFlowView.HORIZONTAL);

        coverFlowAdapter = new CoverFlowAdapter(cardModels, this);
        coverFlowView.setAdapter(coverFlowAdapter);
        coverFlowView.setCoverFlowListener(this);
        coverFlowView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG, "onItemClick:position:" + position);
                coverFlowView.scrollToCenter(position);
            }
        }));

        coverFlowView.getLayoutManager().scrollToPosition(coverFlowAdapter.getItemCount() / 2);
        onItemSelected(coverFlowAdapter.getItemCount() / 2);
        coverFlowAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemChanged(int position) {
        //do something you want
//        Log.i(TAG, "onItemChanged" + position);
    }

    @Override
    public void onItemSelected(int position) {
        //do something you want
//        Log.i(TAG, "onItemSelected" + position);
        text.setText(cardModels.get(position).getTitle());
    }
}
