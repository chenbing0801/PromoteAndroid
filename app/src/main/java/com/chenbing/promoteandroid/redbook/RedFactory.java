package com.chenbing.promoteandroid.redbook;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.chenbing.promoteandroid.R;

/**
 * Created by Administrator on 2016/9/19.
 */
public class RedFactory implements LayoutInflater.Factory {

    private RedLayoutInflater inflater;
    private LayoutInflater.Factory factory;

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.view."
    };

    public RedFactory(RedLayoutInflater inflater, LayoutInflater.Factory factory) {
        this.inflater = inflater;
        this.factory = factory;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if(context instanceof LayoutInflater.Factory){
            view = ((LayoutInflater.Factory) context).onCreateView(name,context,attrs);
        }

        if(factory != null && view == null){
            view = factory.onCreateView(name, context, attrs);
        }

        if(view == null){
            view = createViewOrFailQuietly(name, context, attrs);
        }

        if(view != null){
            onViewCreated(view, context, attrs);
        }
        return view;
    }

    protected void onViewCreated(View view, Context context, AttributeSet attrs) {
        int[] attrIds = {R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out};

        TypedArray array = context.obtainStyledAttributes(attrs, attrIds);

        if(array != null){
            if(array.length() > 0){
                RedViewTag viewTag = new RedViewTag();
                viewTag.alphaIn = array.getFloat(0, 0f);
                viewTag.alphaOut = array.getFloat(1, 0f);
                viewTag.xIn = array.getFloat(2, 0f);
                viewTag.xOut = array.getFloat(3, 0f);
                viewTag.yIn = array.getFloat(4, 0f);
                viewTag.yOut = array.getFloat(5, 0f);
                view.setTag(R.id.parallax_view_tag, viewTag);
            }
            array.recycle();
        }
    }

    protected View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
        if (name.contains(".")) {
            return createViewOrFailQuietly(name, null, context, attrs);
        }

//        for (final String prefix : sClassPrefixList) {
//            final View view = createViewOrFailQuietly(name, prefix, context, attrs);
//
//            if (view != null) {
//                return view;
//            }
//        }

        return null;
    }

    protected View createViewOrFailQuietly(String name, String prefix, Context context,
                                           AttributeSet attrs) {
        try {
            return inflater.createView(name, prefix, attrs);
        } catch (Exception ignore) {
            return null;
        }
    }
}
