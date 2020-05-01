package com.panjikrisnayasa.movieconsumerapp.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.panjikrisnayasa.movieconsumerapp.R
import com.panjikrisnayasa.movieconsumerapp.db.DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.Companion.CONTENT_URI_TV_SHOWS
import com.panjikrisnayasa.movieconsumerapp.helper.MappingHelper
import com.panjikrisnayasa.movieconsumerapp.model.TVShow
import com.panjikrisnayasa.movieconsumerapp.widget.FavoredTVShowsWidget.Companion.EXTRA_ITEM

class StackTVShowsRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    companion object {
        private const val BASE_URL = "https://image.tmdb.org/t/p/w185/"
    }

    private var mTVShowList = ArrayList<TVShow>()

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        mTVShowList.clear()

        val cursor = mContext.contentResolver.query(
            CONTENT_URI_TV_SHOWS,
            null,
            null,
            null,
            null
        )
        val tvShowList = MappingHelper.mapFavoredTVShowCursorToArrayList(cursor)
        mTVShowList.addAll(tvShowList)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.item_favorite_widget)

        val bitmap =
            Glide.with(this.mContext).asBitmap().load(BASE_URL + mTVShowList[position].posterPath)
                .submit(512, 512).get()
        remoteViews.setImageViewBitmap(R.id.image_item_favorite_widget, bitmap)

        val extras = bundleOf(EXTRA_ITEM to position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.image_item_favorite_widget, fillInIntent)
        return remoteViews
    }

    override fun getCount(): Int {
        return mTVShowList.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {}
}