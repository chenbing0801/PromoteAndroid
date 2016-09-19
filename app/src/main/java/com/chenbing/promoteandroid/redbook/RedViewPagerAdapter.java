package com.chenbing.promoteandroid.redbook;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/9/19.
 */
public class RedViewPagerAdapter extends PagerAdapter {

    private Context context;
    private int count = 0;
    private LinkedList<View> recycleView = new LinkedList<>();

    public RedViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if(!recycleView.isEmpty()){
            view = recycleView.pop();
        }
        else{
            view = new View(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        recycleView.push((View) object);
    }
}
