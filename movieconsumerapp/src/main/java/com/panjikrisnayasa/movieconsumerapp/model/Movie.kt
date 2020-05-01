package com.panjikrisnayasa.movieconsumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    var id: String? = null,
    var posterPath: String? = null,
    var title: String? = null,
    var voteAverage: Double? = null,
    var popularity: Double? = null,
    var genre: String? = null,
    var releaseDate: String? = null,
    var forAdult: Boolean? = null,
    var overview: String? = null
) : Parcelable