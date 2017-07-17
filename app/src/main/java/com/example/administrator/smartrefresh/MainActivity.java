package com.example.administrator.smartrefresh;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView rv;
    List<String> mdatas;
    RecyclerView.Adapter<MyAdapter.VH> adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.immersive(this);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl);
        rv= (RecyclerView) findViewById(R.id.rv);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mdatas.add(0," this is the new data.");
                        adapter.notifyDataSetChanged();
                        refreshlayout.finishRefresh();
                    }
                }, 1500);
            }
        });
        initRecyclerView();
    }

    private void initDatas(){
        mdatas=new ArrayList<>();
        for (int i=0;i<20;i++){
            mdatas.add("this is the "+ i+" item");
        }
    }


    private void initRecyclerView(){
        rv.setLayoutManager(new LinearLayoutManager(this));
        initDatas();
        adapter=new MyAdapter(mdatas,this);
        rv.setAdapter(adapter);
    }




}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    List<String> mdatas;
    Context context;
    public MyAdapter(List<String> mdatas,Context context){
        this.mdatas=mdatas;
        this.context=context;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH vh=new VH(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.settext(mdatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    static class VH extends RecyclerView.ViewHolder{
        View view;
        public VH(View itemView) {
            super(itemView);
            this.view=itemView;
        }
        public void settext(String s){
            ((TextView)view.findViewById(R.id.tv)).setText(s);
        }
    }
}