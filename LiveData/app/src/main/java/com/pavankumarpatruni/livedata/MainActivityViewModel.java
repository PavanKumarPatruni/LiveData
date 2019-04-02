package com.pavankumarpatruni.livedata;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.pavankumarpatruni.livedata.api.models.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Item>> mutableRepoList;
    private List<Item> repoList = new ArrayList<>();

    LiveData<List<Item>> getRepoList() {
        if (mutableRepoList == null) {
            mutableRepoList = new MutableLiveData<>();
        }
        return mutableRepoList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("---------------", "on cleared called");
    }

    public void setList(List<Item> repoList) {
        this.repoList = repoList;

        Log.d("-----------------------", repoList.size() + " ");

        this.mutableRepoList.setValue(repoList);
    }

    public void updateList(List<Item> repoList) {
        this.repoList.addAll(repoList);

        Log.d("-----------------------", repoList.size() + " ");

        this.mutableRepoList.setValue(repoList);
    }

}
