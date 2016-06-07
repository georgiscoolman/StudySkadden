package com.example.isoft.studyskadden.views;

import android.widget.TextView;

import com.example.isoft.studyskadden.ExampleViewHolder;

/**
 * Created by isoft on 07.06.16.
 */
public class ExampleViewImpl implements ExampleView {

    private ExampleViewHolder holder;

    public ExampleViewImpl(ExampleViewHolder holder) {
        this.holder = holder;
    }

    @Override
    public void showResponse(String result) {
        holder.textView.setText(String.format("%s %s", result, "\u2103"));
    }

    @Override
    public void stopUpdate() {
        holder.swipeRefreshLayout.setRefreshing(false);
    }


}
