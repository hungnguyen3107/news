package com.mubarak.newscastmb.ui.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.newscastmb.data.repository.BookmarkRepository
import com.mubarak.newscastmb.data.repository.NewsRepository
import com.mubarak.newscastmb.data.sources.local.BookmarkNews
import com.mubarak.newscastmb.data.sources.local.NewsItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val bookmarkNewsRepository: BookmarkRepository
):ViewModel() {

    fun insertNews(bookmarkNews: BookmarkNews){
        viewModelScope.launch {
            bookmarkNewsRepository.insertBookmarkNews(bookmarkNews)
        }
    }

}