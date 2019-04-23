package com.filter.android.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.filter.R
import com.filter.android.model.data.InternalDataConfiguration.INDEX_BAR_LETTER_SPLIT
import java.util.*

class IndexBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private var mListener: OnTouchEventListener? = null
    private lateinit var mResources: Resources
    private lateinit var mLetters: List<String>
    private lateinit var mHintTextView: TextView
    private val mLetterPaint = Paint()//Text Paint
    private var mLetterSize = 0f
    private var mLetterColor = 0
    private var mChoosePosition = -1
    private var mOldChoosePosition = -1
    private var mHeight = 0
    private var mWidth = 0
    private var mLetterHeight = 0

    init {
        init(context, attrs)
    }

    fun setHintText(it: TextView) {
        mHintTextView = it
    }

    @Suppress("UNUSED_PARAMETER")
    private fun init(ctx: Context, attrs: AttributeSet?) {
        mResources = ctx.resources
        mLetters = Arrays.asList(*mResources.getStringArray(R.array.indexBarLetter))
        mLetterColor = ContextCompat.getColor(ctx, R.color.colorIndexBarText)
    }

    private fun updateViewParam() {
        mHeight = height
        mWidth = width
        mLetterHeight = mHeight / mLetters.size//maybe add some padding here
        mLetterSize = mLetterHeight * INDEX_BAR_LETTER_SPLIT//Golden Section 0.618
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        updateViewParam()
        drawLetter(canvas)
    }

    private fun drawLetter(canvas: Canvas) {

        var x: Float
        var y: Float//for each letter
        var cur: String

        setLetterPaint(mLetterPaint)

        for (i in mLetters.indices) {
            cur = mLetters[i]
            x = (mWidth - mLetterPaint.measureText(cur)) / 2
            y = (mLetterHeight * (i + 1)).toFloat()
            canvas.drawText(cur, x, y, mLetterPaint)
        }
    }

    private fun setLetterPaint(letterPaint: Paint) {
        letterPaint.color = mLetterColor
        letterPaint.textSize = mLetterSize
        //mLetterPaint.setTypeface();//Letter Type
        //letterPaint.setFakeBoldText(false);
        letterPaint.isAntiAlias = true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        super.dispatchTouchEvent(event)

        //val x = event.x//each pointer of the screen
        val y = event.y
        val action = event.action

        //if (mHintTextView == null) {
        //    return false
        //}

        mOldChoosePosition = mChoosePosition
        mChoosePosition = y.toInt() / mLetterHeight

        when (action) {
            MotionEvent.ACTION_UP//leave
            -> handleTouchEventActionUp()
            else//others, DOWN and MOVE
            -> handleTouchEventActionMove()
        }
        return true
    }

    private fun handleTouchEventActionUp() {
        mHintTextView.visibility = INVISIBLE
        mChoosePosition = -1
    }

    private fun handleTouchEventActionMove() {
        mHintTextView.visibility = VISIBLE

        if (mChoosePosition != mOldChoosePosition && mChoosePosition >= 0 && mChoosePosition < mLetters.size) {
            val cur = mLetters[mChoosePosition]
            mHintTextView.text = cur
            if (mListener != null) {
                mListener!!.onTouchListener(cur)
            }
        }
        mOldChoosePosition = mChoosePosition
    }

    interface OnTouchEventListener {
        fun onTouchListener(it: String)
    }

    fun setOnTouchEventListener(it: OnTouchEventListener) {
        mListener = it
    }
}