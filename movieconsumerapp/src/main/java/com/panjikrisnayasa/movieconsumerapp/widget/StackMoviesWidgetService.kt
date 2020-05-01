package com.panjikrisnayasa.movieconsumerapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackMoviesWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackMoviesRemoteViewsFactory(this.applicationContext)
    }
}