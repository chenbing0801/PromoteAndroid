package com.chenbing.promoteandroid.redbook;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.chenbing.promoteandroid.R;

/**
 * Created by Administrator on 2016/9/19.
 */
public class RedBookMainActivity extends Activity {

    private RedContentFrameLayout contentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_red_main);

        contentFrameLayout = (RedContentFrameLayout) findViewById(R.id.container);
        contentFrameLayout.setImageView((ImageView) findViewById(R.id.iv_person));

        contentFrameLayout.setUpChildren(getLayoutInflater(), R.layout.view_intro_1,
                R.layout.view_intro_2, R.layout.view_intro_3, R.layout.view_intro_4,
                R.layout.view_intro_5);
    }
}
