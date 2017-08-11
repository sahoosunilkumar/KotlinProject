package com.sunilsahoo.memorygame.model

import com.sunilsahoo.controller.entity.Item
import java.util.*

/**
 * Created by sunilkumarsahoo on 11/14/16.
 */
class Game : OnModelChangeListener {
    var answerPosition: Int = 0
    var randomPosition: Int = 0
    private var rand: Random? = null
    var correctAnsCount: Int = 0

    private var drawables: MutableList<Item>? = null
    private var initialDrawables: List<Item> = ArrayList<Item>()

    override fun onQuestionChanged(questionCount: Int) {

        answerPosition = getRandomPosition(0, questionCount - 1)
        randomPosition = getRandomPosition(0, questionCount - 1)
    }

    override fun onFetchGame(): Game {
        return this
    }

    private fun getRandomPosition(minimum: Int, maximum: Int): Int {
        return minimum + rand!!.nextInt(maximum - minimum + 1)
    }

    override fun onInitCalled(itemList: List<Item>) {
        rand = Random()
        if (drawables != null) {
            drawables!!.clear()
        } else {
            drawables = ArrayList<Item>()
        }

        initialDrawables = itemList
        drawables!!.addAll(initialDrawables)
    }


    override fun onItemUpdated(randomPositionAns: Int, randomPositionPlace: Int) {
        val selectedMedia = initialDrawables[randomPositionAns]
        val item = Item()

        val count = drawables!!.size
        drawables!!.clear()
        for (i in 0..count - 1) {
            if (i != randomPositionPlace) {
                drawables!!.add(item)
            } else {
                drawables!!.add(selectedMedia)
            }
        }
    }

    fun getDrawables(): List<Item> {
        if (drawables == null) {
            drawables = ArrayList<Item>()
        }
        return drawables as MutableList<Item>
    }
}
