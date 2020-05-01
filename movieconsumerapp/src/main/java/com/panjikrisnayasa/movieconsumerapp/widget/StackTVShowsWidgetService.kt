package com.panjikrisnayasa.movieconsumerapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackTVShowsWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackTVShowsRemoteViewsFactory(this.applicationContext)
    }
}
