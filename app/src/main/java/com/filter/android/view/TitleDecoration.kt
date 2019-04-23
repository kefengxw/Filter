package com.filter.android.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.util.TypedValue
import android.view.View
import com.filter.R
import com.filter.android.util.UtilString
import java.util.*

class TitleDecoration(ctx: Context) : ItemDecoration() {

    private var mData: ArrayList<ItemRecyclerDisplayData>? = null
    private var mPaint: Paint
    private var mBounds: Rect
    private var mTitleHeight = 0
    private var mBgColor = 0
    private var mTextColor = 0
    private var mTitleFontSize = 0

    init {

        val res = ctx.resources
        mPaint = Paint()
        mBounds = Rect()
        mBgColor = ContextCompat.getColor(ctx, R.color.colorRecyclerTitleBg)
        mTextColor = ContextCompat.getColor(ctx, R.color.colorRecyclerTitleText)
        mTitleHeight = res.getDimensionPixelSize(R.dimen.item_recycler_title_height)
        mTitleFontSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            res.getDimensionPixelSize(R.dimen.item_recycler_title_text_size).toFloat(), res.displayMetrics
        ).toInt()
        mPaint.textSize = mTitleFontSize.toFloat()
        mPaint.isAntiAlias = true
    }

    fun setData(data: ArrayList<ItemRecyclerDisplayData>) {
        this.mData = data
    }

    override fun getItemOffsets(outRect: Rect, item: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, item, parent, state)
        //the position is index of mData, this function is for itemViews to draw
        val position = (item.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        if (position != RecyclerView.NO_POSITION) {
            if (position == 0 || !inSameGroup(position)) {
                outRect.set(0, mTitleHeight, 0, 0)//outRect.set(0,0,0,0) is default
            }
        }
    }

    private fun inSameGroup(position: Int): Boolean {

        if (mData == null || mData!!.size == 0 || position == 0) {
            return false
        }

        val pre = UtilString.getIndex(mData!![position - 1].name)
        val cur = UtilString.getIndex(mData!![position].name)

        return pre == cur
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        //this function is for ItemDecoration to draw
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        lateinit var itemView: View

        for (i in 0 until childCount) {
            itemView = parent.getChildAt(i)

            val params = itemView.layoutParams as RecyclerView.LayoutParams
            val position = params.viewLayoutPosition

            if (position != RecyclerView.NO_POSITION) {
                if (position == 0 || !inSameGroup(position)) { //first one
                    onDrawTitle(c, left, right, itemView, params, position)
                }
            }
        }
    }

    private fun onDrawTitle(
        c: Canvas,
        left: Int,
        right: Int,
        itemView: View,
        params: RecyclerView.LayoutParams,
        position: Int
    ) {
        mPaint.color = mBgColor
        c.drawRect(
            left.toFloat(),
            (itemView.top - params.topMargin - mTitleHeight).toFloat(),
            right.toFloat(),
            (itemView.top - params.topMargin).toFloat(),
            mPaint
        )

        //draw text on Background
        mPaint.color = mTextColor

        val curFirstLetter = UtilString.getIndex(mData!![position].name)
        mPaint.getTextBounds(curFirstLetter, 0, curFirstLetter.length, mBounds)
        c.drawText(
            curFirstLetter,
            itemView.paddingLeft.toFloat(),
            (itemView.top - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2)).toFloat(),
            mPaint
        )
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}