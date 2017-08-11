package com.sunilsahoo.memorygame.viewmodel

import android.util.Log
import android.view.View
import com.sunilsahoo.controller.ITaskListener
import com.sunilsahoo.controller.ImageListController
import com.sunilsahoo.controller.entity.ImageList
import com.sunilsahoo.controller.entity.RestResponse
import com.sunilsahoo.memorygame.Constants
import com.sunilsahoo.memorygame.OnStateChangeListener
import com.sunilsahoo.memorygame.Utility
import com.sunilsahoo.memorygame.model.Game
import com.sunilsahoo.memorygame.model.OnModelChangeListener
import com.sunilsahoo.memorygame.view.OnViewModifiedListener

/**
 * Created by sunilkumarsahoo on 11/14/16.
 */
class GameViewViewModel internal constructor(private val viewModifiedListener: OnViewModifiedListener) : OnStateChangeListener, ITaskListener {
    val modelChangeListener: OnModelChangeListener
    private var imageList: ImageList? = null

    init {
        this.modelChangeListener = Game()
    }

    override fun onStateChanged(state: Constants.State) {
        Log.d("Sunil", "onStateChanged : " + state)
        viewModifiedListener.onItemLongClickStateChanged(Constants.LongClickState.REMOVE)
        when (state) {
            Constants.State.INITIALIZING -> {
                viewModifiedListener.showProgressBar()
                getImages()
            }
            Constants.State.NEW -> {
                viewModifiedListener.dismissProgressBar()
                viewModifiedListener.onListItemUpdated()
                onStateChanged(Constants.State.ABOUT_TO_RUN)
            }
            Constants.State.ABOUT_TO_RUN -> viewModifiedListener.onTimerStatusChanged(Constants.TimerStatus.START)
            Constants.State.RUNNING -> {
                modelChangeListener.onQuestionChanged(Constants.MAX_NO_OF_IMAGES)
                viewModifiedListener.changeStateTo(Constants.State.RUNNING)
            }
            Constants.State.LEVEL_SUCCESS -> onStateChanged(Constants.State.ABOUT_TO_RUN)
            Constants.State.STOP -> {
            }
        }
    }

    fun onAnswerSelected(position: Int, view: View) {
        if (position == modelChangeListener.onFetchGame().answerPosition) {
            modelChangeListener.onFetchGame().correctAnsCount = modelChangeListener.onFetchGame().correctAnsCount + 1
            viewModifiedListener.onAnswerSelected(view, true)
            if (modelChangeListener.onFetchGame().correctAnsCount % Constants.MAX_NO_OF_IMAGES == 0 && modelChangeListener
                    .onFetchGame().correctAnsCount != 0) {
                //change level
                getImages()
                onStateChanged(Constants.State.NEW)
            } else {
                onStateChanged(Constants.State.RUNNING)
            }
        } else {
            viewModifiedListener.onAnswerSelected(view, false)
        }
    }

    fun invalidate() {
        modelChangeListener.onItemUpdated(modelChangeListener.onFetchGame()
                .answerPosition, modelChangeListener.onFetchGame()
                .randomPosition)
        viewModifiedListener.onListItemUpdated()
    }

    private fun getImages() {
        ImageListController().getImageList(this)
    }

    override fun onSuccess(response: RestResponse) {
        imageList = response.response as ImageList?
        this.modelChangeListener.onInitCalled(Utility.formatList(imageList!!
                .items, Constants.MAX_NO_OF_IMAGES))
        onStateChanged(Constants.State.NEW)
    }

    override fun onError(error: String, code: Int) {
        viewModifiedListener.dismissProgressBar()
    }
}
