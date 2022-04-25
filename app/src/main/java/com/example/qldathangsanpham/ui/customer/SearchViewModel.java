package com.example.qldathangsanpham.ui.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SearchViewModel {
    private MutableLiveData<String> query= new MutableLiveData<String>();

    public void setQuery(String queryData)
    {
        query.setValue(queryData);
    }

    public LiveData<String> getQuery() {
        return query;
    }
}
