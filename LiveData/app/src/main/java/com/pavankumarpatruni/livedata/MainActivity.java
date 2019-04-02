package com.pavankumarpatruni.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pavankumarpatruni.livedata.api.models.GetRepos;
import com.pavankumarpatruni.livedata.api.models.Item;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RepoAdapter.OnLoadMoreListener, RepoAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RepoAdapter repoAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initViewModelObserver();
        getRepoList();
    }

    private void initViewModelObserver() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getRepoList().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                repoAdapter.updateList(items);
            }
        });
    }

    private void getRepoList() {
        MyApplication.getInstance(this).getAPIService().getRepos("p", "stars", "desc", 10, page).enqueue(new Callback<GetRepos>() {
            @Override
            public void onResponse(@NonNull Call<GetRepos> call, @NonNull Response<GetRepos> response) {

                GetRepos getRepos = response.body();
                if (getRepos != null) {
                    List<Item> itemList = getRepos.getItems();
                    if (itemList != null) {
                        if (page == 0) {
                            mainActivityViewModel.setList(itemList);
                        } else {
                            mainActivityViewModel.updateList(itemList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetRepos> call, @NonNull Throwable t) {

            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repoAdapter = new RepoAdapter(true, recyclerView);
        repoAdapter.setOnItemClickListener(this);
        repoAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(repoAdapter);
    }

    @Override
    public void onLoadMore() {
        page++;
        getRepoList();
    }

    @Override
    public void onItemClick(Item item) {

    }
}
