package com.chenbing.promoteandroid.redbook;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chenbing.promoteandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class RedContentFrameLayout extends FrameLayout {

    private RedViewPagerAdapter adapter;
    private Context context;
    private List<View> redViews = new ArrayList<>();
    private ViewPager viewPager;
    private int containerWidth;
    private boolean isLooping = false;
    private int pageCount = 0;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private ImageView imageView;

    public RedContentFrameLayout(Context context) {
        this(context, null);
    }

    public RedContentFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedContentFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        adapter = new RedViewPagerAdapter(context);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        containerWidth = getMeasuredWidth();
        if (viewPager != null) {
            pageChangeListener.onPageScrolled(viewPager.getCurrentItem(), 0, 0);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }


    public void setUpChildren(LayoutInflater inflater, int... childIds){
        RedLayoutInflater layoutInflater = new RedLayoutInflater(inflater, context);

        for(int childId : childIds){
            View view = layoutInflater.inflate(childId, this);
            redViews.add(view);
        }

        pageCount = getChildCount();
        for (int i = 0; i < pageCount; i++) {
            View view = getChildAt(i);
            addRedView(view, i);
        }

        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewPager.setId(R.id.parallax_pager);

        pageChangeListener = new ViewPager.OnPageChangeListener() {
            boolean isleft = false;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 在这个里面控制view显示或不显示
                if (positionOffsetPixels < 10) {
                    isleft = false;
                }

                if (position == 3) {
                    if (isleft) {

                    } else {
                        imageView.setX(imageView.getLeft() - positionOffsetPixels);
                    }
                }

                RedViewTag tag;
                for (View view : redViews) {
                    tag = (RedViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }

                    if ((position == tag.index - 1 || (isLooping && (position == tag.index
                            - 1 + pageCount)))
                            && containerWidth != 0) {

                        // make visible
                        view.setVisibility(VISIBLE);

                        // slide in from right
                        view.setTranslationX((containerWidth - positionOffsetPixels) * tag.xIn);

                        // slide in from top
                        view.setTranslationY(0 - (containerWidth - positionOffsetPixels) * tag.yIn);

                        // fade in
                        view.setAlpha(1.0f - (containerWidth - positionOffsetPixels) * tag.alphaIn / containerWidth);

                    } else if (position == tag.index) {

                        // make visible
                        view.setVisibility(VISIBLE);

                        // slide out to left
                        view.setTranslationX(0 - positionOffsetPixels * tag.xOut);

                        // slide out to top
                        view.setTranslationY(0 - positionOffsetPixels * tag.yOut);

                        // fade out
                        view.setAlpha(1.0f - positionOffsetPixels * tag.alphaOut / containerWidth);

                    } else {
                        view.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(pageChangeListener);
        addView(viewPager, 0);
    }

    private void addRedView(View view,int pageIndex){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            for(int i = 0,childCount = viewGroup.getChildCount(); i < childCount; i++){
                addRedView(viewGroup.getChildAt(i), pageIndex);
            }
        }

        RedViewTag viewTag = (RedViewTag) view.getTag(R.id.parallax_view_tag);
        if(viewTag != null){
            viewTag.index = pageIndex;
            redViews.add(view);
        }
    }

}
