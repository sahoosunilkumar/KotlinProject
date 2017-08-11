package com.sunilsahoo.memorygame.view

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.sunilsahoo.memorygame.Constants
import com.sunilsahoo.memorygame.R
import com.sunilsahoo.memorygame.Utility
import com.sunilsahoo.memorygame.viewmodel.GameViewViewModel

class GameActivity : AppCompatActivity(), OnDragListener, OnItemLongClickListener, OnViewModifiedListener {
    private var gridView: GridView? = null
    private var adapter: ItemAdapter? = null
    private var countdownTimerTV: TextView? = null
    private var titleTV: TextView? = null
    private var timer: Timer? = null
    private var progressBar: ProgressBar? = null
    private var gameViewModel: GameViewViewModel? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer = Timer(Constants.TOTAL_TICKER_TIME.toLong(), Constants.TICKER_INTERVAL.toLong())
        gameViewModel = GameViewViewModel(this)
        gridView = findViewById(R.id.grid_view) as GridView
        adapter = ItemAdapter(this, this, gameViewModel!!.modelChangeListener.onFetchGame().getDrawables(), getGridItemWidth(this))
        gridView!!.adapter = adapter
        gridView!!.columnWidth = getGridItemWidth(this)
        countdownTimerTV = findViewById(R.id.countdownTimerTV) as TextView
        titleTV = findViewById(R.id.titleTV) as TextView
        progressBar = findViewById(R.id.progressBar) as ProgressBar
        gameViewModel!!.onStateChanged(Constants.State.INITIALIZING)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
        when (dragEvent.action) {
            DragEvent.ACTION_DROP -> {
                val destinationPosition = getItemAt(dragEvent.x,
                        dragEvent.y)
                gameViewModel!!.onAnswerSelected(destinationPosition, (view as ViewGroup).getChildAt(destinationPosition))
            }
        }
        return true
    }

    override fun onItemLongClick(gridView: AdapterView<*>, view: View,
                                 position: Int, row: Long): Boolean {
        val item = ClipData.Item(view.tag as String)
        val clipData = ClipData(view.tag as CharSequence,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
        if (android.os.Build.VERSION.SDK_INT < 24) {
            view.startDrag(clipData, View.DragShadowBuilder(view), null, 0)
        } else {
            view.startDragAndDrop(clipData, View.DragShadowBuilder(view), null, 0)
        }
        if (position == gameViewModel!!.modelChangeListener.onFetchGame()
                .randomPosition) {
            gridView.setOnDragListener(this)
        }
        return true
    }


    private fun getItemAt(x: Float, y: Float): Int {
        return gridView!!.pointToPosition(x.toInt(), y.toInt())
    }


    override fun onItemLongClickStateChanged(state: Int) {
        when (state) {
            Constants.LongClickState.ADD -> gridView!!.onItemLongClickListener = this
            Constants.LongClickState.REMOVE -> gridView!!.onItemLongClickListener = null
        }

    }

    override fun onListItemUpdated() {
        runOnUiThread { adapter!!.refreshView() }
    }

    override fun onImagesLoaded() {
        gameViewModel!!.onStateChanged(Constants.State.NEW)
    }

    override fun onTimerStatusChanged(status: Int) {
        when (status) {
            Constants.TimerStatus.START -> startTimer()
            Constants.TimerStatus.STOP -> {
            }
        }
    }

    override fun changeStateTo(state: Constants.State) {
        when (state) {
            Constants.State.RUNNING -> {
                titleTV!!.setText(R.string.place_image_hint)
                countdownTimerTV!!.visibility = View.INVISIBLE
                gameViewModel!!.invalidate()
                onItemLongClickStateChanged(Constants.LongClickState.ADD)
            }
        }
    }

    override fun onAnswerSelected(view: View, isCorrectAnswer: Boolean) {
        if (isCorrectAnswer) {
            Utility.applyAnimation(view)
        }
    }

    override fun showProgressBar() {
        runOnUiThread {
            progressBar!!.visibility = View.VISIBLE
        }
    }

    override fun dismissProgressBar() {
        runOnUiThread {
            progressBar!!.visibility = View.GONE
        }
    }


    private fun startTimer() {
        runOnUiThread {
            titleTV!!.setText(R.string.remaining_time)
            countdownTimerTV!!.visibility = View.VISIBLE
            timer!!.start()
        }

    }

    fun getGridItemWidth(activity: Activity): Int {
        val displaymetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaymetrics)
        val screenHeight = displaymetrics.heightPixels
        val screenWidth = displaymetrics.widthPixels
        return ((if (screenHeight > screenWidth)
            screenWidth
        else
            screenHeight) / 3.5).toInt()
    }

    override fun onDestroy() {
        if (timer != null) {
            timer!!.cancel()
        }
        timer = null
        super.onDestroy()
    }

    internal inner class Timer(totalTime: Long, tickInterval: Long) : CountDownTimer(totalTime, tickInterval) {

        override fun onTick(millisUntilFinished: Long) {
            countdownTimerTV!!.text = "" + millisUntilFinished.toInt() / Constants.TICKER_INTERVAL
        }

        override fun onFinish() {
            gameViewModel!!.onStateChanged(Constants.State.RUNNING)
        }
    }

    companion object {

        private val TAG = GameActivity::class.java.name
    }
}
