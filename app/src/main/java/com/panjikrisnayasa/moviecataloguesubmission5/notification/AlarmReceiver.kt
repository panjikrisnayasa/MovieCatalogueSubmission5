package com.panjikrisnayasa.moviecataloguesubmission5.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjikrisnayasa.moviecataloguesubmission5.BuildConfig
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.model.Movie
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/discover/movie"
        private const val API_KEY_PARAM = "api_key"
        private const val PRIMARY_RELEASE_DATE_GTE = "primary_release_date.gte"
        private const val PRIMARY_RELEASE_DATE_LTE = "primary_release_date.lte"

        const val TYPE_DAILY_REMINDER = "Daily Reminder"
        const val TYPE_RELEASE_REMINDER = "Release Reminder"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        private const val ID_DAILY_REMINDER = 100
        private const val ID_RELEASE_REMINDER = 101
    }

    private var mReleasedMovie: ArrayList<Movie> = arrayListOf()

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val title =
            if (type == TYPE_DAILY_REMINDER) TYPE_DAILY_REMINDER else TYPE_RELEASE_REMINDER
        val notificationId =
            if (type == TYPE_DAILY_REMINDER) ID_DAILY_REMINDER else ID_RELEASE_REMINDER

        Log.d(
            "morgan",
            "on receive fun: type=$type, title=$title, notificationId=$notificationId"
        )

        if (notificationId == ID_DAILY_REMINDER) {
            showAlarmNotification(context, title, message, notificationId)
        } else if (notificationId == ID_RELEASE_REMINDER) {

            mReleasedMovie.clear()
            getReleasedMovies(context)

            val handler = Handler()
            handler.postDelayed({
                Log.d("morgan", "on receive fun: mReleasedMovie.size = ${mReleasedMovie.size}")

                for (i in 0 until mReleasedMovie.size) {
                    showAlarmNotification(
                        context,
                        mReleasedMovie[i].title.toString(),
                        "${mReleasedMovie[i].title.toString()} has released today!",
                        i
                    )
                }
            }, 2000)
        }
    }

    fun setRepeatingAlarm(context: Context?, type: String, message: String) {

        Log.d("morgan", "set repeating alarm fun called")

        val calendar = Calendar.getInstance()
        var pendingIntent: PendingIntent? = null

        val alarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)
        intent.putExtra(EXTRA_MESSAGE, message)

        if (type == TYPE_DAILY_REMINDER) {

            Log.d("morgan", "set repeating alarm fun: daily ${calendar.time}")

            calendar.set(Calendar.HOUR_OF_DAY, 7)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            Log.d("morgan", "set repeating alarm fun: after set ${calendar.time}")

            pendingIntent = PendingIntent.getBroadcast(
                context,
                ID_DAILY_REMINDER,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else if (type == TYPE_RELEASE_REMINDER) {

            Log.d("morgan", "set repeating alarm fun: release ${calendar.time}")

            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            Log.d("morgan", "set repeating alarm fun: after set ${calendar.time}")

            pendingIntent = PendingIntent.getBroadcast(
                context,
                ID_RELEASE_REMINDER,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context?, type: String) {

        Log.d("morgan", "cancel alarm fun called")

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode =
            if (type == TYPE_DAILY_REMINDER) ID_DAILY_REMINDER else ID_RELEASE_REMINDER
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Repeating alarm cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String?,
        notificationId: Int
    ) {

        Log.d(
            "morgan",
            "show alarm notification fun: title = $title, notification id = $notificationId"
        )

        val channelId: String
        val channelName: String
        if (notificationId == ID_DAILY_REMINDER) {
            channelId = "Channel 1"
            channelName = "Alarm Manager Channel"
        } else {
            channelId = "Channel 2"
            channelName = "Alarm Manager Channel 2"
        }
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_movie_grey_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)
    }

    private fun getReleasedMovies(context: Context) {

        Log.d("morgan", "get released movies fun called")

        val mClient = AsyncHttpClient()
        val currentDate = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formattedDate = df.format(currentDate)

        val listItems: ArrayList<Movie> = arrayListOf()
        val builtUri =
            Uri.parse(BASE_URL_MOVIE).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MY_API_KEY)
                .appendQueryParameter(PRIMARY_RELEASE_DATE_GTE, formattedDate)
                .appendQueryParameter(PRIMARY_RELEASE_DATE_LTE, formattedDate)
                .build()
        val url = builtUri.toString()

        mClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    var tResult = ""
                    if (responseBody != null) {
                        tResult = String(responseBody)
                    }
                    val resultM = tResult
                    val responseObjectM = JSONObject(resultM)
                    val listM = responseObjectM.getJSONArray("results")

                    Log.d("morgan", "listM = ${listM.length()}")

                    for (i in 0 until listM.length()) {
                        if (i > 2) break
                        val listMovies = listM.getJSONObject(i)
                        val movie =
                            Movie()
                        movie.id = listMovies.getLong("id").toString()
                        movie.posterPath = listMovies.getString("poster_path")
                        movie.title = listMovies.getString("title")
                        movie.popularity = listMovies.getDouble("popularity")
                        movie.voteAverage = listMovies.getDouble("vote_average")
                        movie.releaseDate = listMovies.getString("release_date")
                        movie.forAdult = listMovies.getBoolean("adult")
                        listItems.add(movie)
                    }
                    mReleasedMovie.addAll(listItems)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(
                    context,
                    context.getString(R.string.failed_to_retrieve_data),
                    Toast.LENGTH_SHORT
                ).show()
                error?.printStackTrace()
            }
        })
    }
}
