package com.panjikrisnayasa.moviecataloguesubmission5.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.notification.AlarmReceiver

class NotificationFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var mAlarmReceiver: AlarmReceiver
    private lateinit var mKeyPrefDailyReminder: String
    private lateinit var mKeyPrefReleaseReminder: String
    private var mIsDailyPref: SwitchPreference? = null
    private var mIsReleasePref: SwitchPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAlarmReceiver = AlarmReceiver()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences != null) {
            if (key == mKeyPrefDailyReminder) {
                if (sharedPreferences.getBoolean(mKeyPrefDailyReminder, false)) {

                    Log.d("morgan", "daily")

                    mAlarmReceiver.setRepeatingAlarm(
                        context,
                        AlarmReceiver.TYPE_DAILY_REMINDER,
                        getString(R.string.notification_message_daily)
                    )
                } else {
                    mAlarmReceiver.cancelAlarm(
                        context,
                        AlarmReceiver.TYPE_DAILY_REMINDER
                    )
                }
            } else if (key == mKeyPrefReleaseReminder) {
                if (sharedPreferences.getBoolean(mKeyPrefReleaseReminder, false)) {

                    Log.d("morgan", "release")

                    mAlarmReceiver.setRepeatingAlarm(
                        context,
                        AlarmReceiver.TYPE_RELEASE_REMINDER,
                        ""
                    )

                } else {
                    mAlarmReceiver.cancelAlarm(
                        context,
                        AlarmReceiver.TYPE_RELEASE_REMINDER
                    )
                }
            }
        }
    }

    private fun init() {
        mKeyPrefDailyReminder = resources.getString(R.string.preference_key_daily_reminder)
        mIsDailyPref = findPreference(mKeyPrefDailyReminder)
        mKeyPrefReleaseReminder = resources.getString(R.string.preference_key_release_reminder)
        mIsReleasePref = findPreference(mKeyPrefReleaseReminder)
    }
}