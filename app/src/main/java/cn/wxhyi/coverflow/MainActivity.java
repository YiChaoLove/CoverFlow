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
        setContentView(R.layout.main);//or vertical layout R.layout.main_vertical
        initView();
        initData();
        coverFlowAdapter = new CoverFlowAdapter(cardModels, this);
        coverFlowView.setTilted(true);// set whether tilted item
        coverFlowView.setAdapter(coverFlowAdapter);
        coverFlowView.setCoverFlowListener(this);
        coverFlowView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG, "onItemClick:position:" + position);
                coverFlowView.scrollToCenter(position);
            }
        }));
        //scroll to center item
        coverFlowView.getLayoutManager().scrollToPosition(coverFlowAdapter.getItemCount() / 2);
        //scroll to item's center
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                coverFlowView.scrollToCenter(coverFlowAdapter.getItemCount() / 2);
            }
        }, 200);
    }

    private void initView() {
        coverFlowView = (CoverFlowView) this.findViewById(R.id.cover_flow);
        text = (TextView) this.findViewById(R.id.text);
    }

    private void initData() {
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
    }

    @Override
    public void onItemChanged(int position) {
        //do something you want
    }

    @Override
    public void onItemSelected(int position) {
        //do something you want
        text.setText(cardModels.get(position).getTitle());
    }
}
