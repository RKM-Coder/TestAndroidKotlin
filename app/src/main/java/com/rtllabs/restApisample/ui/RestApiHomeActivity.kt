package com.rtllabs.restApisample.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goalsr.testandroidapp.R
import com.rtllabs.restApisample.model.PostModel
import kotlinx.android.synthetic.main.activity_rest_api_home.*
import kotlinx.android.synthetic.main.create_post_dialog.view.*

class RestApiHomeActivity : AppCompatActivity(),HomeAdpater.HomeListener {

    private lateinit var restApiViewModel: RestApiViewModel
    private lateinit var adapterPost:HomeAdpater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_api_home)
        restApiViewModel=ViewModelProvider(this).get(RestApiViewModel::class.java)
        rv_home.apply {
            layoutManager=LinearLayoutManager(this@RestApiHomeActivity)
            adapterPost= HomeAdpater(ArrayList(),this@RestApiHomeActivity)
            adapter=adapterPost
        }

        restApiViewModel.fetchLivePost()
        restApiViewModel.postListLiveData?.observe(this, Observer {
            if (it!=null){
                rv_home.visibility = View.VISIBLE
                adapterPost.setData(it as ArrayList<PostModel>)
            }else{
                showToast("Something went wrong")
            }
            progress_home.visibility = View.GONE
        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_create_post ->showCreatePostDialog()
        }
        return true

    }

    private fun showCreatePostDialog() {

        val dialog=Dialog(this)
        val view=LayoutInflater.from(this).inflate(R.layout.create_post_dialog,null)
        dialog.setContentView(view)
        var title =""
        var  body =""
        view.btn_submit.setOnClickListener {
            var  texttitle=view.et_title.text.toString().trim()
            var textBody=view.et_body.text.toString().trim()
            //
            if (texttitle.isNotEmpty() && textBody.isNotEmpty()){

                val  postModel=PostModel()
                postModel.userId=1
                postModel.body=textBody
                postModel.title=texttitle

                restApiViewModel.PostData(postModel)
                restApiViewModel.postLiveData?.observe(this, Observer {
                    if (it!=null){
                        Log.d("OPENDIALOG", "showCreatePostDialog: "+it)
                        adapterPost.addPost(it)
                        rv_home.smoothScrollToPosition(0)
                    }else{
                        showToast("Cannot create post at the moment")
                    }
                    dialog.cancel()
                })

            }else{
                showToast("Please fill data carefully!")
            }

        }
        dialog.show()
        //dialog.show()

        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onItemDeleted(postModel: PostModel, position: Int) {
        postModel.id?.let { restApiViewModel.deletePost(it) }
        restApiViewModel.deleteLiveData?.observe(this, Observer {
            if (it!=null){
                adapterPost.removeData(position)
            }else{
                showToast("Cannot delete post at the moment!")
            }
        })
    }
}