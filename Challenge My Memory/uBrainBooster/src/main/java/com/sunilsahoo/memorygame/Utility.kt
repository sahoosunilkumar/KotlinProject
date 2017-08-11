package com.sunilsahoo.memorygame

import android.animation.ObjectAnimator
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.sunilsahoo.controller.entity.Item
import com.sunilsahoo.controller.entity.Media
import java.util.*

/**
 * Created by sunilkumarsahoo on 11/15/16.
 */
object Utility {

    fun createDefaultMedia(context: Context): Media {
        val imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.resources.getResourcePackageName(R
                .drawable.question)
                + '/' + context.resources.getResourceTypeName(R.drawable
                .question) + '/' + context.resources
                .getResourceEntryName(R.drawable.question))
        val media = Media()
        media.m = imageUri.path
        return media
    }

    fun formatList(list: List<Item>, maxSize: Int): List<Item> {
        val newList = ArrayList<Item>()
        for (i in 0..maxSize - 1) {
            newList.add(list[i])
        }
        return newList
    }


    fun applyAnimation(view: View) {

        val animation = ObjectAnimator.ofFloat(view, "rotationY",
                0.0f, 360f)
        animation.duration = 1000
        animation.repeatCount = 1
        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.start()
    }
}
