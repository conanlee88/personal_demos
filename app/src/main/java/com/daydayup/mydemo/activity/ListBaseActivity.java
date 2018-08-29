package com.daydayup.mydemo.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityListBinding;
import com.daydayup.mydemo.util.ToastUtils;

import java.util.List;
import java.util.Map;

/**
 * $desc$
 * Created by conan on 2018/8/29.
 */

public abstract class ListBaseActivity extends AppCompatActivity{

    protected ActivityListBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        initView();
        initEvent();
    }

    protected void initView(){
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        stringArrayAdapter.addAll(getListData());
        mBinding.list.setAdapter(stringArrayAdapter);
    }

    protected abstract List<String> getListData();

    private void initEvent() {
        mBinding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class wantedStartActivity = getWantedStartActivity(position);
                if (wantedStartActivity == null){
                    ToastUtils.makeToast("要开启的activity不能为null");
                    return;
                }
                Intent intent = new Intent(ListBaseActivity.this,wantedStartActivity);
                Map<String, String> startParams = getStartParams(position);
                if (startParams != null && startParams.size() > 0){
                    for (Map.Entry<String,String> param : startParams.entrySet()){
                        intent.putExtra(param.getKey(),param.getValue());
                    }
                }
                startActivity(intent);
            }
        });
    }

    protected abstract Map<String,String> getStartParams(int position);

    protected abstract Class getWantedStartActivity(int position);

}
