package com.sunilsahoo.memorygame.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.squareup.picasso.Callback

import com.sunilsahoo.controller.entity.Item
import com.sunilsahoo.memorygame.R
import com.squareup.picasso.Picasso

/**
 * Created by sunilkumarsahoo on 11/14/16.
 */
class ItemAdapter internal constructor(private val mContext: Context, private val mViewModifiedListener: OnViewModifiedListener, private val itemList: List<Item>, private val mImageWidth: Int) : BaseAdapter() {

    @Volatile private var count = 0

    override // Get a View that displays the data at the specified position in
            // the data set.
    fun getView(position: Int, convertView: View?,
                gridView: ViewGroup): View {
        // try to reuse the views.
        var view: ImageView? = null
        // if convert view is null then create a new instance else reuse
        // it
        if (convertView != null) {
            view = convertView as ImageView
        }
        if (view == null) {
            view = ImageView(mContext)
            view.minimumWidth = mImageWidth
            view.minimumHeight = mImageWidth
        } else {

        }
        loadImage(position, view)//.setImageResource(mDrawables.get(position));
        view.tag = position.toString()
        return view
    }

    private fun loadImage(position: Int, imageView: ImageView) {
        if (itemList[position] == null || itemList[position]
                .media == null) {
            Picasso
                    .with(mContext)
                    .load(R.drawable.question)
                    .fit()
                    .into(imageView, PicassoCallback())
        } else {
            Picasso
                    .with(mContext)
                    .load(itemList[position].media!!.m)
                    .fit()
                    .into(imageView, PicassoCallback())
        }
    }

    override // Get the row id associated with the specified position in the
            // list.
    fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override // Get the data item associated with the specified position in the
            // data set.
    fun getItem(position: Int): Any {
        return itemList[position]
    }

    override // How many items are in the data set represented by this Adapter.
    fun getCount(): Int {
        return itemList.size
    }

    fun refreshView(){
        count = 0
        notifyDataSetChanged()
    }
    fun updateCounter(){
        count = count++
        if(count == itemList.size)
        mViewModifiedListener.onImagesLoaded()
    }

    inner class PicassoCallback : Callback{
        override fun onSuccess() {
            updateCounter()
        }

        override fun onError() {
            updateCounter()
        }

    }
}