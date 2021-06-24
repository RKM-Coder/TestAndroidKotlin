package com.rtllabs.restApisample.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rtllabs.restApisample.model.PostModel
import com.rtllabs.restApisample.repository.HomeRepository

class RestApiViewModel(application: Application):AndroidViewModel(application) {

    private var homeRepository:HomeRepository?=null
    var postListLiveData:LiveData<List<PostModel>>?=null
    var postLiveData: LiveData<PostModel>?=null
    var deleteLiveData:LiveData<Boolean>?=null

    init {
        homeRepository= HomeRepository()
        postListLiveData= MutableLiveData()
        postLiveData= MutableLiveData()
        deleteLiveData= MutableLiveData()
    }

    fun fetchLivePost(){
        postListLiveData=homeRepository?.getListOfPost()
    }

    fun PostData(postModel: PostModel){
        postLiveData=homeRepository?.createPost(postModel)
    }

    fun deletePost(id:Int){
        deleteLiveData=homeRepository?.deletePost(id)
    }
}