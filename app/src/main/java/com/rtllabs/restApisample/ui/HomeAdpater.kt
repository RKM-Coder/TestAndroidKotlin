package com.rtllabs.restApisample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goalsr.testandroidapp.R
import com.rtllabs.restApisample.model.PostModel
import kotlinx.android.synthetic.main.home_list_item_post.view.*

class HomeAdpater(var listofPost: ArrayList<PostModel>,var listener:HomeListener): RecyclerView.Adapter<HomeAdpater.HomeViewHolder>() {

    //private var listofPost:ArrayList<PostModel>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_list_item_post, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = listofPost?.get(position)
        holder.bindView(item)
        holder.itemView.img_delete.setOnClickListener {
            item?.let { it1 ->
               listener.onItemDeleted(it1, position)
            }
        }
    }

    override fun getItemCount(): Int =listofPost?.size?:0

    fun setData(list: ArrayList<PostModel>){
        listofPost=list
        notifyDataSetChanged()
    }

    fun addPost(it: PostModel) {
        listofPost?.add(0,it)
        notifyItemInserted(0)
    }
    fun removeData(position: Int) {
        listofPost?.removeAt(position)
        notifyDataSetChanged()
    }
    class HomeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun bindView(item: PostModel) {
            itemView.tv_home_item_title.text = item?.title
            itemView.tv_home_item_body.text = item?.body
        }

    }

    interface HomeListener{
        fun onItemDeleted(postModel: PostModel, position: Int)
    }
}