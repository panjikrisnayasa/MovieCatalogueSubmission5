package com.panjikrisnayasa.moviecataloguesubmission2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MoviesModel(
    var poster: Int = 0,
    var title: String? = "",
    var ratingScore: Float = 0f,
    var genre: String? = "",
    var duration: String? = "",
    var rating: String? = "",
    var synopsis: String? = ""
) : Parcelable