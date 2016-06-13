package com.cn.nj.vhtable;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

/**
 * Created by zlt on 2016/6/13.
 */
public class ScrollAdapter extends SimpleAdapter {
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    private List<? extends Map<String, ?>> datas;
    private int res;
    private String[] from;
    private int[] to;
    private Context context;
    private List<CHScrollView2> mHScrollViews;
    private ListView mListView;
    public ScrollAdapter(Context context, List<? extends Map<String, ?>> data,
                         int resource, String[] from, int[] to, List<CHScrollView2> mHScrollViews
                           , ListView mListview) {
        super(context, data, resource, from, to);
        this.context = context;
        this.datas = data;
        this.res = resource;
        this.from = from;
        this.to = to;
        this.mHScrollViews=mHScrollViews;
        this.mListView=mListview;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
           //convertView = LayoutInflater.from(context).inflate(res, null);
           View v = LayoutInflater.from(context).inflate(res, null);
                //第一次初始化的时候装进来
            addHViews((CHScrollView2) v.findViewById(R.id.item_chscroll_scroll));
            View[] views = new View[to.length];
            //单元格点击事件
            for(int i = 0; i < to.length; i++) {
                View tv = v.findViewById(to[i]);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("ii","点击了11111111:"+datas.get(position).get("title"));
                        Toast.makeText(context, "点击了11111111:"+datas.get(position), Toast.LENGTH_SHORT).show();
                    }
                });
                views[i] = tv;
            }
            v.setTag(views);

        View[] holders = (View[]) v.getTag();
        int len = holders.length;
        for(int i = 0 ; i < len; i++) {
            if (position%2==0){
                ((TextView)holders[i]).setText(this.datas.get(position).get(from[i]).toString());
                //(holders[i]).setBackgroundColor(Color.rgb(219, 238, 244) );
            }else {
                ((TextView)holders[i]).setText(this.datas.get(position).get(from[i]).toString());
                (holders[i]).setBackgroundColor(Color.WHITE);
            }
        }
        return v;
    }
    public void addHViews(final CHScrollView2 hScrollView) {
        if(!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CHScrollView2 scrollView = mHScrollViews.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if(scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
    }
}
