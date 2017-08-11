package com.sunilsahoo.memorygame

/**
 * Created by sunilkumarsahoo on 11/14/16.
 */
interface Constants {

    enum class State {
        INITIALIZING, NEW, ABOUT_TO_RUN, RUNNING, LEVEL_SUCCESS, STOP
    }

    interface TimerStatus {
        companion object {
            val START = 0
            val STOP = 1
        }
    }

    interface LongClickState {
        companion object {
            val ADD = 0
            val REMOVE = 1
        }
    }

    companion object {
        val TICKER_INTERVAL = 1000
        val TOTAL_TICKER_TIME = 15 * TICKER_INTERVAL
        val MAX_NO_OF_IMAGES = 9
    }
}
