package com.panjikrisnayasa.moviecataloguesubmission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TVShow(
    var id: String? = null,
    var posterPath: String? = null,
    var name: String? = null,
    var voteAverage: Double? = null,
    var genre: String? = null,
    var popularity: Double? = null,
    var firstAirDate: String? = null,
    var language: String? = null,
    var overview: String? = null
) : Parcelable