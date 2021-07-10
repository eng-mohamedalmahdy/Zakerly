package com.graduationproject.zakerly.navigation.search;

import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private SearchRepository repository;

    public SearchViewModel(SearchRepository repository) {
        this.repository = repository;
    }
}
