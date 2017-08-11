package com.sunilsahoo.memorygame.view

import android.view.View
import com.sunilsahoo.memorygame.Constants

/**
 * Created by sunilkumarsahoo on 11/14/16.
 */
interface OnViewModifiedListener {
    fun onItemLongClickStateChanged(state: Int)

    fun onListItemUpdated()

    fun onImagesLoaded()

    fun onTimerStatusChanged(status: Int)

    fun changeStateTo(state: Constants.State)

    fun onAnswerSelected(view: View, isCorrectAnswer: Boolean)

    fun showProgressBar()

    fun dismissProgressBar()
}
