package com.filter.android.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.filter.R
import com.filter.android.util.UtilString
import java.util.*

class RecyclerAdapter(ctx: Context) : Adapter<RecyclerAdapter.RecyclerHolder>() {

    private val mCtx = ctx
    private var mListener: OnItemClickListener? = null
    private var mData: ArrayList<ItemRecyclerDisplayData> = ArrayList()//null;

    fun setData(data: ArrayList<ItemRecyclerDisplayData>) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(vg: ViewGroup, i: Int): RecyclerHolder {
        val itemView = LayoutInflater.from(vg.context).inflate(R.layout.item_recycler_view, vg, false)
        return RecyclerHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, iPosition: Int) {
        val currentData = mData[iPosition]
        //after testing, it is better to update directly in one thread
        //new LoadImageAsyncTask(mCtx, currentData, holder).execute();
//        var flagId = currentData.flagId
//        if (flagId == 0) {
//            val flag = IC_FLAG_FILE + currentData.alpha2Code!!.toLowerCase()
//            flagId = mResources.getIdentifier(flag, IC_FLAG_FOLDER, mPackageName)
//            currentData.flagId = flagId
//        }
//
//        var country = currentData.callId
//        if (country == null) {
//            country = currentData.name + CALL_CODE_LEFT + currentData.callCode + CALL_CODE_RIGHT
//            currentData.callId = country
//        }
//        holder.mIvFlag.setImageResource(currentData.imagesUrl)

        holder.mTvName.text = currentData.name
        if (currentData.imagesUrl == "") {
            Glide.with(mCtx).load(R.drawable.default_image_icon).into(holder.mIvFlag)//default Image
        } else {
            Glide.with(mCtx).load(currentData.imagesUrl).into(holder.mIvFlag)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    class RecyclerHolder(itemView: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {//can be static class if outside want to use it

        private var mItemLer: OnItemClickListener? = null
        var mIvFlag: ImageView = itemView.findViewById(R.id.item_image)
        var mTvName: TextView = itemView.findViewById(R.id.item_text)

        private val onClickListener = View.OnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                mItemLer?.onItemClick(position)
            }
        }

        init {
            mItemLer = listener
            itemView.setOnClickListener(onClickListener)
        }
    }

    fun getPositionByIndex(it: String): Int {
        if (mData.isEmpty() || 'A' > it[0] || it[0] > 'Z') {
            return RecyclerView.NO_POSITION
        }

        for (i in mData.indices) {
            if (UtilString.getIndex(mData[i].name.toUpperCase()) == it) {
                return i
            }
        }

        return RecyclerView.NO_POSITION
    }

    //    //just for improve the performance, cpu is 1% lower than do it directly in mainThread, but load is slower
    //    private static class LoadImageAsyncTask extends AsyncTask<Void, Void, Void> {
    //
    //        private static String mPackageName = null;
    //        private static Resources mResources = null;
    //        private ItemRecyclerDisplayData mCurrentData;
    //        private RecyclerHolder mHolder;
    //        private String mCountry = null;
    //        private int mFlagId = 0;
    //
    //        public LoadImageAsyncTask(Context ctx, ItemRecyclerDisplayData currentData, RecyclerHolder holder) {
    //            this.mCurrentData = currentData;
    //            this.mHolder = holder;
    //
    //            if (mPackageName == null) {
    //                mPackageName = ctx.getPackageName();
    //            }
    //            if (mResources == null) {
    //                mResources = ctx.getResources();
    //            }
    //        }
    //
    //        @Override
    //        protected Void doInBackground(Void... params) {
    //
    //            String flag = ("ic_flag_" + mCurrentData.getAlpha2Code().toLowerCase());
    //            mFlagId = mResources.getIdentifier(flag, "drawable", mPackageName);
    //
    //            mCountry = mCurrentData.getName() + " (+" + mCurrentData.getCallCode() + ")";
    //
    //            return null;
    //        }
    //
    //        @Override
    //        protected void onPostExecute(Void aVoid) {
    //            super.onPostExecute(aVoid);
    //
    //            mHolder.mIvFlag.setImageResource(mFlagId);
    //            mHolder.mTvName.setText(mCountry);
    //        }
    //    }
}