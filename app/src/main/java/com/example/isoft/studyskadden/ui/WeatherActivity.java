package com.example.isoft.studyskadden.ui;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        weatherPresenter.setCityList();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Log.d("onSwiped" , viewHolder.toString() + " " + swipeDir);
                weatherPresenter.removeCity(viewHolder);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onRefresh() {
        if (mAdapter!=null){
            if (mAdapter.getItemCount()>0)
                weatherPresenter.refreshData();
        }
    }

    @Override
    public void addCity(PreviewCityWeather previewCityWeather){
        if (mAdapter != null)
            mAdapter.addItem(previewCityWeather);
    }

    @Override
    public void removeCity(int adapterPosition){
        if (mAdapter != null)
            mAdapter.dropItem(adapterPosition);
    }

    @Override
    public void setCityList(List<PreviewCityWeather> previewCityWeathers) {
        if (previewCityWeathers!=null) {
            if (mAdapter != null) {
                mAdapter.updateItems(previewCityWeathers);
            } else {
                mAdapter = new CitiesAdapter(this, previewCityWeathers);
                mAdapter.setHasStableIds(true);
                mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        checkAdapterIsEmpty();
                    }
                });
                recyclerView.setAdapter(mAdapter);
                checkAdapterIsEmpty();
            }
        }else {
            hideList();
        }
    }

    private void hideList(){
        emptyView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(false);
    }

    private void showList(){
        emptyView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(true);
    }

    private void checkAdapterIsEmpty() {
        if (mAdapter.getItemCount() == 0) {
            hideList();
        } else {
            showList();
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
        if (swipeRefreshLayout.isEnabled()){
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
    public void showMessage(int titleResId, String message){
        AlertDialog messDialog = new AlertDialog.Builder(this)
                .setTitle(getString(titleResId))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create();

        messDialog.show();
    }
}
