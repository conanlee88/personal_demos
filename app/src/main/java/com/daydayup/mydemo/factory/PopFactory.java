package com.daydayup.mydemo.factory;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.daydayup.mydemo.MyDemoApplication;
import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.PopListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc$
 * Created by conan on 2018/8/29.
 */

public class PopFactory {

    /**
     * 生产简单选项的pop
     * @param title pop的标题
     * @param options pop的选项（string的列表类型）
     * @return pop
     */
    public static List<Object> createPop(Context context, int width, int height, String title, @NonNull List<String> options){
        if (context == null){
            context = MyDemoApplication.getMyDemoApplication();
        }
        if (width == 0) width = LinearLayout.LayoutParams.MATCH_PARENT;
        if (height == 0) height = LinearLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(width,height);
        PopListBinding popListBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.pop_list,null,false);
        if (!TextUtils.isEmpty(title)){
            popListBinding.popTitle.setText(title);
        }
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        optionsAdapter.addAll(options);
        popListBinding.popList.setAdapter(optionsAdapter);

        popupWindow.setContentView(popListBinding.getRoot());

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setTouchable(true);

        List<Object> objects = new ArrayList<>();
        objects.add(popupWindow);
        objects.add(popListBinding.popList);
        return objects;
    }
}
