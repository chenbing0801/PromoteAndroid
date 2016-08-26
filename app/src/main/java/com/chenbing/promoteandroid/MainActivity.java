package com.chenbing.promoteandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> mDatas=new ArrayList<>();
    private Button btnAdd,btnSub;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd= (Button) findViewById(R.id.btn_add);
        btnSub= (Button) findViewById(R.id.btn_subtract);

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler);

//        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);

//        GridLayoutManager mLayoutManager=new GridLayoutManager(this,3);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));

        initData();

        adapter = new MyAdapter();

        recyclerView.setAdapter(adapter);

        initListener();
    }

    private void initListener() {
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }

    private void initData() {
        for (int i=0;i<100;i++){
            mDatas.add(i,""+i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                mDatas.add(2,"x");
                adapter.notifyItemInserted(2);
                break;
            case R.id.btn_subtract:
                mDatas.remove(2);
                adapter.notifyItemRemoved(2);
                break;
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_listview_item,parent,false);
            ViewHolder vh=new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView tv;

            public ViewHolder(View itemView) {
                super(itemView);
                tv= (TextView) itemView.findViewById(R.id.textview);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("onClick",""+getPosition());
                    }
                });
            }
        }
    }
}