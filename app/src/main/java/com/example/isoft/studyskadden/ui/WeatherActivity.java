package com.example.isoft.studyskadden.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.isoft.studyskadden.CitiesAdapter;
import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.presenters.WeatherPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends BaseActivity implements WeatherView, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @Inject
    WeatherPresenter weatherPresenter;

    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.sv_list_cities) ScrollView scrollView;
    @BindView(R.id.rv_cities) RecyclerView recyclerView;
    @BindView(R.id.tv_empty_list) TextView emptyView;

    private CitiesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        weatherPresenter.attachView(this);

        weatherPresenter.updateAllView();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {
         weatherPresenter.refreshData();
    }

    @Override
    public void refreshView(List<PreviewCityWeather> previewCityWeathers) {

        if (previewCityWeathers!=null) {
            if (previewCityWeathers.size() > 0) {
                emptyView.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                if (mAdapter != null) {
                    mAdapter.setCityWeathers(previewCityWeathers);
                } else {
                    mAdapter = new CitiesAdapter(this, previewCityWeathers);
                    mAdapter.setHasStableIds(true);
                    recyclerView.setAdapter(mAdapter);
                }
                mAdapter.notifyDataSetChanged();

            } else {
                emptyView.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
            }

        }else {
            emptyView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
    }

    @Override
    public void startUpdate() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopUpdate() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.city_name));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_refresh){
            weatherPresenter.updateAllView();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherPresenter.detachView();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        weatherPresenter.requestCity(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showMessage(String message){
        AlertDialog messDialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create();

        messDialog.show();
    }
}
