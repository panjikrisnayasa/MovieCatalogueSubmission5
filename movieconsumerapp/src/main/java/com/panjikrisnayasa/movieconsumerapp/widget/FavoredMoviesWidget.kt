package com.panjikrisnayasa.movieconsumerapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.panjikrisnayasa.movieconsumerapp.R

/**
 * Implementation of App Widget functionality.
 */
class FavoredMoviesWidget : AppWidgetProvider() {

    companion object {

        const val EXTRA_ITEM = "com.panjikrisnayasa.movieconsumerapp.favored_movies.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackMoviesWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favored_movies_widget)
            views.setRemoteAdapter(R.id.stack_favored_movies_widget, intent)
            views.setEmptyView(
                R.id.stack_favored_movies_widget,
                R.id.text_favored_movies_widget_no_data
            )

            appWidgetManager.notifyAppWidgetViewDataChanged(
                appWidgetId,
                R.id.stack_favored_movies_widget
            )
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}