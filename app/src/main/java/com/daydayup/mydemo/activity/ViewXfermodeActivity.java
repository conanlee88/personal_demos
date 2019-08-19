package com.daydayup.mydemo.activity;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.activity.view.xfermode.XfermodeStr;
import com.daydayup.mydemo.view.XfermodeTestView;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by conan on 19-8-15
 * Describe:
 */
public class ViewXfermodeActivity extends AppCompatActivity {

    private XfermodeTestView mXfermodeTestView;
    private RecyclerView mRecyclerView;

    public static void start(Context context) {
        Intent starter = new Intent(context, ViewXfermodeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_xfermode);
        mXfermodeTestView = findViewById(R.id.view_xfermode_test);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(new XfermodeItemAdapter());
    }

    private class XfermodeItemAdapter extends RecyclerView.Adapter<XfermodeItemViewHolder> {

        private List<String> mModes;

        XfermodeItemAdapter() {
            createData();
        }

        private void createData() {
            mModes = new ArrayList<>();
            mModes.add(XfermodeStr.CLEAR.getName());
            mModes.add(XfermodeStr.SRC.getName());
            mModes.add(XfermodeStr.DST.getName());
            mModes.add(XfermodeStr.SRC_OVER.getName());
            mModes.add(XfermodeStr.DST_OVER.getName());
            mModes.add(XfermodeStr.SRC_IN.getName());
            mModes.add(XfermodeStr.DST_IN.getName());
            mModes.add(XfermodeStr.SRC_OUT.getName());
            mModes.add(XfermodeStr.DST_OUT.getName());
            mModes.add(XfermodeStr.SRC_ATOP.getName());
            mModes.add(XfermodeStr.DST_ATOP.getName());
            mModes.add(XfermodeStr.XOR.getName());
            mModes.add(XfermodeStr.DARKEN.getName());
            mModes.add(XfermodeStr.LIGHTEN.getName());
            mModes.add(XfermodeStr.MULTIPLY.getName());
            mModes.add(XfermodeStr.SCREEN.getName());
            mModes.add(XfermodeStr.ADD.getName());
            mModes.add(XfermodeStr.OVERLAY.getName());
        }

        private int margin = 18;

        @Override
        public XfermodeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(ViewXfermodeActivity.this);
            textView.setTextSize(18);
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(margin, margin, margin, margin);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(getResources().getColor(R.color.colorWhite));
            textView.setGravity(Gravity.CENTER);
            textView.setBackground(getDrawable(R.drawable.stroke_btn_bg));
            return new XfermodeItemViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(XfermodeItemViewHolder holder, int position) {
            final String modeName = mModes.get(position);
            holder.mTextView.setText(modeName);
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mXfermodeTestView.setXfermode(modeName);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mModes.size();
        }
    }

    private class XfermodeItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public XfermodeItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
