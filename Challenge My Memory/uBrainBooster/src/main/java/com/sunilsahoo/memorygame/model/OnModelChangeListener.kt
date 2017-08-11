package com.sunilsahoo.memorygame.model

import com.sunilsahoo.controller.entity.Item

/**
 * Created by sunilkumarsahoo on 11/15/16.
 */
interface OnModelChangeListener {
    fun onInitCalled(drawables: List<Item>)

    fun onQuestionChanged(questionCount: Int)

    fun onFetchGame(): Game

    fun onItemUpdated(randomPositionAns: Int, randomPositionPlace: Int)
}
