package com.example.isoft.studyskadden;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

/**
 * Created by isoft on 07.06.16.
 */
public class ExampleViewHolder {

    public final SwipeRefreshLayout swipeRefreshLayout;
    public final TextView textView;

    public ExampleViewHolder(SwipeRefreshLayout swipeRefreshLayout, TextView textView) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.textView = textView;
    }

}
