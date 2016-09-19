package com.chenbing.promoteandroid.redbook;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by Administrator on 2016/9/19.
 */
public class RedLayoutInflater extends LayoutInflater {

    public RedLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        if(!(getFactory() instanceof RedFactory)){
            setFactory(new RedFactory(this, getFactory()));
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new RedLayoutInflater(this, newContext);
    }
}
