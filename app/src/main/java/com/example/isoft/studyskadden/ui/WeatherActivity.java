package com.example.isoft.studyskadden.ui;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.isoft.studyskadden.adapters.CitiesAdapter;
import com.example.isoft.studyskadden.PreviewCityWeather;
import com.example.isoft.studyskadden.R;
import com.example.isoft.studyskadden.adapters.SimpleItemTouchHelperCallback;
import com.example.isoft.studyskadden.presenters.WeatherPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends BaseActivity implements WeatherView, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @Inject
    WeatherPresenter weatherPresenter;

    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_cities) RecyclerView recyclerView;
    @BindView(R.id.tv_empty_list) TextView emptyView;

    private CitiesAdapter mAdapter;
    private boolean isRefreshingEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        initList();

        weatherPresenter.attachView(this);

        weatherPresenter.setCityList();

        checkAdapterIsEmpty();

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mAdapter = new CitiesAdapter(this, new ArrayList<>());
        mAdapter.setHasStableIds(true);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkAdapterIsEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkAdapterIsEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkAdapterIsEmpty();
            }

        });

        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter, weatherPresenter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onRefresh() {
        if (mAdapter!=null){
            if (mAdapter.getItemCount()>0)
                weatherPresenter.refreshAllCities();
        }
    }

    @Override
    public void addCity(PreviewCityWeather previewCityWeather){
        if (mAdapter != null)
            mAdapter.addItem(previewCityWeather);
    }

    @Override
    public void removeCity(Long id){
        if (mAdapter != null)
            mAdapter.dropItem(id);
    }

    private void hideList(){
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(false);
    }

    private void showList(){
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(true);
    }

    private void checkAdapterIsEmpty() {
        isRefreshingEnable = !(mAdapter.getItemCount() == 0);
        if (isRefreshingEnable) {
            showList();
        } else {
            hideList();
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

        MenuItem refreshMenuItem = menu.findItem(R.id.menu_refresh);
        if (isRefreshingEnable){
            refreshMenuItem.setVisible(true);
        }
        else {
            refreshMenuItem.setVisible(false);
        }

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.city_name));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_refresh){
            onRefresh();
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
    public void showMessage(String title, String message){
        AlertDialog messDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create();

        messDialog.show();
    }
}
